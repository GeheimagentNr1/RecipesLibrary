package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.RecipeSerializers;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipe;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;

import javax.annotation.Nonnull;


public class ShapedNBTRecipe extends NBTRecipe implements IShapedRecipe<CraftingInventory> {
	
	
	public static final String registry_name = "crafting_shaped_nbt";
	
	private final int recipeWidth;
	
	private final int recipeHeight;
	
	//package-private
	ShapedNBTRecipe( ResourceLocation id, String group, NonNullList<Ingredient> ingredients, ItemStack result,
		boolean merge_nbt, int _recipeWidth, int _recipeHeight ) {
		
		super( id, group, ingredients, result, merge_nbt );
		recipeWidth = _recipeWidth;
		recipeHeight = _recipeHeight;
	}
	
	@Nonnull
	@Override
	public IRecipeSerializer<?> getSerializer() {
		
		return RecipeSerializers.SHAPED_NBT;
	}
	
	@Override
	public boolean canFit( int width, int height ) {
		
		return width >= recipeWidth && height >= recipeHeight;
	}
	
	@Override
	public boolean matches( CraftingInventory inv, @Nonnull World worldIn ) {
		
		for( int x = 0; x <= inv.getWidth() - recipeWidth; x++ ) {
			for( int y = 0; y <= inv.getHeight() - recipeHeight; y++ ) {
				if( checkMatch( inv, x, y, true ) ) {
					return true;
				}
				if( checkMatch( inv, x, y, false ) ) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkMatch( CraftingInventory craftingInventory, int x0, int y0, boolean turned ) {
		
		NonNullList<Ingredient> ingredients = getIngredients();
		for( int x = 0; x < craftingInventory.getWidth(); x++ ) {
			for( int y = 0; y < craftingInventory.getHeight(); y++ ) {
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
				if( !ingredient.test( craftingInventory.getStackInSlot( x + y * craftingInventory.getWidth() ) ) ) {
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
