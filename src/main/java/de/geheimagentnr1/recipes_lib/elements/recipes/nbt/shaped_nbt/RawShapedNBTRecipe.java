package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.RawNBTRecipeResult;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Map;


public record RawShapedNBTRecipe(String group,
                                 Map<String, Ingredient> key,
                                 List<String> pattern,
                                 RawNBTRecipeResult result) {
	
	
}
