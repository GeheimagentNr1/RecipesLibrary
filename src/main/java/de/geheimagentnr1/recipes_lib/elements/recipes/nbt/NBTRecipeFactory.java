package de.geheimagentnr1.recipes_lib.elements.recipes.nbt;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;


@FunctionalInterface
public interface NBTRecipeFactory<R extends NBTRecipe> {
	
	
	//public
	R buildRecipe(
		ResourceLocation recipeId,
		String group,
		NonNullList<Ingredient> ingredients,
		ItemStack result,
		boolean merge_nbt );
}
