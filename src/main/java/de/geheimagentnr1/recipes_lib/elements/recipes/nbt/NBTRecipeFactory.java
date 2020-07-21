package de.geheimagentnr1.recipes_lib.elements.recipes.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;


@FunctionalInterface
public interface NBTRecipeFactory<R extends NBTRecipe> {
	
	//public
	R buildRecipe( ResourceLocation recipeId, String group, NonNullList<Ingredient> ingredients,
		ItemStack result, boolean merge_nbt );
}
