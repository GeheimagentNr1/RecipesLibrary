package de.geheimagentnr1.recipes_lib.helpers;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.List;


public class ShaplessRecipesHelper {
	
	
	public static boolean matches(
		ICraftingRecipe recipe,
		CraftingInventory inv,
		NonNullList<Ingredient> ingredients,
		boolean isSimple ) {
		
		RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
		List<ItemStack> inputs = new java.util.ArrayList<>();
		int inputCout = 0;
		
		for( int j = 0; j < inv.getSizeInventory(); j++ ) {
			ItemStack itemstack = inv.getStackInSlot( j );
			if( !itemstack.isEmpty() ) {
				inputCout++;
				if( isSimple ) {
					recipeitemhelper.func_221264_a( itemstack, 1 );
				} else {
					inputs.add( itemstack );
				}
			}
		}
		return inputCout == ingredients.size() && ( isSimple
			? recipeitemhelper.canCraft( recipe, null )
			: RecipeMatcher.findMatches( inputs, ingredients ) != null );
	}
}
