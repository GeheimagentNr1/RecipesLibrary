package de.geheimagentnr1.recipes_lib.elements.recipes.renaming;

import de.geheimagentnr1.recipes_lib.elements.recipes.ModRecipeSerializersRegisterFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.components.MatchType;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.components.ComponentsIngredient;
import de.geheimagentnr1.recipes_lib.helpers.ShaplessRecipesHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public class RenamingRecipe implements CraftingRecipe {
	
	
	@NotNull
	public static final String registry_name = "renaming";
	
	@NotNull
	private final Ingredient ingredient;
	
	@NotNull
	private final NonNullList<Ingredient> ingredients;
	
	private final boolean isSimple;
	
	//package-private
	RenamingRecipe( @NotNull Ingredient _ingredient ) {
		
		ingredient = _ingredient;
		ingredients = NonNullList.create();
		ingredients.addAll( Arrays.asList( buildNameTagIngredient(), ingredient ) );
		isSimple = ingredients.stream().allMatch( Ingredient::isSimple );
	}
	
	
	@NotNull
	private Ingredient buildNameTagIngredient() {
		
		ItemStack stack = new ItemStack( Items.NAME_TAG );
		stack.set( DataComponents.CUSTOM_NAME, null );
		return ComponentsIngredient.fromStack( stack, MatchType.CONTAINS, true );
	}
	
	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		
		return ModRecipeSerializersRegisterFactory.RENAMING;
	}
	
	@NotNull
	@Override
	public ItemStack getResultItem( @NotNull HolderLookup.Provider pRegistries ) {
		
		return ingredient.getItems()[0];
	}
	
	@NotNull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		
		return ingredients;
	}
	
	@Override
	public boolean matches( @NotNull CraftingContainer container, @NotNull Level level ) {
		
		return ShaplessRecipesHelper.matches( this, container, ingredients, isSimple );
	}
	
	@NotNull
	@Override
	public ItemStack assemble(
		@NotNull CraftingContainer pCraftingContainer,
		@NotNull HolderLookup.Provider pRegistries ) {
		
		ItemStack result = ItemStack.EMPTY;
		Component resultDisplayName = null;
		for( int j = 0; j < pCraftingContainer.getContainerSize(); j++ ) {
			ItemStack stack = pCraftingContainer.getItem( j );
			if( !stack.isEmpty() && stack.getItem() != Items.NAME_TAG ) {
				result = stack.copy();
			}
		}
		for( int j = 0; j < pCraftingContainer.getContainerSize(); j++ ) {
			ItemStack stack = pCraftingContainer.getItem( j );
			if( stack.getItem() == Items.NAME_TAG ) {
				resultDisplayName = stack.getHoverName();
			}
		}
		if( result.getHoverName().equals( resultDisplayName ) ) {
			return ItemStack.EMPTY;
		}
		result.set( DataComponents.CUSTOM_NAME, resultDisplayName );
		return result;
	}
	
	@Override
	public boolean canCraftInDimensions( int width, int height ) {
		
		return width * height >= ingredients.size();
	}
	
	@NotNull
	@Override
	public CraftingBookCategory category() {
		
		return CraftingBookCategory.MISC;
	}
	
	@NotNull
	public Ingredient getIngredient() {
		
		return ingredient;
	}
}
