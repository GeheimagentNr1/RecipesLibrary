package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.util.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;


public class ShapedNBTRecipeSerializer extends NBTRecipeSerializer<ShapedNBTRecipe> {
	
	
	@NotNull
	@Override
	protected Pair<NonNullList<Ingredient>, NBTRecipeFactory<ShapedNBTRecipe>> readRecipeData(
		@NotNull JsonObject json ) {
		
		Map<String, Ingredient> keys = readKeys( GsonHelper.getAsJsonObject( json, "key" ) );
		String[] pattern = shrink( patternFromJson( GsonHelper.getAsJsonArray( json, "pattern" ) ) );
		int width = pattern[0].length();
		int height = pattern.length;
		return new Pair<>(
			deserializeIngredients( pattern, keys, width, height ),
			new ShapedNBTRecipeFactory( width, height )
		);
	}
	
	@NotNull
	private Map<String, Ingredient> readKeys( @NotNull JsonObject json ) {
		
		Map<String, Ingredient> map = Maps.newHashMap();
		
		for( Map.Entry<String, JsonElement> entry : json.entrySet() ) {
			if( entry.getKey().length() != 1 ) {
				throw new JsonSyntaxException(
					"Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only)." );
			}
			if( " ".equals( entry.getKey() ) ) {
				throw new JsonSyntaxException( "Invalid key entry: ' ' is a reserved symbol." );
			}
			map.put( entry.getKey(), Ingredient.fromJson( entry.getValue() ) );
		}
		map.put( " ", Ingredient.EMPTY );
		return map;
	}
	
	@NotNull
	private String[] patternFromJson( @NotNull JsonArray patternArray ) {
		
		String[] pattern = new String[patternArray.size()];
		if( pattern.length > MAX_HEIGHT ) {
			throw new JsonSyntaxException( "Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum" );
		} else {
			if( pattern.length == 0 ) {
				throw new JsonSyntaxException( "Invalid pattern: empty pattern not allowed" );
			} else {
				for( int i = 0; i < pattern.length; i++ ) {
					String rowPattern = GsonHelper.getAsString(
						patternArray.get( i ).getAsJsonObject(),
						"pattern[" + i + "]"
					);
					if( rowPattern.length() > MAX_WIDTH ) {
						throw new JsonSyntaxException(
							"Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum" );
					}
					if( i > 0 && pattern[0].length() != rowPattern.length() ) {
						throw new JsonSyntaxException( "Invalid pattern: each row must be the same width" );
					}
					pattern[i] = rowPattern;
				}
				return pattern;
			}
		}
	}
	
	@NotNull
	private String[] shrink( @NotNull String... pattern ) {
		
		int first = Integer.MAX_VALUE;
		int last = 0;
		int row = 0;
		int column = 0;
		
		for( int index = 0; index < pattern.length; index++ ) {
			String s = pattern[index];
			first = Math.min( first, firstNonSpace( s ) );
			int lastIndex = lastNonSpace( s );
			last = Math.max( last, lastIndex );
			if( lastIndex < 0 ) {
				if( row == index ) {
					row++;
				}
				column++;
			} else {
				column = 0;
			}
		}
		if( pattern.length == column ) {
			return new String[0];
		} else {
			String[] shortPattern = new String[pattern.length - column - row];
			
			for( int index = 0; index < shortPattern.length; index++ ) {
				shortPattern[index] = pattern[index + row].substring( first, last + 1 );
			}
			return shortPattern;
		}
	}
	
	private int firstNonSpace( @NotNull String str ) {
		
		int i = 0;
		while( i < str.length() && str.charAt( i ) == ' ' ) {
			i++;
		}
		return i;
	}
	
	private int lastNonSpace( @NotNull String str ) {
		
		int i = str.length() - 1;
		while( i >= 0 && str.charAt( i ) == ' ' ) {
			i--;
		}
		return i;
	}
	
	@NotNull
	private NonNullList<Ingredient> deserializeIngredients(
		@NotNull String[] pattern,
		@NotNull Map<String, Ingredient> keys,
		int patternWidth,
		int patternHeight ) {
		
		NonNullList<Ingredient> ingredients = NonNullList.withSize( patternWidth * patternHeight, Ingredient.EMPTY );
		Set<String> set = Sets.newHashSet( keys.keySet() );
		set.remove( " " );
		
		for( int i = 0; i < pattern.length; i++ ) {
			for( int j = 0; j < pattern[i].length(); j++ ) {
				String key = pattern[i].substring( j, j + 1 );
				Ingredient ingredient = keys.get( key );
				if( ingredient == null ) {
					throw new JsonSyntaxException(
						"Pattern references symbol '" + key + "' but it's not defined in the key" );
				}
				set.remove( key );
				ingredients.set( j + patternWidth * i, ingredient );
			}
		}
		if( set.isEmpty() ) {
			return ingredients;
		} else {
			throw new JsonSyntaxException( "Key defines symbols that aren't used in pattern: " + set );
		}
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
