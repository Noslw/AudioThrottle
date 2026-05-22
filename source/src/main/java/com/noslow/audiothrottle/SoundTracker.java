package com.noslow.audiothrottle;

import java.util.ArrayDeque;
import java.util.Deque;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public final class SoundTracker {
	private static final Deque<TrackedSound> RECENT_SOUNDS = new ArrayDeque<>();
	private static final double SCAN_RADIUS_SQUARED = AudioThrottleConfig.SCAN_RADIUS * AudioThrottleConfig.SCAN_RADIUS;
	private static long lastObservedTick = Long.MIN_VALUE;

	private SoundTracker() {
	}

	public static synchronized boolean shouldThrottle(Identifier soundId, double x, double y, double z, long tick) {
		pruneExpired(tick);

		int matchingSounds = 0;
		for (TrackedSound trackedSound : RECENT_SOUNDS) {
			if (trackedSound.matches(soundId, x, y, z)) {
				matchingSounds++;
				if (matchingSounds >= AudioThrottleConfig.MAX_IDENTICAL_SOUNDS_PER_RADIUS) {
					return true;
				}
			}
		}

		return false;
	}

	public static synchronized void log(Identifier soundId, double x, double y, double z, long tick) {
		pruneExpired(tick);
		RECENT_SOUNDS.addLast(new TrackedSound(soundId, x, y, z, tick));
	}

	public static synchronized void clear() {
		RECENT_SOUNDS.clear();
		lastObservedTick = Long.MIN_VALUE;
	}

	private static void pruneExpired(long tick) {
		if (tick < lastObservedTick) {
			clear();
		}

		lastObservedTick = tick;
		long oldestAllowedTick = tick - AudioThrottleConfig.CHECK_WINDOW_TICKS;
		while (!RECENT_SOUNDS.isEmpty() && RECENT_SOUNDS.peekFirst().tick() < oldestAllowedTick) {
			RECENT_SOUNDS.removeFirst();
		}
	}

	private record TrackedSound(Identifier soundId, double x, double y, double z, long tick) {
		private boolean matches(Identifier incomingSoundId, double incomingX, double incomingY, double incomingZ) {
			return soundId.equals(incomingSoundId) && squaredDistanceTo(incomingX, incomingY, incomingZ) <= SCAN_RADIUS_SQUARED;
		}

		private double squaredDistanceTo(double incomingX, double incomingY, double incomingZ) {
			double dx = x - incomingX;
			double dy = y - incomingY;
			double dz = z - incomingZ;
			return dx * dx + dy * dy + dz * dz;
		}
	}
}
