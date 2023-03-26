package de.geheimagentnr1.recipes_lib.elements.recipes.nbt;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;


public abstract class NBTRecipe implements CraftingRecipe {
	
	
	private final ResourceLocation id;
	
	private final String group;
	
	private final NonNullList<Ingredient> ingredients;
	
	private final ItemStack result;
	
	private final boolean merge_nbt;
	
	protected NBTRecipe(
		ResourceLocation _id,
		String _group,
		NonNullList<Ingredient> _ingredients,
		ItemStack _result,
		boolean _merge_nbt ) {
		
		id = _id;
		group = _group;
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
	
	@Nonnull
	@Override
	public CraftingBookCategory category() {
		
		return CraftingBookCategory.MISC;
	}
	
	@SuppressWarnings( "WeakerAccess" )
	public boolean isMergeNbt() {
		
		return merge_nbt;
	}
	
	public ItemStack getResult() {
		
		return result;
	}
	
}
