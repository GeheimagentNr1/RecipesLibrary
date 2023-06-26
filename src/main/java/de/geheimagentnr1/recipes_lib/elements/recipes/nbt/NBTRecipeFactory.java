package de.geheimagentnr1.recipes_lib.elements.recipes.nbt;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;


@FunctionalInterface
public interface NBTRecipeFactory<R extends NBTRecipe> {
	
	
	//public
	R buildRecipe(
		@NotNull ResourceLocation recipeId,
		@NotNull String group,
		@NotNull NonNullList<Ingredient> ingredients,
		@NotNull ItemStack result,
		boolean merge_nbt );
}
