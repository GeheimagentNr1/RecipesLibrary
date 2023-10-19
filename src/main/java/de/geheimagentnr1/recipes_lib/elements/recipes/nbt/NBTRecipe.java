package de.geheimagentnr1.recipes_lib.elements.recipes.nbt;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;


public abstract class NBTRecipe implements CraftingRecipe {
	
	
	@NotNull
	private final String group;
	
	@NotNull
	private final NonNullList<Ingredient> ingredients;
	
	@NotNull
	private final ItemStack result;
	
	private final boolean merge_nbt;
	
	protected NBTRecipe(
		@NotNull String _group,
		@NotNull NonNullList<Ingredient> _ingredients,
		@NotNull ItemStack _result,
		boolean _merge_nbt ) {
		
		group = _group;
		ingredients = _ingredients;
		result = _result;
		merge_nbt = _merge_nbt;
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
	public ItemStack getResultItem( @NotNull RegistryAccess registryAccess ) {
		
		return result;
	}
	
	@NotNull
	@Override
	public ItemStack assemble( CraftingContainer container, RegistryAccess registryAccess ) {
		
		if( merge_nbt ) {
			for( int j = 0; j < container.getContainerSize(); j++ ) {
				ItemStack itemstack = container.getItem( j );
				if( itemstack.getItem() == result.getItem() ) {
					ItemStack resultStack = result.copy();
					resultStack.setTag( itemstack.getOrCreateTag().copy().merge( resultStack.getOrCreateTag() ) );
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
	public boolean isMergeNbt() {
		
		return merge_nbt;
	}
	
	@NotNull
	public ItemStack getResult() {
		
		return result;
	}
	
}
