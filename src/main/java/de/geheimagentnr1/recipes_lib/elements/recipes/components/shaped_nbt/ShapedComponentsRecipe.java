package de.geheimagentnr1.recipes_lib.elements.recipes.components.shaped_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.ModRecipeSerializersRegisterFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.ComponentsRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.ComponentsRecipeResult;
import lombok.Getter;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;


@Getter
public class ShapedComponentsRecipe extends ComponentsRecipe {


	public static final int MAX_WIDTH = 3;

	public static final int MAX_HEIGHT = 3;

	@NotNull
	public static final String registry_name = "crafting_shaped_components";

	@NotNull
	private final ShapedRecipePattern pattern;

	ShapedComponentsRecipe(
		@NotNull String _group,
		@NotNull ShapedRecipePattern _pattern,
		@NotNull ComponentsRecipeResult _result ) {

		this( _group, _pattern, _result.buildTemplate(), _result.mergeComponents() );
	}

	ShapedComponentsRecipe(
		@NotNull String _group,
		@NotNull ShapedRecipePattern _pattern,
		@NotNull ItemStackTemplate _result,
		boolean _merge_components ) {

		super( _group, _result, _merge_components );
		pattern = _pattern;
	}

	@NotNull
	@Override
	public RecipeSerializer<ShapedComponentsRecipe> getSerializer() {

		return ModRecipeSerializersRegisterFactory.SHAPED_NBT;
	}

	@NotNull
	@Override
	protected PlacementInfo createPlacementInfo() {

		return PlacementInfo.createFromOptionals( pattern.ingredients() );
	}

	@NotNull
	@Override
	public List<RecipeDisplay> display() {

		return List.of(
			new ShapedCraftingRecipeDisplay(
				pattern.width(),
				pattern.height(),
				pattern.ingredients()
					.stream()
					.map( ingredient -> ingredient.map( Ingredient::display ).orElse( SlotDisplay.Empty.INSTANCE ) )
					.toList(),
				resultDisplay(),
				craftingStationDisplay()
			)
		);
	}

	@Override
	public boolean matches( @NotNull CraftingInput container, @NotNull Level level ) {

		return pattern.matches( container );
	}

	public int getRecipeWidth() {

		return pattern.width();
	}

	public int getRecipeHeight() {

		return pattern.height();
	}
}
