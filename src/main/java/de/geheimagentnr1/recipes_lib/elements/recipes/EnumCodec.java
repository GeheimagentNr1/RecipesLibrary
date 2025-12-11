package de.geheimagentnr1.recipes_lib.elements.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import org.jetbrains.annotations.NotNull;


public class EnumCodec<E extends Enum<E>> implements Codec<E> {
	
	
	@NotNull
	private final Class<E> enumClass;
	
	public EnumCodec( @NotNull Class<E> _enumClass ) {
		
		enumClass = _enumClass;
	}
	
	@Override
	public <T> DataResult<com.mojang.datafixers.util.Pair<E, T>> decode(
		com.mojang.serialization.DynamicOps<T> ops,
		T input ) {
		
		return ops.getStringValue( input ).flatMap( name -> {
			try {
				return DataResult.success( com.mojang.datafixers.util.Pair.of(
					Enum.valueOf( enumClass, name.toUpperCase() ),
					ops.empty()
				) );
			} catch( IllegalArgumentException e ) {
				return DataResult.error( () -> "Unknown enum value: " + name );
			}
		} );
	}
	
	@Override
	public <T> DataResult<T> encode( E input, com.mojang.serialization.DynamicOps<T> ops, T prefix ) {
		
		return DataResult.success( ops.createString( input.name().toLowerCase() ) );
	}
}
