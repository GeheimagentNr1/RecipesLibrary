package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt.ShapedNBTRecipe;
import de.geheimagentnr1.recipes_lib.util.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.Ingredient;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;


public class ShapelessNBTRecipeSerializer extends NBTRecipeSerializer<ShapelessNBTRecipe> {
	
	
	private static final Codec<RawShapelessNBTRecipe> RAW_CODEC =
		RecordCodecBuilder.create( ( builder ) -> builder.group(
			ExtraCodecs.strictOptionalField( Codec.STRING, "group", "" ).forGetter( RawShapelessNBTRecipe::group ),
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
			).forGetter( RawShapelessNBTRecipe::ingredients ),
			RESULT_CODEC.fieldOf( "result" ).forGetter( RawShapelessNBTRecipe::result )
		).apply( builder, RawShapelessNBTRecipe::new ) );
	
	private static final Codec<ShapelessNBTRecipe> CODEC =
		RAW_CODEC.flatXmap(
			recipe -> DataResult.success(
				new ShapelessNBTRecipe(
					recipe.group(),
					recipe.ingredients(),
					recipe.result().buildItemStack(),
					recipe.result().mergeNbt()
				)
			),
			( recipe ) -> {
				throw new NotImplementedException( "Serializing ShapelessNBTRecipe is not implemented yet." );
			}
		);
	
	@NotNull
	private static final ShapelessNBTRecipeFactory SHAPELESS_NBT_RECIPE_FACTORY = new ShapelessNBTRecipeFactory();
	
	@NotNull
	@Override
	public Codec<ShapelessNBTRecipe> codec() {
		
		return CODEC;
	}
	
	@NotNull
	@Override
	protected Pair<Integer, NBTRecipeFactory<ShapelessNBTRecipe>> readRecipeData( @NotNull FriendlyByteBuf buffer ) {
		
		return new Pair<>( buffer.readVarInt(), SHAPELESS_NBT_RECIPE_FACTORY );
	}
	
	@Override
	protected void writeRecipeData( @NotNull FriendlyByteBuf buffer, @NotNull ShapelessNBTRecipe recipe ) {
		
		buffer.writeVarInt( recipe.getIngredients().size() );
	}
}
