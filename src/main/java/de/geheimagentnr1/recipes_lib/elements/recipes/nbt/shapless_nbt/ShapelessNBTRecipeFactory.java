package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeFactory;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;


public class ShapelessNBTRecipeFactory implements NBTRecipeFactory<ShapelessNBTRecipe> {
	
	
	@Override
	public ShapelessNBTRecipe buildRecipe(
		@NotNull ResourceLocation recipeId,
		@NotNull String group,
		@NotNull NonNullList<Ingredient> ingredients,
		@NotNull ItemStack result,
		boolean merge_nbt ) {
		
		return new ShapelessNBTRecipe( recipeId, group, ingredients, result, merge_nbt );
	}
}
