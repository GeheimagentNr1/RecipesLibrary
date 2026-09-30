package de.geheimagentnr1.recipes_lib.helpers;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ShaplessRecipesHelper {


	//Same matching as vanilla ShapelessRecipe#matches
	public static boolean matches(
		@NotNull CraftingRecipe recipe,
		@NotNull CraftingInput container,
		@NotNull List<Ingredient> ingredients,
		boolean isSimple ) {

		if( container.ingredientCount() != ingredients.size() ) {
			return false;
		}
		if( !isSimple ) {
			List<ItemStack> inputs = new ArrayList<>( container.ingredientCount() );
			for( ItemStack stack : container.items() ) {
				if( !stack.isEmpty() ) {
					inputs.add( stack );
				}
			}
			return RecipeMatcher.findMatches( inputs, ingredients ) != null;
		}
		return container.size() == 1 && ingredients.size() == 1
			? ingredients.getFirst().test( container.getItem( 0 ) )
			: container.stackedContents().canCraft( recipe, null );
	}
}
