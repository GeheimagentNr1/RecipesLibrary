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
		
		return new NBTIngredient( CraftingHelper.getItemStack( json, true ), json.get( "equalsNBT" )
			.getAsBoolean() );
	}
	
	@Nonnull
	@Override
	public NBTIngredient parse( PacketBuffer buffer ) {
		
		return new NBTIngredient( buffer.readItemStack(), buffer.readBoolean() );
	}
	
	@Override
	public void write( PacketBuffer buffer, NBTIngredient ingredient ) {
		
		buffer.writeItemStack( ingredient.getStack() );
		buffer.writeBoolean( ingredient.isEqualsNBT() );
	}
}
