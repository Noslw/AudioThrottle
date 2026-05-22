package com.noslow.audiothrottle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class AudioThrottleConfig {
	public static final int MAX_IDENTICAL_SOUNDS_PER_RADIUS = 5;
	public static final double SCAN_RADIUS = 8.0D;
	public static final int CHECK_WINDOW_TICKS = 20;

	private AudioThrottleConfig() {
	}
}
