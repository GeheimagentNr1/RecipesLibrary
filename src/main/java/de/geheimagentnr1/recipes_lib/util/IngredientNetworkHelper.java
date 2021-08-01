package de.geheimagentnr1.recipes_lib.util;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;


public class IngredientNetworkHelper {
	
	
	public static Ingredient fromNetwork( FriendlyByteBuf buffer ) {
		
		int i = buffer.readVarInt();
		if( i == -1 ) {
			return CraftingHelper.getIngredient( buffer.readResourceLocation(), buffer );
		}
		return Ingredient.fromValues( buffer.readList( FriendlyByteBuf::readItem )
			.stream()
			.map( Ingredient.ItemValue::new ) );
	}
}
