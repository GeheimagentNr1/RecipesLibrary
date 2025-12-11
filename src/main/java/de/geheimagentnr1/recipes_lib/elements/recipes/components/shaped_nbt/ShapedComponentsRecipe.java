package de.geheimagentnr1.recipes_lib.elements.recipes.components.shaped_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.ModRecipeSerializersRegisterFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.ComponentsRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.ComponentsRecipeResult;
import lombok.Getter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;


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
		
		this( _group, _pattern, _result.buildItemStack(), _result.mergeComponents() );
	}
	
	ShapedComponentsRecipe(
		@NotNull String _group,
		@NotNull ShapedRecipePattern _pattern,
		@NotNull ItemStack _result,
		boolean _merge_components ) {
		
		super( _group, _pattern.ingredients(), _result, _merge_components );
		pattern = _pattern;
	}
	
	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		
		return ModRecipeSerializersRegisterFactory.SHAPED_NBT;
	}
	
	@Override
	public boolean canCraftInDimensions( int width, int height ) {
		
		return width >= pattern.width() && height >= pattern.height();
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
