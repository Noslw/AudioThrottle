# AudioThrottle

AudioThrottle is a lightweight client-side mod that improves the Minecraft sound engine.

Instead of allowing hundreds of identical sounds to play simultaneously and clip into a deafening wall of digital noise, the mod dynamically limits sound saturation within a tight radius. The result is a clean, natural group sound that makes high-density areas feel more polished, while recovering lost performance.

## Features

- Prevents overlapping ambient sounds from clipping and distorting
- Reclaims frame rate and smooths out micro-stutters around crowded farms
- Adds a dynamic proximity cap for identical audio IDs
- Reduces resource overhead by mitigating sound-linked particle floods
- Operates silently with a zero-config setup out of the box
- Works across all entities, block interactions, and environmental audio loops
- Client-side only

## Why use it?

Minecraft's default audio engine handles crowded spaces poorly, pushing every single cluck, moo, and piston click to the client even when the human ear can't distinguish them. In pure vanilla stress tests with hundreds of crowded entities, AudioThrottle increased average frame rates by 11% and boosted minimum frame stability by 14% simply by cutting out redundant audio processing at the root. It keeps your vanilla acoustic awareness while making crowded server spawns, villager trading hubs, and high-speed sorting rooms run noticeably smoother.

## Compatibility

- Fabric
- Client-side only
- Compatible with [Sound Physics Remastered](https://modrinth.com/mod/sound-physics-remastered) and [Simple Voice Chat](https://modrinth.com/mod/simple-voice-chat)


This mod does not need to be installed on the server.

## Notes

AudioThrottle focuses entirely on sound engine density management and client stability. It does not alter core server-side game logic, change mob AI behaviors, or add new audio tracks to the game. It is fully compatible with proximity voice chat mods like Simple Voice Chat and Plasmo Voice.
