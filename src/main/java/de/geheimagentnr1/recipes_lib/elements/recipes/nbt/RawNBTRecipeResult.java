package de.geheimagentnr1.recipes_lib.elements.recipes.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;


public record RawNBTRecipeResult(
	Item item,
	CompoundTag nbt,
	int count,
	boolean mergeNbt) {
	
	
	public ItemStack buildItemStack() {
		
		ItemStack stack = new ItemStack( item, count );
		stack.setTag( nbt );
		return stack;
	}
}
