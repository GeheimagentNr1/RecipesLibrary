package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.util.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.Ingredient;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;


public class ShapedNBTRecipeSerializer extends NBTRecipeSerializer<ShapedNBTRecipe> {
	
	
	private static final Codec<List<String>> PATTERN_CODEC = Codec.STRING.listOf().flatXmap(
		pattern -> {
			if( pattern.size() > MAX_HEIGHT ) {
				return DataResult.error( () -> "Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum" );
			} else {
				if( pattern.isEmpty() ) {
					return DataResult.error( () -> "Invalid pattern: empty pattern not allowed" );
				} else {
					int length = pattern.get( 0 ).length();
					
					for( String element : pattern ) {
						if( element.length() > MAX_WIDTH ) {
							return DataResult.error( () -> "Invalid pattern: too many columns, " + MAX_WIDTH +
								" is maximum" );
						}
						if( length != element.length() ) {
							return DataResult.error( () -> "Invalid pattern: each row must be the same width" );
						}
					}
					return DataResult.success( pattern );
				}
			}
		},
		DataResult::success
	);
	
	private static final Codec<String> SINGLE_CHARACTER_STRING_CODEC = Codec.STRING.flatXmap( ( key ) -> {
		if( key.length() == 1 ) {
			return " ".equals( key )
				? DataResult.error( () -> "Invalid key entry: ' ' is a reserved symbol." )
				: DataResult.success( key );
		} else {
			return DataResult.error( () -> "Invalid key entry: '" + key +
				"' is an invalid symbol (must be 1 character only)." );
		}
	}, DataResult::success );
	
	private static final Codec<RawShapedNBTRecipe> RAW_CODEC =
		RecordCodecBuilder.create( builder -> builder.group(
			ExtraCodecs.strictOptionalField( Codec.STRING, "group", "" ).forGetter( RawShapedNBTRecipe::group ),
			ExtraCodecs.strictUnboundedMap( SINGLE_CHARACTER_STRING_CODEC, Ingredient.CODEC_NONEMPTY )
				.fieldOf( "key" )
				.forGetter( RawShapedNBTRecipe::key ),
			PATTERN_CODEC.fieldOf( "pattern" ).forGetter( RawShapedNBTRecipe::pattern ),
			RESULT_CODEC.fieldOf( "result" ).forGetter( RawShapedNBTRecipe::result )
		).apply( builder, RawShapedNBTRecipe::new ) );
	
	private static final Codec<ShapedNBTRecipe> CODEC =
		RAW_CODEC.flatXmap(
			recipe -> {
				String[] pattern = ShapedNBTRecipe.shrink( recipe.pattern() );
				int width = pattern[0].length();
				int height = pattern.length;
				NonNullList<Ingredient> nonnulllist = NonNullList.withSize( width * height, Ingredient.EMPTY );
				Set<String> keys = Sets.newHashSet( recipe.key().keySet() );
				
				for( int k = 0; k < pattern.length; ++k ) {
					String row = pattern[k];
					
					for( int l = 0; l < row.length(); ++l ) {
						String key = row.substring( l, l + 1 );
						Ingredient ingredient = key.equals( " " ) ? Ingredient.EMPTY : recipe.key().get( key );
						if( ingredient == null ) {
							return DataResult.error( () -> "Pattern references symbol '" + key +
								"' but it's not defined in the key" );
						}
						
						keys.remove( key );
						nonnulllist.set( l + width * k, ingredient );
					}
				}
				if( keys.isEmpty() ) {
					return DataResult.success(
						new ShapedNBTRecipe(
							recipe.group(),
							nonnulllist,
							recipe.result().buildItemStack(),
							recipe.result().mergeNbt(),
							width,
							height
						)
					);
				} else {
					return DataResult.error( () -> "Key defines symbols that aren't used in pattern: " + keys );
				}
			},
			( recipe ) -> {
				throw new NotImplementedException( "Serializing ShapedNBTRecipe is not implemented yet." );
			}
		);
	
	@NotNull
	@Override
	public Codec<ShapedNBTRecipe> codec() {
		
		return CODEC;
	}
	
	@NotNull
	@Override
	protected Pair<Integer, NBTRecipeFactory<ShapedNBTRecipe>> readRecipeData( @NotNull FriendlyByteBuf buffer ) {
		
		int width = buffer.readVarInt();
		int height = buffer.readVarInt();
		return new Pair<>( width * height, new ShapedNBTRecipeFactory( width, height ) );
	}
	
	@Override
	protected void writeRecipeData( @NotNull FriendlyByteBuf buffer, @NotNull ShapedNBTRecipe recipe ) {
		
		buffer.writeVarInt( recipe.getRecipeWidth() );
		buffer.writeVarInt( recipe.getRecipeHeight() );
	}
}
