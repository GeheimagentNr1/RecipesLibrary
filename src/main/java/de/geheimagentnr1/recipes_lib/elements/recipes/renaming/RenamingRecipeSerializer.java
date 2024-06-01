package de.geheimagentnr1.recipes_lib.elements.recipes.renaming;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;


public class RenamingRecipeSerializer implements RecipeSerializer<RenamingRecipe> {
	
	
	private static final MapCodec<RenamingRecipe> CODEC = RecordCodecBuilder.mapCodec( ( builder ) -> builder.group(
		Ingredient.CODEC_NONEMPTY.fieldOf( "ingredient" ).forGetter( RenamingRecipe::getIngredient )
	).apply( builder, RenamingRecipe::new ) );
	
	private static final StreamCodec<RegistryFriendlyByteBuf, RenamingRecipe> STREAM_CODEC = StreamCodec.composite(
		Ingredient.CONTENTS_STREAM_CODEC, RenamingRecipe::getIngredient,
		RenamingRecipe::new
	);
	
	@Override
	public MapCodec<RenamingRecipe> codec() {
		
		return CODEC;
	}
	
	@Override
	public StreamCodec<RegistryFriendlyByteBuf, RenamingRecipe> streamCodec() {
		
		return STREAM_CODEC;
	}
}
