package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import org.jetbrains.annotations.NotNull;


public class NBTIngredientSerializer implements IIngredientSerializer<NBTIngredient> {
	
	
	@NotNull
	@Override
	public NBTIngredient parse( @NotNull JsonObject json ) {
		
		return NBTIngredient.fromStack(
			CraftingHelper.getItemStack( json, true ),
			MatchType.valueOf( json.get( "matchType" ).getAsString() )
		);
	}
	
	@NotNull
	@Override
	public NBTIngredient parse( @NotNull FriendlyByteBuf buffer ) {
		
		return NBTIngredient.fromStack( buffer.readItem(), MatchType.values()[buffer.readInt()] );
	}
	
	@Override
	public void write( @NotNull FriendlyByteBuf buffer, @NotNull NBTIngredient ingredient ) {
		
		buffer.writeItem( ingredient.getStack() );
		buffer.writeInt( ingredient.getMatchType().ordinal() );
	}
}
