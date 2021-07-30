package de.geheimagentnr1.recipes_lib.elements.recipes.nbt;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;


public abstract class NBTRecipe implements CraftingRecipe {
	
	
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
	public ItemStack assemble( @Nonnull CraftingContainer container ) {
		
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
	
	@SuppressWarnings( "WeakerAccess" )
	public boolean isMergeNbt() {
		
		return merge_nbt;
	}
}
