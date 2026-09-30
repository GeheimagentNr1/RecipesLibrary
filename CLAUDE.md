# CLAUDE.md - Recipes Library

## Projekt-Übersicht

**Recipes Library** ist ein NeoForge Minecraft Mod für Minecraft 26.1 - 26.3 (Branch `develop_26.1`; ältere Versionen siehe Tabelle).
- **Mod ID**: `recipes_lib`
- **Package**: `de.geheimagentnr1.recipes_lib`
- **Java Version**: 25 (Gradle-Wrapper 9.2.1, Lombok 1.18.48)
- **NeoForge Version**: kompiliert gegen `26.1.0.19-beta` (niedrigste Zielversion), `neoforge_version_range=[26.1,)`
- **Minecraft-Range**: `[26.1,27)` - ein Jar für 26.1, 26.1.1, 26.1.2, 26.2, 26.3 (getestet 2026-09-30)

| Branch | MC | Range | NeoForge (kompiliert gegen) | Java |
|---|---|---|---|---|
| `develop_1.21.1` | 1.21.1 | `[1.21.1,1.21.2)` | 21.1.x | 21 |
| `develop_1.21.2` | 1.21.2 - 1.21.10 | `[1.21.2,1.21.11)` | `21.2.1-beta` | 21 |
| `develop_1.21.11` | 1.21.11 | `[1.21.11,1.21.12)` | `21.11.45` | 21 |
| `develop_26.1` | 26.1 - 26.3 | `[26.1,27)` | `26.1.0.19-beta` | 25 |

**Ab 26.1:** Rezepte werden geparst, bevor Item-Komponenten gebunden sind. Rezept-Ergebnisse und `ComponentsIngredient` speichern deshalb `ItemStackTemplate` und erzeugen den `ItemStack` erst in `assemble()` bzw. beim ersten `test()`. Beim Parsen nie einen `ItemStack` erzeugen (`Components not bound yet`), siehe `../Docs/migrations/1.21.11-to-26.1.md`. `RecipeSerializer` ist ein Record; `ComponentsRecipeSerializer`/`RenamingRecipeSerializer` bauen ihn nur noch (`createSerializer()`).

Eine Library, die Implementierungen für Rezepte bereitstellt:

| JSON-Typ | Klasse | Zweck |
|---|---|---|
| `recipes_lib:crafting_shaped_components` | `ShapedComponentsRecipe` | Shaped-Rezept mit Komponenten im Ergebnis, optional Komponenten der Zutat übernehmen (`merge_components`) |
| `recipes_lib:crafting_shapeless_components` | `ShapelessComponentsRecipe` | wie oben, shapeless |
| `recipes_lib:renaming` | `RenamingRecipe` | Item + benanntes Namensschild → Item mit diesem Namen |
| Ingredient `recipes_lib:components` | `ComponentsIngredient` | Item mit Komponenten-Vergleich (`MatchType`: `EQUAL`, `CONTAINS`, `CONTAINS_NONE`, `NOT_EQUAL`) |

Abhängige Mods (DynamicalCompass, ManyIdeasDoors, ManyIdeasChristmas) nutzen **nur die JSON-Typen**, keine Java-API. Seit 1.21.2 wird der Ingredient-Typ im JSON über `"neoforge:ingredient_type"` gewählt (vorher `"type"`), siehe `../Docs/migrations/1.21.1-to-1.21.2.md`. Getestete Beispiele für alle Typen: `../Docs/testing/datapacks/recipes_lib_test/`.

`develop_1.21.3` ist ein veralteter Branch aus der Forge-Zeit (nur Versionsnummern) und kann ignoriert werden. `wip_1.21.2_first_attempt` enthält den verworfenen ersten 1.21.2-Versuch (nur lokal, als Referenz).

## Abhängigkeiten

Keine Mod-Abhängigkeiten - dies ist eine eigenständige Library.

## Projektstruktur

```
src/main/java/de/geheimagentnr1/recipes_lib/
├── RecipesLibrary.java                    # Haupt-Mod-Klasse
├── elements/
│   └── recipes/
│       ├── EnumCodec.java                 # Codec für Enums
│       ├── ModRecipeSerializersRegisterFactory.java
│       ├── components/                    # ComponentsRecipe (Basis), shaped_nbt/, shapless_nbt/
│       ├── renaming/                      # RenamingRecipe + Serializer
│       └── ingredients/
│           ├── ModIngredientSerializersRegisterFactory.java
│           └── components/                # ComponentsIngredient, Codec, MatchType
├── helpers/
│   └── ShaplessRecipesHelper.java         # Helper für formlose Rezepte
└── util/
    ├── JSONUtil.java                      # JSON-Utilities
    └── Pair.java                          # Pair-Utility-Klasse
```

## Architektur

Dieser Mod nutzt **nicht** `AbstractMod` aus ManyIdeas Core, sondern ist eigenständig:
```java
@Mod( RecipesLibrary.MODID )
public class RecipesLibrary {
    public RecipesLibrary( @NotNull IEventBus modEventBus ) {
        new ModIngredientSerializersRegisterFactory().register( modEventBus );
        modEventBus.register( new ModRecipeSerializersRegisterFactory() );
    }
}
```

## Besonderheiten

- **Recipe Serializers**: Custom Rezept-Serializer
- **Ingredient Serializers**: Custom Zutaten-Serializer
- **Utility-Klassen**: JSONUtil, Pair, EnumCodec

## Code-Stil

- **Annotations**: `@NotNull` aus `org.jetbrains.annotations`
- **Lombok**: Projekt nutzt Lombok
- **Formatierung**: Leerzeichen nach `(` und vor `)` bei Methodenaufrufen

## Build & Test

```bash
./gradlew build
./gradlew runClient
./gradlew runServer
```

## Deployment

- **CurseForge**: `./gradlew curseforge`
- **Modrinth**: `./gradlew modrinth`

## Wichtige Hinweise

1. **Library-Mod**: Wird von anderen Mods als Abhängigkeit genutzt
2. **Eigenständig**: Nutzt nicht das ManyIdeas Core Framework

## Testing

### Java-Versionen

Verschiedene Java-Versionen sind unter `C:\Program Files\Eclipse Adoptium` installiert. Für einen Gradle-Build muss die passende Java-Version gewählt werden:

```powershell
# Java 25 für MC 26.x (develop_26.1); Java 21 (jdk-21.0.12.8-hotspot) für die 1.21.x-Branches
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-25.0.4.7-hotspot"
./gradlew build

# Kompatibilität gegen weitere Versionen im Bereich prüfen (baut kein zusätzliches Jar)
./gradlew compileJava compileTestJava --rerun-tasks -Pminecraft_version=26.3 -Pneoforge_version=26.3.0.36-beta -Pmapping_version=26.3
```

### Rezept-Tests

Standalone-Test-Datapack mit Vanilla-Items: `../Docs/testing/datapacks/recipes_lib_test/` (README enthält Test-Tabelle und `/give`-Befehle). In `<Testpack>\world\datapacks\` kopieren, Server starten, `datapack list` prüfen und ingame craften.

### Unit Tests (JUnit 5)

Für reine Logik-Tests ohne Minecraft-Abhängigkeiten:

```bash
./gradlew test
```

Tests liegen unter `src/test/java/`. Ergebnisse: `build/reports/tests/test/index.html`

### NeoForge GameTest Framework

Für Integration Tests in einer echten Minecraft-Umgebung:

Auf `develop_1.21.2`, `develop_1.21.11` und `develop_26.1` gibt es keine GameTests: Das Annotations-Framework (`@GameTest`, `@GameTestHolder`) existiert ab 1.21.5 nicht mehr, der triviale Smoke-Test wurde samt `gameTestServer`-Run-Config und CI-Job entfernt (siehe `../Docs/migrations/1.21.10-to-1.21.11.md`).

### CI/CD (GitHub Actions)

Der Workflow `.github/workflows/build-and-test.yml` führt automatisch aus:
1. **Build**: Kompiliert den Mod
2. **Unit Tests**: Führt JUnit Tests aus (JUnit-Abhängigkeit seit `develop_1.21.2` vorhanden, vorher kompilierte der Test nicht)

### Was kann automatisiert getestet werden?

| Aspekt | Automatisiert? | Methode |
|--------|----------------|---------|
| Utility-Klassen | ✅ | JUnit |
| Config-Parsing | ✅ | JUnit |
| Commands | ✅ | GameTest |
| Block/Item-Verhalten | ✅ | GameTest |
| Multi-MC-Version | ⚠️ Pro Branch | CI Matrix |

## Referenzen

- [NeoForge Migration Primer](https://docs.neoforged.net/primer/docs/) — Dokumentiert API-Aenderungen zwischen Minecraft/NeoForge-Versionen; nuetzlich fuer die Pruefung von Breaking Changes beim Upgrade auf neue Versionen

---

## Wissensdatenbank

Versionsübergreifende Migrations- und Entwicklungs-Erkenntnisse (Breaking Changes, Fixes, Testumgebungs-Patterns) werden zentral in [`../Docs/`](../Docs/) gepflegt. Bei neuen relevanten Erkenntnissen dort ergänzen, nicht nur hier.
