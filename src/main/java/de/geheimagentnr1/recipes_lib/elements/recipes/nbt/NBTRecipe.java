package de.geheimagentnr1.recipes_lib.elements.recipes.nbt;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;


public abstract class NBTRecipe implements ICraftingRecipe {
	
	
	private final ResourceLocation id;
	
	private final String group;
	
	private final NonNullList<Ingredient> ingredients;
	
	private final ItemStack result;
	
	private final boolean merge_nbt;
	
	protected NBTRecipe(
		ResourceLocation idIn,
		String groupIn,
		NonNullList<Ingredient> _ingredients,
		ItemStack _result,
		boolean _merge_nbt ) {
		
		id = idIn;
		group = groupIn;
		ingredients = _ingredients;
		result = _result;
		merge_nbt = _merge_nbt;
	}
	
	@Nonnull
	@Override
	public ResourceLocation getId() {
		
		return id;
	}
	
	@Nonnull
	@Override
	public String getGroup() {
		
		return group;
	}
	
	@Nonnull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		
		return ingredients;
	}
	
	@Nonnull
	@Override
	public ItemStack getResultItem() {
		
		return result;
	}
	
	@Nonnull
	@Override
	public ItemStack assemble( @Nonnull CraftingInventory inv ) {
		
		if( merge_nbt ) {
			for( int j = 0; j < inv.getContainerSize(); j++ ) {
				ItemStack itemstack = inv.getItem( j );
				if( itemstack.getItem() == result.getItem() ) {
					ItemStack resultStack = result.copy();
					resultStack.setTag( itemstack.getOrCreateTag().copy().merge( resultStack.getOrCreateTag() ) );
					return resultStack;
				}
			}
		}
		return result.copy();
	}
	
	@SuppressWarnings( "WeakerAccess" )
	public boolean isMergeNbt() {
		
		return merge_nbt;
	}
}
