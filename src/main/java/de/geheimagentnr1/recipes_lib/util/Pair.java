package de.geheimagentnr1.recipes_lib.util;

import javax.annotation.Nonnull;


public class Pair<K, V> {
	
	
	private final K key;
	
	private final V value;
	
	public Pair( @Nonnull K _key, @Nonnull V _value ) {
		
		key = _key;
		value = _value;
	}
	
	@Nonnull
	public K getKey() {
		
		return key;
	}
	
	@Nonnull
	public V getValue() {
		
		return value;
	}
}
