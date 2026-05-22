package com.noslow.audiothrottle;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class AudioThrottleClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		SoundTracker.clear();
	}
}
