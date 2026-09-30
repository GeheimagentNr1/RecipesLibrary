package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.geheimagentnr1.recipes_lib.elements.recipes.EnumCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;


public class ComponentsIngredientCodec {


	//ItemStackTemplate reads the same "id", "count" and "components" keys as ItemStack did
	public static final MapCodec<ComponentsIngredient> CODEC = RecordCodecBuilder.mapCodec(
		builder -> builder.group(
				ItemStackTemplate.CODEC.fieldOf( "item" ).forGetter( ComponentsIngredient::getTemplate ),
				new EnumCodec<>( MatchType.class ).fieldOf( "matchType" ).forGetter( ComponentsIngredient::getMatchType ),
				Codec.BOOL.fieldOf( "ignoreNullValue" ).orElse( false ).forGetter( ComponentsIngredient::isIgnoreNullValue )
			)
			.apply( builder, ComponentsIngredient::new )
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, ComponentsIngredient> STREAM_CODEC = StreamCodec.of(
		ComponentsIngredientCodec::write,
		ComponentsIngredientCodec::read
	);

	private static void write( RegistryFriendlyByteBuf buffer, ComponentsIngredient value ) {

		ItemStackTemplate.STREAM_CODEC.encode( buffer, value.getTemplate() );
		buffer.writeInt( value.getMatchType().ordinal() );
		buffer.writeBoolean( value.isIgnoreNullValue() );
	}

	private static ComponentsIngredient read( RegistryFriendlyByteBuf buffer ) {

		return ComponentsIngredient.fromTemplate(
			ItemStackTemplate.STREAM_CODEC.decode( buffer ),
			MatchType.values()[buffer.readInt()],
			buffer.readBoolean()
		);
	}
}
