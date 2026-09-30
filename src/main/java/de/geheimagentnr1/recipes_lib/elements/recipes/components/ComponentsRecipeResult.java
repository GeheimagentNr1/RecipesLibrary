package de.geheimagentnr1.recipes_lib.elements.recipes.components;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;


public record ComponentsRecipeResult(
	Item item,
	DataComponentPatch components,
	int count,
	boolean mergeComponents) {


	//Since 26.1 recipes are parsed before item components are bound, so no ItemStack may be created here
	public ItemStackTemplate buildTemplate() {

		return new ItemStackTemplate( item, count, components );
	}
}
