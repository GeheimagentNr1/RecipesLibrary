package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.ModRecipeSerializersRegisterFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipe;
import de.geheimagentnr1.recipes_lib.helpers.ShaplessRecipesHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;


public class ShapelessNBTRecipe extends NBTRecipe {
	
	
	@NotNull
	public static final String registry_name = "crafting_shapeless_nbt";
	
	private final boolean isSimple;
	
	//package-private
	ShapelessNBTRecipe(
		@NotNull String _group,
		@NotNull NonNullList<Ingredient> _ingredients,
		@NotNull ItemStack _result,
		boolean _merge_nbt ) {
		
		super( _group, _ingredients, _result, _merge_nbt );
		isSimple = _ingredients.stream().allMatch( Ingredient::isSimple );
	}
	
	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		
		return ModRecipeSerializersRegisterFactory.SHAPELESS_NBT;
	}
	
	@Override
	public boolean canCraftInDimensions( int width, int height ) {
		
		return width * height >= getIngredients().size();
	}
	
	@Override
	public boolean matches( @NotNull CraftingContainer container, @NotNull Level level ) {
		
		NonNullList<Ingredient> ingredients = getIngredients();
		return ShaplessRecipesHelper.matches( this, container, ingredients, isSimple );
	}
}