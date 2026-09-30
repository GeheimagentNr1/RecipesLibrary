Add compatibility for minecraft version 1.21.2, 1.21.3, 1.21.4, 1.21.5, 1.21.6, 1.21.7, 1.21.8, 1.21.9, 1.21.10

Notes for data pack and mod authors:
- Since NeoForge 21.2 the `recipes_lib:components` ingredient is selected with `"neoforge:ingredient_type": "recipes_lib:components"` instead of `"type"`. Vanilla ingredients are written as plain strings, e.g. `"minecraft:stick"` instead of `{ "item": "minecraft:stick" }`.
- Recipes of all RecipesLibrary recipe types are shown in the recipe book.