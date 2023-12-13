package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.ModRecipeSerializersRegisterFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeResult;
import lombok.Getter;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.IShapedRecipe;
import org.jetbrains.annotations.NotNull;


@Getter
public class ShapedNBTRecipe extends NBTRecipe implements IShapedRecipe<CraftingContainer> {
	
	
	public static final int MAX_WIDTH = 3;
	
	public static final int MAX_HEIGHT = 3;
	
	@NotNull
	public static final String registry_name = "crafting_shaped_nbt";
	
	@NotNull
	private final ShapedRecipePattern pattern;
	
	ShapedNBTRecipe(
		@NotNull String _group,
		@NotNull ShapedRecipePattern _pattern,
		@NotNull NBTRecipeResult _result ) {
		
		this( _group, _pattern, _result.buildItemStack(), _result.mergeNbt() );
	}
	
	ShapedNBTRecipe(
		@NotNull String _group,
		@NotNull ShapedRecipePattern _pattern,
		@NotNull ItemStack _result,
		boolean _merge_nbt ) {
		
		super( _group, _pattern.ingredients(), _result, _merge_nbt );
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
	public boolean matches( @NotNull CraftingContainer container, @NotNull Level level ) {
		
		return pattern.matches( container );
	}
	
	@Override
	public int getRecipeWidth() {
		
		return pattern.width();
	}
	
	@Override
	public int getRecipeHeight() {
		
		return pattern.height();
	}
}
