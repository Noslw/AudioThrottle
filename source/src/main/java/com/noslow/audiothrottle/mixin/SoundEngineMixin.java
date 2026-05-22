package com.noslow.audiothrottle.mixin;

import com.noslow.audiothrottle.SoundTracker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(SoundEngine.class)
public abstract class SoundEngineMixin {
	@Inject(
			target = @Desc(
					value = "play",
					args = SoundInstance.class,
					ret = SoundEngine.PlayResult.class
			),
			at = @At("HEAD"),
			cancellable = true
	)
	private void audiothrottle$throttleIdenticalSoundDensity(SoundInstance sound, CallbackInfoReturnable<SoundEngine.PlayResult> info) {
		Identifier soundId = sound.getIdentifier();
		double x = sound.getX();
		double y = sound.getY();
		double z = sound.getZ();
		long tick = audiothrottle$currentClientTick();

		if (SoundTracker.shouldThrottle(soundId, x, y, z, tick)) {
			info.setReturnValue(SoundEngine.PlayResult.NOT_STARTED);
			return;
		}

		SoundTracker.log(soundId, x, y, z, tick);
	}

	private static long audiothrottle$currentClientTick() {
		ClientLevel level = Minecraft.getInstance().level;
		if (level != null) {
			return level.getGameTime();
		}

		return System.nanoTime() / 50_000_000L;
	}
}
