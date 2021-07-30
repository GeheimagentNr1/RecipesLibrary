package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeFactory;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;


public class ShapedNBTRecipeFactory implements NBTRecipeFactory<ShapedNBTRecipe> {
	
	
	private final int width;
	
	private final int height;
	
	//package-private
	ShapedNBTRecipeFactory( int _width, int _height ) {
		
		width = _width;
		height = _height;
	}
	
	@Override
	public ShapedNBTRecipe buildRecipe(
		ResourceLocation recipeId,
		String group,
		NonNullList<Ingredient> ingredients,
		ItemStack result,
		boolean merge_nbt ) {
		
		return new ShapedNBTRecipe( recipeId, group, ingredients, result, merge_nbt, width, height );
	}
}
