package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.RecipeSerializers;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.IShapedRecipe;

import javax.annotation.Nonnull;


public class ShapedNBTRecipe extends NBTRecipe implements IShapedRecipe<CraftingContainer> {
	
	
	public static final String registry_name = "crafting_shaped_nbt";
	
	private final int recipeWidth;
	
	private final int recipeHeight;
	
	//package-private
	ShapedNBTRecipe(
		ResourceLocation id,
		String group,
		NonNullList<Ingredient> ingredients,
		ItemStack result,
		boolean merge_nbt,
		int _recipeWidth,
		int _recipeHeight ) {
		
		super( id, group, ingredients, result, merge_nbt );
		recipeWidth = _recipeWidth;
		recipeHeight = _recipeHeight;
	}
	
	@Nonnull
	@Override
	public RecipeSerializer<?> getSerializer() {
		
		return RecipeSerializers.SHAPED_NBT;
	}
	
	@Override
	public boolean canCraftInDimensions( int width, int height ) {
		
		return width >= recipeWidth && height >= recipeHeight;
	}
	
	@Override
	public boolean matches( CraftingContainer container, @Nonnull Level level ) {
		
		for( int x = 0; x <= container.getWidth() - recipeWidth; x++ ) {
			for( int y = 0; y <= container.getHeight() - recipeHeight; y++ ) {
				if( checkMatch( container, x, y, true ) ) {
					return true;
				}
				if( checkMatch( container, x, y, false ) ) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkMatch( CraftingContainer container, int x0, int y0, boolean turned ) {
		
		NonNullList<Ingredient> ingredients = getIngredients();
		for( int x = 0; x < container.getWidth(); x++ ) {
			for( int y = 0; y < container.getHeight(); y++ ) {
				int dx = x - x0;
				int dy = y - y0;
				Ingredient ingredient = Ingredient.EMPTY;
				if( dx >= 0 && dy >= 0 && dx < recipeWidth && dy < recipeHeight ) {
					if( turned ) {
						ingredient = ingredients.get( recipeWidth - dx - 1 + dy * recipeWidth );
					} else {
						ingredient = ingredients.get( dx + dy * recipeWidth );
					}
				}
				if( !ingredient.test( container.getItem( x + y * container.getWidth() ) ) ) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public int getRecipeWidth() {
		
		return recipeWidth;
	}
	
	@Override
	public int getRecipeHeight() {
		
		return recipeHeight;
	}
}
