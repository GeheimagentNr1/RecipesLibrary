package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.components;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.ModIngredientSerializersRegisterFactory;
import de.geheimagentnr1.recipes_lib.util.JSONUtil;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.stream.Stream;


public class ComponentsIngredient implements ICustomIngredient {
	
	
	@NotNull
	public static final String registry_name = "components";
	
	@NotNull
	private final ItemStack stack;
	
	@NotNull
	private final MatchType matchType;
	
	private final boolean ignoreNullValue;
	
	public ComponentsIngredient( @NotNull ItemStack _stack, @NotNull MatchType _matchType, boolean _ignoreNullValue ) {
		
		stack = _stack;
		matchType = _matchType;
		ignoreNullValue = _ignoreNullValue;
	}
	
	@NotNull
	public static ComponentsIngredient fromStack(
		@NotNull ItemStack _stack,
		@NotNull MatchType _matchType,
		boolean _ignoreNullValue ) {
		
		return new ComponentsIngredient( _stack, _matchType, _ignoreNullValue );
	}
	
	@Override
	public boolean test( @Nullable ItemStack pStack ) {
		
		if( pStack == null || stack.getItem() != pStack.getItem() ||
			stack.getDamageValue() != pStack.getDamageValue() ) {
			return false;
		}
		return switch( matchType ) {
			case EQUAL -> ItemStack.isSameItemSameComponents( stack, pStack );
			case CONTAINS -> containsComponents(
				JSONUtil.buildComponentsJson( stack ),
				JSONUtil.buildComponentsJson( pStack )
			);
			case CONTAINS_NONE -> containsNoneComponents(
				JSONUtil.buildComponentsJson( stack ),
				JSONUtil.buildComponentsJson( pStack )
			);
			case NOT_EQUAL -> !ItemStack.isSameItemSameComponents( stack, pStack );
		};
	}
	
	private boolean containsComponents( @Nullable JsonElement element1, @Nullable JsonElement element2 ) {
		
		if( element1 == element2 ) {
			return true;
		} else {
			if( element1 == null ) {
				return true;
			} else {
				if( element2 == null ) {
					return false;
				} else {
					if( element1.getClass().equals( element2.getClass() ) ) {
						if( element1 instanceof JsonObject object1 ) {
							JsonObject object2 = (JsonObject)element2;
							
							for( String key : object1.keySet() ) {
								JsonElement subElement1 = object1.get( key );
								if( !containsComponents( subElement1, object2.get( key ) ) ) {
									return false;
								}
							}
							return true;
						} else {
							if( element1 instanceof JsonArray array1 ) {
								JsonArray array2 = (JsonArray)element2;
								if( array1.isEmpty() ) {
									return array2.isEmpty();
								} else {
									for( JsonElement subElement1 : array1 ) {
										boolean containsNone = true;
										
										for( JsonElement subElement2 : array2 ) {
											if( containsComponents( subElement1, subElement2 ) ) {
												containsNone = false;
												break;
											}
										}
										if( containsNone ) {
											return false;
										}
									}
									return true;
								}
							} else {
								if( element1.isJsonNull() && ignoreNullValue ) {
									return true;
								} else {
									return element1.equals( element2 );
								}
							}
						}
					} else {
						return false;
					}
				}
			}
		}
	}
	
	private boolean containsNoneComponents( @Nullable JsonElement element1, @Nullable JsonElement element2 ) {
		
		if( element1 == element2 ) {
			return false;
		} else {
			if( element1 == null ) {
				return false;
			} else {
				if( element2 == null ) {
					return true;
				} else {
					if( element1.getClass().equals( element2.getClass() ) ) {
						if( element1 instanceof JsonObject object1 ) {
							JsonObject object2 = (JsonObject)element2;
							
							for( String key : object1.keySet() ) {
								JsonElement subElement1 = object1.get( key );
								if( !containsNoneComponents( subElement1, object2.get( key ) ) ) {
									return false;
								}
							}
							return true;
						} else {
							if( element1 instanceof JsonArray array1 ) {
								JsonArray array2 = (JsonArray)element2;
								if( array1.isEmpty() ) {
									return false;
								} else {
									for( JsonElement subElement1 : array1 ) {
										boolean contains = false;
										
										for( JsonElement subElement2 : array2 ) {
											if( !containsNoneComponents( subElement1, subElement2 ) ) {
												contains = true;
												break;
											}
										}
										if( contains ) {
											return false;
										}
									}
									return true;
								}
							} else {
								if( element1.isJsonNull() && ignoreNullValue ) {
									return false;
								} else {
									return !element1.equals( element2 );
								}
							}
						}
					} else {
						return false;
					}
				}
			}
		}
	}
	
	@Override
	public boolean isSimple() {
		
		return false;
	}
	
	@Override
	public IngredientType<?> getType() {
		
		return ModIngredientSerializersRegisterFactory.COMPONENTS_INGREDIENT.get();
	}
	
	@Override
	public Stream<ItemStack> getItems() {
		
		return Stream.of( stack );
	}
	
	//package-private
	@NotNull
	ItemStack getStack() {
		
		return stack;
	}
	
	//package-private
	@NotNull
	MatchType getMatchType() {
		
		return matchType;
	}
	
	boolean isIgnoreNullValue() {
		
		return ignoreNullValue;
	}
}
