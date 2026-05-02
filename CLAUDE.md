# CLAUDE.md - Recipes Library

## Projekt-Übersicht

**Recipes Library** ist ein NeoForge Minecraft Mod für Minecraft 1.21.1.
- **Mod ID**: `recipes_lib`
- **Package**: `de.geheimagentnr1.recipes_lib`
- **Java Version**: 21
- **NeoForge Version**: 21.1.x

Eine Library, die Implementierungen für Rezepte bereitstellt.

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
│       └── ingredients/
│           └── ModIngredientSerializersRegisterFactory.java
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
# Java 21 für MC 1.20.5+ (NeoForge)
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.9.10-hotspot"
./gradlew build
```

### Unit Tests (JUnit 5)

Für reine Logik-Tests ohne Minecraft-Abhängigkeiten:

```bash
./gradlew test
```

Tests liegen unter `src/test/java/`. Ergebnisse: `build/reports/tests/test/index.html`

### NeoForge GameTest Framework

Für Integration Tests in einer echten Minecraft-Umgebung:

```bash
./gradlew runGameTestServer
```

GameTest-Klassen werden mit `@GameTestHolder` annotiert und liegen unter `src/main/java/.../elements/gametests/`.

### CI/CD (GitHub Actions)

Der Workflow `.github/workflows/build-and-test.yml` führt automatisch aus:
1. **Build**: Kompiliert den Mod
2. **Unit Tests**: Führt JUnit Tests aus
3. **GameTests**: Startet GameTestServer (optional)

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
