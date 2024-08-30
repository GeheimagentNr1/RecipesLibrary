package de.geheimagentnr1.recipes_lib.elements.recipes.components;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;


public abstract class ComponentsRecipe implements CraftingRecipe {
	
	
	@NotNull
	private final String group;
	
	@NotNull
	private final NonNullList<Ingredient> ingredients;
	
	@NotNull
	private final ItemStack result;
	
	private final boolean merge_components;
	
	protected ComponentsRecipe(
		@NotNull String _group,
		@NotNull NonNullList<Ingredient> _ingredients,
		@NotNull ItemStack _result,
		boolean _merge_components ) {
		
		group = _group;
		ingredients = _ingredients;
		result = _result;
		merge_components = _merge_components;
	}
	
	@NotNull
	@Override
	public String getGroup() {
		
		return group;
	}
	
	@NotNull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		
		return ingredients;
	}
	
	@NotNull
	@Override
	public ItemStack getResultItem( @NotNull HolderLookup.Provider pRegistries ) {
		
		return result;
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
