package de.geheimagentnr1.recipes_lib.elements.recipes.components.shapless_nbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.ComponentsRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.shaped_nbt.ShapedComponentsRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;


public class ShapelessComponentsRecipeSerializer extends ComponentsRecipeSerializer<ShapelessComponentsRecipe> {
	
	
	private static final MapCodec<ShapelessComponentsRecipe> CODEC =
		RecordCodecBuilder.mapCodec( ( builder ) -> builder.group(
			Codec.STRING.fieldOf( "group" ).orElse( "" ).forGetter( ShapelessComponentsRecipe::getGroup ),
			Ingredient.CODEC_NONEMPTY.listOf().fieldOf( "ingredients" ).flatXmap(
				( recipe ) -> {
					Ingredient[] ingredients = recipe.stream()
						.filter( ( ingredient ) -> !ingredient.isEmpty() )
						.toArray( Ingredient[]::new );
					if( ingredients.length == 0 ) {
						return DataResult.error( () -> "No ingredients for shapeless recipe" );
					} else {
						return ingredients.length > ShapedComponentsRecipe.MAX_WIDTH * ShapedComponentsRecipe.MAX_HEIGHT
							? DataResult.error( () -> "Too many ingredients for shapeless recipe" )
							: DataResult.success( NonNullList.of( Ingredient.EMPTY, ingredients ) );
					}
				},
				DataResult::success
			).forGetter( ShapelessComponentsRecipe::getIngredients ),
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
		NonNullList<Ingredient> ingredients = NonNullList.withSize( ingredientCount, Ingredient.EMPTY );
		for( int i = 0; i < ingredientCount; i++ ) {
			ingredients.set( i, Ingredient.CONTENTS_STREAM_CODEC.decode( buffer ) );
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
