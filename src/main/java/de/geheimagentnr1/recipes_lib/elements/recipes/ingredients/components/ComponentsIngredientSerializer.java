package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.geheimagentnr1.minecraft_forge_api.elements.recipes.EnumCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.ingredients.IIngredientSerializer;


public class ComponentsIngredientSerializer implements IIngredientSerializer<ComponentsIngredient> {
	
	
	private static final MapCodec<ComponentsIngredient> CODEC = RecordCodecBuilder.mapCodec(
		builder -> builder.group(
				ItemStack.CODEC.fieldOf( "item" ).forGetter( ComponentsIngredient::getStack ),
				new EnumCodec<>( MatchType.class ).fieldOf( "matchType" ).forGetter( ComponentsIngredient::getMatchType ),
				Codec.BOOL.fieldOf( "ignoreNullValue" ).orElse(false).forGetter( ComponentsIngredient::isIgnoreNullValue )
			)
			.apply( builder, ComponentsIngredient::new )
	);
	
	@Override
	public MapCodec<? extends ComponentsIngredient> codec() {
		
		return CODEC;
	}
	
	@Override
	public void write( RegistryFriendlyByteBuf buffer, ComponentsIngredient value ) {
		
		ItemStack.STREAM_CODEC.encode( buffer, value.getStack() );
		buffer.writeInt( value.getMatchType().ordinal() );
		buffer.writeBoolean( value.isIgnoreNullValue() );
	}
	
	@Override
	public ComponentsIngredient read( RegistryFriendlyByteBuf buffer ) {
		
		return ComponentsIngredient.fromStack(
			ItemStack.STREAM_CODEC.decode( buffer ),
			MatchType.values()[buffer.readInt()],
			buffer.readBoolean()
		);
	}
}
