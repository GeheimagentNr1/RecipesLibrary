package de.geheimagentnr1.recipes_lib.elements.recipes.components.shapless_nbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.ComponentsRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.shaped_nbt.ShapedComponentsRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ShapelessComponentsRecipeSerializer extends ComponentsRecipeSerializer<ShapelessComponentsRecipe> {


	private static final MapCodec<ShapelessComponentsRecipe> CODEC =
		RecordCodecBuilder.mapCodec( ( builder ) -> builder.group(
			Codec.STRING.fieldOf( "group" ).orElse( "" ).forGetter( ShapelessComponentsRecipe::group ),
			Ingredient.CODEC.listOf( 1, ShapedComponentsRecipe.MAX_WIDTH * ShapedComponentsRecipe.MAX_HEIGHT )
				.fieldOf( "ingredients" )
				.forGetter( ShapelessComponentsRecipe::getIngredients ),
			RESULT_CODEC.fieldOf( "result" ).forGetter( ShapelessComponentsRecipe::getNBTRecipeResult )
		).apply( builder, ShapelessComponentsRecipe::new ) );

	@NotNull
	@Override
	public MapCodec<ShapelessComponentsRecipe> codec() {

		return CODEC;
	}

	@NotNull
	@Override
	protected ShapelessComponentsRecipe buildRecipe(
		@NotNull RegistryFriendlyByteBuf buffer,
		@NotNull String group,
		@NotNull ItemStack result,
		boolean merge_components ) {

		int ingredientCount = buffer.readVarInt();
		List<Ingredient> ingredients = new ArrayList<>( ingredientCount );
		for( int i = 0; i < ingredientCount; i++ ) {
			ingredients.add( Ingredient.CONTENTS_STREAM_CODEC.decode( buffer ) );
		}
		return new ShapelessComponentsRecipe( group, ingredients, result, merge_components );
	}

	@Override
	protected void writeRecipeData( @NotNull RegistryFriendlyByteBuf buffer, @NotNull ShapelessComponentsRecipe recipe ) {

		buffer.writeVarInt( recipe.getIngredients().size() );
		for( Ingredient ingredient : recipe.getIngredients() ) {
			Ingredient.CONTENTS_STREAM_CODEC.encode( buffer, ingredient );
		}
	}
}
