# CLAUDE.md - Recipes Library

Library mit zusätzlichen Rezept- und Ingredient-Typen für Datapacks und andere Mods: Crafting-Rezepte mit Item-Komponenten (früher NBT) im Ergebnis, ein Umbenennungs-Rezept mit Namensschild und ein Ingredient, das Items anhand ihrer Komponenten vergleicht. Mod-ID `recipes_lib`, Package `de.geheimagentnr1.recipes_lib`, Client+Server, keine eigenen Blöcke/Items.

Diese Datei ist auf allen `develop_*`-Branches identisch; Quelle: `../Docs/mod-claude/RecipesLibrary.md` (Änderungen dort, dann `Docs/tools/sync_mod_claude_md.sh`). Versionsstand: [`../Docs/VERSIONEN.md`](../Docs/VERSIONEN.md).

## Versionen

- Jars, Branches, Ranges und Tags: [`../Docs/VERSIONEN.md`](../Docs/VERSIONEN.md). NeoForge ab `develop_1.21.1` (wenige, breite Jars: 1.21.2 - 1.21.10, 1.21.11, 26.x), Forge `develop_1.14.4` - `develop_1.21` plus `develop_1.21.1_forge`. `develop_1.21.3` ist ein unveröffentlichter Forge-Versuch und kann ignoriert werden.
- Abhängige Mods binden das Jar `RecipesLibrary-<mc>:${rl_version}` aus `ModdingMavenRepositories` nur `runtimeOnly`; ab 1.21.2 steht `<mc>` in einer eigenen Variable `rl_minecraft_version` (z. B. ein 26.3-Branch nutzt das 26.1-Jar). **RecipesLibrary vor den abhängigen Mods veröffentlichen.**
- CurseForge-Projekt `398351`, Modrinth `LG8SignH`.

## Abhängigkeiten

- Keine Mod-Abhängigkeiten. Forge 1.20 - 1.21.1-Forge: MinecraftForgeAPI (Hauptklasse erweitert dort deren `AbstractMod`), siehe [`../Docs/forge/README.md`](../Docs/forge/README.md).
- Nutzer: DynamicalCompass, ManyIdeasDoors, ManyIdeasChristmas (ab 1.20.6) - **nur über JSON-Typen**, keine Java-API. Änderungen an Typ-Namen, JSON-Feldern (`item`, `matchType`, `merge_components`) oder `MatchType`-Werten brechen deren Rezepte still (Rezept lädt nicht, nur Log-Fehler).

## Architektur

| JSON-Typ | Klasse | Zweck |
|---|---|---|
| `recipes_lib:crafting_shaped_components` | `ShapedComponentsRecipe` | Shaped-Rezept mit Komponenten im Ergebnis, optional Komponenten der Zutat übernehmen (`merge_components`) |
| `recipes_lib:crafting_shapeless_components` | `ShapelessComponentsRecipe` | wie oben, shapeless |
| `recipes_lib:renaming` | `RenamingRecipe` | Item + benanntes Namensschild → Item mit diesem Namen |
| Ingredient `recipes_lib:components` | `ComponentsIngredient` | Item mit Komponenten-Vergleich, `MatchType` `EQUAL`, `CONTAINS`, `CONTAINS_NONE`, `NOT_EQUAL` |

- Code: `elements/recipes/components/` (`ComponentsRecipe`, `ComponentsRecipeResult`, `ComponentsRecipeSerializer`, `shaped_nbt/`, `shapless_nbt/` - Ordnernamen historisch), `elements/recipes/renaming/`, `elements/recipes/ingredients/components/` (`ComponentsIngredient`, Codec, `MatchType`), Registrierung in `ModRecipeSerializersRegisterFactory` und `ModIngredientSerializersRegisterFactory` (`DeferredRegister` auf `NeoForgeRegistries.Keys.INGREDIENT_TYPES`); Helfer `helpers/ShaplessRecipesHelper`, `util/JSONUtil`, `util/Pair`, `EnumCodec`.
- NeoForge: eigenständige Hauptklasse mit `IEventBus`-Konstruktor, **kein** ManyIdeasCore-`AbstractMod`.
- Forge bis 1.20.4: NBT statt Komponenten - Typen `recipes_lib:crafting_shaped_nbt`, `crafting_shapeless_nbt`, Ingredient `recipes_lib:nbt` (`elements/recipes/nbt/`, `ingredients/nbt/`). Ab 1.20.6 (auch Forge) die Komponenten-Typen oben.

## Funktionen, Befehle, Konfiguration

Keine Befehle, keine Config. Rezepte werden ausschließlich per JSON (Mod-Ressourcen oder Datapacks) definiert.

## Fallstricke

- **Rezept-JSON ab 1.21.2:** Custom-Ingredients über `"neoforge:ingredient_type": "recipes_lib:components"` statt `"type"`, Vanilla-Ingredients als String. Betrifft alle Nutzer-Mods.
- **Rezept-API ab 1.21.2:** `placementInfo()` + `display()` statt `getIngredients()`/`getResultItem()` (sonst fehlt das Rezept im Rezeptbuch), `ICustomIngredient.items()` + `display()`. `Ingredient.items()` liefert in 1.21.2 eine `List<Holder<Item>>` (`items().stream()`), ab 1.21.11 einen `Stream`. Anzeige in Rezept-Viewern (JEI): `display()` muss ein passendes Beispiel-Item zeigen (ohne ausgeschlossene Komponenten; Umbenennung mit Beispielnamen) - gefixt in 4.0.2.
- **Ab 26.1:** `RecipeSerializer` ist ein Record (`ComponentsRecipeSerializer`/`RenamingRecipeSerializer` bauen ihn per `createSerializer()`), Ergebnisse als `ItemStackTemplate`. **Beim Parsen nie einen `ItemStack` erzeugen** - Komponenten sind dann noch nicht gebunden (`Components not bound yet`, Serverstart bricht ab, Compile grün); Stack erst in `assemble()` bzw. beim ersten `test()` bauen.
- GameTests: annotationsbasiert nur bis 1.21.4; ab `develop_1.21.2` entfernt (samt Run-Config und CI-Job).
- Kompatibilität eines Jars über seinen ganzen Bereich prüfen: `./gradlew compileJava compileTestJava --rerun-tasks -Pminecraft_version=<mc> -Pneoforge_version=<nf> -Pmapping_version=<mc>` plus `bincheck.ps1`.

## Testen

- Allgemein: [`../Docs/testing/README.md`](../Docs/testing/README.md).
- Standalone-Test-Datapack nur mit Vanilla-Items: [`../Docs/testing/datapacks/recipes_lib_test/`](../Docs/testing/datapacks/recipes_lib_test/) (Format 1.21.2 - 26.3; README mit Test-Tabelle und `/give`-Befehlen). In `<Testpack>\world\datapacks\` kopieren, `/datapack list` prüfen, Log auf `Couldn't parse data file`, dann ingame craften (inkl. Gegenproben) und JEI-Anzeige prüfen.
- Danach die Rezepte der Nutzer-Mods (DynamicalCompass: Kompass sperren/umbenennen, Doors: gefärbte Dielen, Christmas: heiße Schokolade).
- `/give` mit Namen: bis 1.21.4 `custom_name='"Name"'`, ab 1.21.5 `custom_name="Name"`.

## Links

- Wiki: https://github.com/GeheimagentNr1/RecipesLibrary/wiki
- CurseForge: https://www.curseforge.com/minecraft/mc-mods/recipes-library, Modrinth: https://modrinth.com/mod/LG8SignH
- [`../Docs/migrations/1.21.1-to-1.21.2.md`](../Docs/migrations/1.21.1-to-1.21.2.md) 1 - 3 und 5, [`../Docs/migrations/1.21.11-to-26.1.md`](../Docs/migrations/1.21.11-to-26.1.md), [`../Docs/migrations/BREAKING_CHANGES.md`](../Docs/migrations/BREAKING_CHANGES.md)
