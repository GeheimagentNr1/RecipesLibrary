package de.geheimagentnr1.recipes_lib.elements.recipes.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;


//Since 26.1 RecipeSerializer is a record of codec and stream codec, this class builds it
public abstract class ComponentsRecipeSerializer<R extends ComponentsRecipe> {
	
	
	private static final Codec<Item> ITEM_NONAIR_CODEC = BuiltInRegistries.ITEM.byNameCodec()
		.validate(
			builder -> builder == Items.AIR ?
				DataResult.error( () -> "Crafting result must not be minecraft:air" ) :
				DataResult.success( builder )
		);
	
	protected static final Codec<ComponentsRecipeResult> RESULT_CODEC =
		RecordCodecBuilder.create( builder -> builder.group(
			ITEM_NONAIR_CODEC.fieldOf( "item" ).forGetter( ComponentsRecipeResult::item ),
			DataComponentPatch.CODEC
				.optionalFieldOf("components", DataComponentPatch.EMPTY)
				.forGetter( ComponentsRecipeResult::components ),
			ExtraCodecs.POSITIVE_INT.fieldOf( "count" ).orElse( 1 ).forGetter( ComponentsRecipeResult::count ),
			Codec.BOOL.fieldOf( "merge_components" ).orElse( true ).forGetter( ComponentsRecipeResult::mergeComponents )
		).apply( builder, ComponentsRecipeResult::new ) );
	
	private final StreamCodec<RegistryFriendlyByteBuf, R> STREAM_CODEC = StreamCodec.of(
		this::toNetwork, this::fromNetwork
	);
	
	@NotNull
	public abstract MapCodec<R> codec();

	@NotNull
	public StreamCodec<RegistryFriendlyByteBuf, R> streamCodec() {

		return STREAM_CODEC;
	}

	@NotNull
	public RecipeSerializer<R> createSerializer() {

		return new RecipeSerializer<>( codec(), streamCodec() );
	}
	
	public R fromNetwork( @NotNull RegistryFriendlyByteBuf buffer ) {
		
		String group = buffer.readUtf();
		ItemStackTemplate result = ItemStackTemplate.STREAM_CODEC.decode( buffer );
		boolean merge_components = buffer.readBoolean();
		return buildRecipe( buffer, group, result, merge_components );
	}

	@NotNull
	protected abstract R buildRecipe(
		@NotNull RegistryFriendlyByteBuf buffer,
		@NotNull String group,
		@NotNull ItemStackTemplate result,
		boolean merge_components );

	public void toNetwork( @NotNull RegistryFriendlyByteBuf buffer, @NotNull R recipe ) {

		buffer.writeUtf( recipe.group() );
		ItemStackTemplate.STREAM_CODEC.encode( buffer, recipe.getResult() );
		buffer.writeBoolean( recipe.isMergeComponents() );
		writeRecipeData( buffer, recipe );
	}
	
	protected abstract void writeRecipeData( @NotNull RegistryFriendlyByteBuf buffer, @NotNull R recipe );
}
