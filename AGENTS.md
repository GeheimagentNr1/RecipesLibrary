# AGENTS.md - Recipes Library

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
