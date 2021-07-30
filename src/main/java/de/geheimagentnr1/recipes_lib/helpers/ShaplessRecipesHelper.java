package de.geheimagentnr1.recipes_lib.helpers;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.List;


public class ShaplessRecipesHelper {
	
	
	public static boolean matches(
		CraftingRecipe recipe,
		CraftingContainer container,
		NonNullList<Ingredient> ingredients,
		boolean isSimple ) {
		
		StackedContents stackedContents = new StackedContents();
		List<ItemStack> inputs = new java.util.ArrayList<>();
		int inputCount = 0;
		
		for( int j = 0; j < container.getContainerSize(); j++ ) {
			ItemStack stack = container.getItem( j );
			if( !stack.isEmpty() ) {
				inputCount++;
				if( isSimple ) {
					stackedContents.accountStack( stack, 1 );
				} else {
					inputs.add( stack );
				}
			}
		}
		return inputCount == ingredients.size() && ( isSimple
			? stackedContents.canCraft( recipe, null )
			: RecipeMatcher.findMatches( inputs, ingredients ) != null );
	}
}
