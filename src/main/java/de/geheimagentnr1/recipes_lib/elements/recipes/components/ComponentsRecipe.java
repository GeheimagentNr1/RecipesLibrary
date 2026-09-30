package de.geheimagentnr1.recipes_lib.elements.recipes.components;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


public abstract class ComponentsRecipe implements CraftingRecipe {


	@NotNull
	private final String group;

	@NotNull
	private final ItemStack result;

	private final boolean merge_components;

	@Nullable
	private PlacementInfo placementInfo;

	protected ComponentsRecipe(
		@NotNull String _group,
		@NotNull ItemStack _result,
		boolean _merge_components ) {

		group = _group;
		result = _result;
		merge_components = _merge_components;
	}

	@NotNull
	@Override
	public String group() {

		return group;
	}

	@NotNull
	@Override
	public PlacementInfo placementInfo() {

		if( placementInfo == null ) {
			placementInfo = createPlacementInfo();
		}
		return placementInfo;
	}

	@NotNull
	protected abstract PlacementInfo createPlacementInfo();

	@NotNull
	protected SlotDisplay resultDisplay() {

		return new SlotDisplay.ItemStackSlotDisplay( result );
	}

	@NotNull
	protected static SlotDisplay craftingStationDisplay() {

		return new SlotDisplay.ItemSlotDisplay( Items.CRAFTING_TABLE );
	}

	@NotNull
	@Override
	public ItemStack assemble(
		@NotNull CraftingInput pCraftingContainer,
		@NotNull HolderLookup.Provider pRegistries ) {
		
		if( merge_components ) {
			for( int j = 0; j < pCraftingContainer.size(); j++ ) {
				ItemStack itemstack = pCraftingContainer.getItem( j );
				if( itemstack.getItem() == result.getItem() ) {
					ItemStack resultStack = result.copy();
					resultStack.applyComponentsAndValidate( itemstack.getComponentsPatch() );
					return resultStack;
				}
			}
		}
		return result.copy();
	}
	
	@NotNull
	@Override
	public CraftingBookCategory category() {
		
		return CraftingBookCategory.MISC;
	}
	
	@SuppressWarnings( "WeakerAccess" )
	public boolean isMergeComponents() {
		
		return merge_components;
	}
	
	@NotNull
	public ItemStack getResult() {
		
		return result;
	}
	
	@NotNull
	public ComponentsRecipeResult getNBTRecipeResult() {
		
		return new ComponentsRecipeResult(
			result.getItem(),
			result.getComponentsPatch(),
			result.getCount(),
			merge_components
		);
	}
}
