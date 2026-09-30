package de.geheimagentnr1.recipes_lib.elements.recipes.components.shapless_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.ModRecipeSerializersRegisterFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.ComponentsRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.ComponentsRecipeResult;
import de.geheimagentnr1.recipes_lib.helpers.ShaplessRecipesHelper;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ShapelessComponentsRecipe extends ComponentsRecipe {


	@NotNull
	public static final String registry_name = "crafting_shapeless_components";

	@NotNull
	private final List<Ingredient> ingredients;

	private final boolean isSimple;

	//package-private
	ShapelessComponentsRecipe(
		@NotNull String _group,
		@NotNull List<Ingredient> _ingredients,
		@NotNull ComponentsRecipeResult result ) {

		this( _group, _ingredients, result.buildTemplate(), result.mergeComponents() );
	}

	//package-private
	ShapelessComponentsRecipe(
		@NotNull String _group,
		@NotNull List<Ingredient> _ingredients,
		@NotNull ItemStackTemplate _result,
		boolean _merge_components ) {

		super( _group, _result, _merge_components );
		ingredients = _ingredients;
		isSimple = _ingredients.stream().allMatch( Ingredient::isSimple );
	}

	@NotNull
	@Override
	public RecipeSerializer<ShapelessComponentsRecipe> getSerializer() {

		return ModRecipeSerializersRegisterFactory.SHAPELESS_NBT;
	}

	@NotNull
	@Override
	protected PlacementInfo createPlacementInfo() {

		return PlacementInfo.create( ingredients );
	}

	@NotNull
	@Override
	public List<RecipeDisplay> display() {

		return List.of(
			new ShapelessCraftingRecipeDisplay(
				ingredients.stream().map( Ingredient::display ).toList(),
				resultDisplay(),
				craftingStationDisplay()
			)
		);
	}

	@Override
	public boolean matches( @NotNull CraftingInput container, @NotNull Level level ) {

		return ShaplessRecipesHelper.matches( this, container, ingredients, isSimple );
	}

	@NotNull
	public List<Ingredient> getIngredients() {

		return ingredients;
	}
}
