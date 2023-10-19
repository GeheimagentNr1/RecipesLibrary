package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.geheimagentnr1.minecraft_forge_api.elements.recipes.EnumCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.ingredients.IIngredientSerializer;
import org.jetbrains.annotations.NotNull;


public class NBTIngredientSerializer implements IIngredientSerializer<NBTIngredient> {
	
	
	private static final Codec<NBTIngredient> CODEC = RecordCodecBuilder.create(
		builder -> builder.group(
				ItemStack.CODEC.fieldOf( "stack" ).forGetter( NBTIngredient::getStack ),
				new EnumCodec<>( MatchType.class ).fieldOf( "matchType" ).forGetter( NBTIngredient::getMatchType )
			)
			.apply( builder, NBTIngredient::new )
	);
	
	@Override
	public Codec<? extends NBTIngredient> codec() {
		
		return CODEC;
	}
	
	@Override
	public void write( @NotNull FriendlyByteBuf buffer, @NotNull NBTIngredient ingredient ) {
		
		buffer.writeItem( ingredient.getStack() );
		buffer.writeInt( ingredient.getMatchType().ordinal() );
	}
	
	@NotNull
	@Override
	public NBTIngredient read( FriendlyByteBuf buffer ) {
		
		return NBTIngredient.fromStack( buffer.readItem(), MatchType.values()[buffer.readInt()] );
	}
}
