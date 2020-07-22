package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.RecipeSerializers;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipe;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


public class ShapelessNBTRecipe extends NBTRecipe {
	
	
	public static final String registry_name = "crafting_shapeless_nbt";
	
	private final boolean isSimple;
	
	public ShapelessNBTRecipe( ResourceLocation id, String group, NonNullList<Ingredient> ingredients,
		ItemStack result, boolean merge_nbt ) {
		
		super( id, group, ingredients, result, merge_nbt );
		isSimple = ingredients.stream().allMatch( Ingredient::isSimple );
	}
	
	@Nonnull
	@Override
	public IRecipeSerializer<?> getSerializer() {
		
		return RecipeSerializers.SHAPELESS_NBT;
	}
	
	@Override
	public boolean canFit( int width, int height ) {
		
		return width * height >= getIngredients().size();
	}
	
	@Override
	public boolean matches( CraftingInventory inv, @Nonnull World worldIn ) {
		
		NonNullList<Ingredient> ingredients = getIngredients();
		RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
		List<ItemStack> inputs = new ArrayList<>();
		int i = 0;
		
		for( int j = 0; j < inv.getSizeInventory(); j++ ) {
			ItemStack itemstack = inv.getStackInSlot( j );
			if( !itemstack.isEmpty() ) {
				i++;
				if( isSimple ) {
					recipeitemhelper.func_221264_a( itemstack, 1 );
				} else {
					inputs.add( itemstack );
				}
			}
		}
		return i == ingredients.size() && ( isSimple ? recipeitemhelper.canCraft( this, null ) :
			RecipeMatcher.findMatches( inputs, ingredients ) != null );
	}
}