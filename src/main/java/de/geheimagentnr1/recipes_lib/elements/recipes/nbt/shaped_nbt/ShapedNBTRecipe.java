package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.ModRecipeSerializersRegisterFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.IShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ShapedNBTRecipe extends NBTRecipe implements IShapedRecipe<CraftingContainer> {
	
	
	public static final int MAX_WIDTH = 3;
	
	public static final int MAX_HEIGHT = 3;
	
	@NotNull
	public static final String registry_name = "crafting_shaped_nbt";
	
	private final int recipeWidth;
	
	private final int recipeHeight;
	
	//package-private
	ShapedNBTRecipe(
		@NotNull String _group,
		@NotNull NonNullList<Ingredient> _ingredients,
		@NotNull ItemStack _result,
		boolean _merge_nbt,
		int _recipeWidth,
		int _recipeHeight ) {
		
		super( _group, _ingredients, _result, _merge_nbt );
		recipeWidth = _recipeWidth;
		recipeHeight = _recipeHeight;
	}
	
	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		
		return ModRecipeSerializersRegisterFactory.SHAPED_NBT;
	}
	
	@Override
	public boolean canCraftInDimensions( int width, int height ) {
		
		return width >= recipeWidth && height >= recipeHeight;
	}
	
	@Override
	public boolean matches( @NotNull CraftingContainer container, @NotNull Level level ) {
		
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
	
	private boolean checkMatch( @NotNull CraftingContainer container, int x0, int y0, boolean turned ) {
		
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
	
	static String[] shrink( List<String> p_299210_ ) {
		
		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;
		
		for( int i1 = 0; i1 < p_299210_.size(); ++i1 ) {
			String s = p_299210_.get( i1 );
			i = Math.min( i, firstNonSpace( s ) );
			int j1 = lastNonSpace( s );
			j = Math.max( j, j1 );
			if( j1 < 0 ) {
				if( k == i1 ) {
					++k;
				}
				
				++l;
			} else {
				l = 0;
			}
		}
		
		if( p_299210_.size() == l ) {
			return new String[0];
		} else {
			String[] astring = new String[p_299210_.size() - l - k];
			
			for( int k1 = 0; k1 < astring.length; ++k1 ) {
				astring[k1] = p_299210_.get( k1 + k ).substring( i, j + 1 );
			}
			
			return astring;
		}
	}
	
	private static int firstNonSpace( String p_44185_ ) {
		
		int i;
		for( i = 0; i < p_44185_.length() && p_44185_.charAt( i ) == ' '; ++i ) {
		}
		
		return i;
	}
	
	private static int lastNonSpace( String p_44201_ ) {
		
		int i;
		for( i = p_44201_.length() - 1; i >= 0 && p_44201_.charAt( i ) == ' '; --i ) {
		}
		
		return i;
	}
}
