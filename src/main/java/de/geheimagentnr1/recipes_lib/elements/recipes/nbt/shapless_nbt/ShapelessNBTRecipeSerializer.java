package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt.ShapedNBTRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;


public class ShapelessNBTRecipeSerializer extends NBTRecipeSerializer<ShapelessNBTRecipe> {
	
	
	private static final Codec<ShapelessNBTRecipe> CODEC =
		RecordCodecBuilder.create( ( builder ) -> builder.group(
			ExtraCodecs.strictOptionalField( Codec.STRING, "group", "" ).forGetter( ShapelessNBTRecipe::getGroup ),
			Ingredient.CODEC_NONEMPTY.listOf().fieldOf( "ingredients" ).flatXmap(
				( recipe ) -> {
					Ingredient[] ingredients = recipe.stream()
						.filter( ( ingredient ) -> !ingredient.isEmpty() )
						.toArray( Ingredient[]::new );
					if( ingredients.length == 0 ) {
						return DataResult.error( () -> "No ingredients for shapeless recipe" );
					} else {
						return ingredients.length > ShapedNBTRecipe.MAX_WIDTH * ShapedNBTRecipe.MAX_HEIGHT
							? DataResult.error( () -> "Too many ingredients for shapeless recipe" )
							: DataResult.success( NonNullList.of( Ingredient.EMPTY, ingredients ) );
					}
				},
				DataResult::success
			).forGetter( ShapelessNBTRecipe::getIngredients ),
			RESULT_CODEC.fieldOf( "result" ).forGetter( ShapelessNBTRecipe::getNBTRecipeResult )
		).apply( builder, ShapelessNBTRecipe::new ) );
	
	@NotNull
	@Override
	public Codec<ShapelessNBTRecipe> codec() {
		
		return CODEC;
	}
	
	@NotNull
	@Override
	protected ShapelessNBTRecipe buildRecipe(
		@NotNull FriendlyByteBuf buffer,
		@NotNull String group,
		@NotNull ItemStack result,
		boolean merge_nbt ) {
		
		int ingredientCount = buffer.readVarInt();
		NonNullList<Ingredient> ingredients = NonNullList.withSize( ingredientCount, Ingredient.EMPTY );
		for( int i = 0; i < ingredientCount; i++ ) {
			ingredients.set( i, Ingredient.fromNetwork( buffer ) );
		}
		return new ShapelessNBTRecipe( group, ingredients, result, merge_nbt );
	}
	
	@Override
	protected void writeRecipeData( @NotNull FriendlyByteBuf buffer, @NotNull ShapelessNBTRecipe recipe ) {
		
		buffer.writeVarInt( recipe.getIngredients().size() );
		for( Ingredient ingredient : recipe.getIngredients() ) {
			ingredient.toNetwork( buffer );
		}
	}
}
