package de.geheimagentnr1.recipes_lib.elements.recipes.components;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;


public record ComponentsRecipeResult(
	Item item,
	DataComponentPatch components,
	int count,
	boolean mergeComponents) {
	
	
	public ItemStack buildItemStack() {
		
		ItemStack stack = new ItemStack( item, count );
		stack.applyComponentsAndValidate( components );
		return stack;
	}
}
