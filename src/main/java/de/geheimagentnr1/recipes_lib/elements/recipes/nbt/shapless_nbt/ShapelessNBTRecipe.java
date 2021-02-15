package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.RecipeSerializers;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipe;
import de.geheimagentnr1.recipes_lib.helpers.ShaplessRecipesHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


public class ShapelessNBTRecipe extends NBTRecipe {
	
	
	public static final String registry_name = "crafting_shapeless_nbt";
	
	private final boolean isSimple;
	
	//package-private
	ShapelessNBTRecipe(
		ResourceLocation idIn,
		String groupIn,
		NonNullList<Ingredient> _ingredients,
		ItemStack _result,
		boolean _merge_nbt ) {
		
		super( idIn, groupIn, _ingredients, _result, _merge_nbt );
		isSimple = _ingredients.stream().allMatch( Ingredient::isSimple );
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
	public boolean matches( @Nonnull CraftingInventory inv, @Nonnull World worldIn ) {
		
		NonNullList<Ingredient> ingredients = getIngredients();
		return ShaplessRecipesHelper.matches( this, inv, ingredients, isSimple );
	}
}