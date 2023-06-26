package de.geheimagentnr1.recipes_lib.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;


@RequiredArgsConstructor
@Data
public class Pair<K, V> {
	
	
	@NotNull
	private final K key;
	
	@NotNull
	private final V value;
}
