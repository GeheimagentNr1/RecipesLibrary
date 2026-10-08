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
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public class RenamingRecipe implements CraftingRecipe {
	
	
	@NotNull
	public static final String registry_name = "renaming";
	
	//Example name, so recipe viewers show, that the name of the name tag is used.
	@NotNull
	private static final Component EXAMPLE_NAME = Component.literal( "Name" );
	
	@NotNull
	private final Ingredient ingredient;
	
	@NotNull
	private final NonNullList<Ingredient> ingredients;
	
	@NotNull
	private final NonNullList<Ingredient> displayIngredients;
	
	private final boolean isSimple;
	
	//package-private
	RenamingRecipe( @NotNull Ingredient _ingredient ) {
		
		ingredient = _ingredient;
		ingredients = NonNullList.create();
		ingredients.addAll( Arrays.asList( buildNameTagIngredient(), ingredient ) );
		isSimple = ingredients.stream().allMatch( Ingredient::isSimple );
		displayIngredients = NonNullList.create();
		displayIngredients.addAll( Arrays.asList( Ingredient.of( buildExampleNameTag() ), ingredient ) );
	}
	
	
	@NotNull
	private Ingredient buildNameTagIngredient() {
		
		ItemStack stack = new ItemStack( Items.NAME_TAG );
		stack.set( DataComponents.CUSTOM_NAME, null );
		return ComponentsIngredient.fromStack( stack, MatchType.CONTAINS, true ).toVanilla();
	}
	
	@NotNull
	private ItemStack buildExampleNameTag() {
		
		ItemStack stack = new ItemStack( Items.NAME_TAG );
		stack.set( DataComponents.CUSTOM_NAME, EXAMPLE_NAME );
		return stack;
	}
	
	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		
		return ModRecipeSerializersRegisterFactory.RENAMING;
	}
	
	@NotNull
	@Override
	public ItemStack getResultItem( @NotNull HolderLookup.Provider pRegistries ) {
		
		ItemStack result = ingredient.getItems()[0].copy();
		result.set( DataComponents.CUSTOM_NAME, EXAMPLE_NAME );
		return result;
	}
	
	@NotNull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		
		return displayIngredients;
	}
	
	@Override
	public boolean matches( @NotNull CraftingInput container, @NotNull Level level ) {
		
		return ShaplessRecipesHelper.matches( this, container, ingredients, isSimple );
	}
	
	@NotNull
	@Override
	public ItemStack assemble(
		@NotNull CraftingInput pCraftingContainer,
		@NotNull HolderLookup.Provider pRegistries ) {
		
		ItemStack result = ItemStack.EMPTY;
		Component resultDisplayName = null;
		for( int j = 0; j < pCraftingContainer.size(); j++ ) {
			ItemStack stack = pCraftingContainer.getItem( j );
			if( !stack.isEmpty() && stack.getItem() != Items.NAME_TAG ) {
				result = stack.copy();
			}
		}
		for( int j = 0; j < pCraftingContainer.size(); j++ ) {
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
