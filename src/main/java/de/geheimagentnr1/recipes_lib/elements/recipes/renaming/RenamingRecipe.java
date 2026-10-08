package de.geheimagentnr1.recipes_lib.elements.recipes.renaming;

import de.geheimagentnr1.recipes_lib.elements.recipes.ModRecipeSerializersRegisterFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.components.MatchType;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.components.ComponentsIngredient;
import de.geheimagentnr1.recipes_lib.helpers.ShaplessRecipesHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;


public class RenamingRecipe implements CraftingRecipe {


	@NotNull
	public static final String registry_name = "renaming";
	
	//Example name, so recipe viewers show, that the name of the name tag is used.
	@NotNull
	private static final Component EXAMPLE_NAME = Component.literal( "Name" );

	@NotNull
	private final Ingredient ingredient;

	@NotNull
	private final List<Ingredient> ingredients;

	private final boolean isSimple;

	@Nullable
	private PlacementInfo placementInfo;

	//package-private
	RenamingRecipe( @NotNull Ingredient _ingredient ) {

		ingredient = _ingredient;
		ingredients = List.of( buildNameTagIngredient(), ingredient );
		isSimple = ingredients.stream().allMatch( Ingredient::isSimple );
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
	private SlotDisplay buildExampleResult() {
		
		return ingredient.items().findFirst().<SlotDisplay>map( item -> {
			ItemStack stack = new ItemStack( item );
			stack.set( DataComponents.CUSTOM_NAME, EXAMPLE_NAME );
			return new SlotDisplay.ItemStackSlotDisplay( stack );
		} ).orElseGet( ingredient::display );
	}
	
	@NotNull
	@Override
	public RecipeSerializer<RenamingRecipe> getSerializer() {

		return ModRecipeSerializersRegisterFactory.RENAMING;
	}

	@NotNull
	@Override
	public PlacementInfo placementInfo() {

		if( placementInfo == null ) {
			placementInfo = PlacementInfo.create( ingredients );
		}
		return placementInfo;
	}

	@NotNull
	@Override
	public List<RecipeDisplay> display() {

		return List.of(
			new ShapelessCraftingRecipeDisplay(
				List.of( new SlotDisplay.ItemStackSlotDisplay( buildExampleNameTag() ), ingredient.display() ),
				buildExampleResult(),
				new SlotDisplay.ItemSlotDisplay( Items.CRAFTING_TABLE )
			)
		);
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
