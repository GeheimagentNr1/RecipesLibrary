package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.IngredientSerializers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;


public class NBTIngredient extends Ingredient {
	
	
	//package-private
	static final String registry_name = "nbt";
	
	private final ItemStack stack;
	
	private final MatchType matchType;
	
	private NBTIngredient( ItemStack _stack, MatchType _matchType ) {
		
		super( Stream.of( new ItemValue( _stack ) ) );
		stack = _stack;
		matchType = _matchType;
	}
	
	public static NBTIngredient fromStack( ItemStack _stack, MatchType _matchType ) {
		
		return new NBTIngredient( _stack, _matchType );
	}
	
	@Override
	public boolean test( @Nullable ItemStack input ) {
		
		if( input == null || stack.getItem() != input.getItem() || stack.getDamageValue() != input.getDamageValue() ) {
			return false;
		}
		return switch( matchType ) {
			case EQUAL -> stack.areShareTagsEqual( input );
			case CONTAINS -> containsNBT( stack.getTag(), input.getTag() );
			case CONTAINS_NONE -> containsNoneNBT( stack.getTag(), input.getTag() );
			case NOT_EQUAL -> !stack.areShareTagsEqual( input );
		};
	}
	
	private boolean containsNBT( @Nullable Tag nbt1, @Nullable Tag nbt2 ) {
		
		if( nbt1 == nbt2 ) {
			return true;
		} else {
			if( nbt1 == null ) {
				return true;
			} else {
				if( nbt2 == null ) {
					return false;
				} else {
					if( nbt1.getClass().equals( nbt2.getClass() ) ) {
						if( nbt1 instanceof CompoundTag compoundTag ) {
							CompoundTag compoundTag1 = (CompoundTag)nbt2;
							
							for( String key : compoundTag.getAllKeys() ) {
								Tag inbt1 = compoundTag.get( key );
								if( !containsNBT( inbt1, compoundTag1.get( key ) ) ) {
									return false;
								}
							}
							
							return true;
						} else {
							if( nbt1 instanceof ListTag listTag ) {
								ListTag listTag1 = (ListTag)nbt2;
								if( listTag.isEmpty() ) {
									return listTag1.isEmpty();
								} else {
									for( Tag inbt : listTag ) {
										boolean containsNone = true;
										
										for( Tag value : listTag1 ) {
											if( containsNBT( inbt, value ) ) {
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
								return nbt1.equals( nbt2 );
							}
						}
					} else {
						return false;
					}
				}
			}
		}
	}
	
	private boolean containsNoneNBT( @Nullable Tag nbt1, @Nullable Tag nbt2 ) {
		
		if( nbt1 == nbt2 ) {
			return false;
		} else {
			if( nbt1 == null ) {
				return false;
			} else {
				if( nbt2 == null ) {
					return true;
				} else {
					if( nbt1.getClass().equals( nbt2.getClass() ) ) {
						if( nbt1 instanceof CompoundTag compoundTag ) {
							CompoundTag compoundTag1 = (CompoundTag)nbt2;
							
							for( String key : compoundTag.getAllKeys() ) {
								Tag inbt1 = compoundTag.get( key );
								if( !containsNoneNBT( inbt1, compoundTag1.get( key ) ) ) {
									return false;
								}
							}
							return true;
						} else {
							if( nbt1 instanceof ListTag listTag ) {
								ListTag listTag1 = (ListTag)nbt2;
								if( listTag.isEmpty() ) {
									return false;
								} else {
									for( Tag inbt : listTag ) {
										boolean contains = false;
										
										for( Tag value : listTag1 ) {
											if( !containsNoneNBT( inbt, value ) ) {
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
								return !nbt1.equals( nbt2 );
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
	
	@Nonnull
	@Override
	public IIngredientSerializer<? extends Ingredient> getSerializer() {
		
		return IngredientSerializers.NBT_INGREDIENT;
	}
	
	@Nonnull
	@Override
	public JsonElement toJson() {
		
		JsonObject json = new JsonObject();
		json.addProperty(
			"type",
			Objects.requireNonNull( CraftingHelper.getID( IngredientSerializers.NBT_INGREDIENT ) ).toString()
		);
		json.addProperty( "item", BuiltInRegistries.ITEM.getKey( stack.getItem() ).toString() );
		json.addProperty( "count", stack.getCount() );
		if( stack.hasTag() ) {
			json.addProperty( "nbt", stack.getOrCreateTag().toString() );
		}
		return json;
	}
	
	//package-private
	ItemStack getStack() {
		
		return stack;
	}
	
	//package-private
	MatchType getMatchType() {
		
		return matchType;
	}
}
