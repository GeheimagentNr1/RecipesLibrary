package de.geheimagentnr1.recipes_lib.elements.recipes.renaming;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;


//Since 26.1 RecipeSerializer is a record of codec and stream codec, this class builds it
public class RenamingRecipeSerializer {


	private static final MapCodec<RenamingRecipe> CODEC = RecordCodecBuilder.mapCodec( ( builder ) -> builder.group(
		Ingredient.CODEC.fieldOf( "ingredient" ).forGetter( RenamingRecipe::getIngredient )
	).apply( builder, RenamingRecipe::new ) );

	private static final StreamCodec<RegistryFriendlyByteBuf, RenamingRecipe> STREAM_CODEC = StreamCodec.composite(
		Ingredient.CONTENTS_STREAM_CODEC, RenamingRecipe::getIngredient,
		RenamingRecipe::new
	);

	@NotNull
	public static RecipeSerializer<RenamingRecipe> createSerializer() {

		return new RecipeSerializer<>( CODEC, STREAM_CODEC );
	}
}
