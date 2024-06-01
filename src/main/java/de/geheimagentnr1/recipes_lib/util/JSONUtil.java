package de.geheimagentnr1.recipes_lib.util;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import lombok.extern.log4j.Log4j2;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;


@Log4j2
public class JSONUtil {
	
	
	@NotNull
	public static JsonObject buildComponentsJson( @NotNull ItemStack stack ) {
		
		return DataComponentPatch.CODEC.encode( stack.getComponentsPatch(), JsonOps.INSTANCE, new JsonObject() )
			.result()
			.map( JsonElement::getAsJsonObject )
			.orElseThrow();
	}
}
