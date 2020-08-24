package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt;

import com.google.gson.JsonObject;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.IngredientSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CraftingHelper;

import javax.annotation.Nonnull;


public class NBTIngredientSerializer implements IngredientSerializer<NBTIngredient> {
	
	
	@Override
	public String getRegistryName() {
		
		return NBTIngredient.registry_name;
	}
	
	@Nonnull
	@Override
	public NBTIngredient parse( @Nonnull JsonObject json ) {
		
		return NBTIngredient.fromStack( CraftingHelper.getItemStack( json, true ),
			MatchType.valueOf( json.get( "matchType" ).getAsString() ) );
	}
	
	@Nonnull
	@Override
	public NBTIngredient parse( PacketBuffer buffer ) {
		
		return NBTIngredient.fromStack( buffer.readItemStack(), MatchType.values()[buffer.readInt()] );
	}
	
	@Override
	public void write( PacketBuffer buffer, NBTIngredient ingredient ) {
		
		buffer.writeItemStack( ingredient.getStack() );
		buffer.writeInt( ingredient.getMatchType().ordinal() );
	}
}
