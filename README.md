# Keep Trim

A Minecraft mod (Fabric / Forge / NeoForge, MC 1.21.1) that stops the crafting grid
from stripping armor trims when you repair.

Vanilla lets you repair two damaged pieces of the same armor by combining them in a
crafting grid — but the result comes out **untrimmed**, even if both inputs were
trimmed. Keep Trim carries the trim over: the result keeps the trim of the
**left-most** piece (and if both are in the same column, the **top-most** one).

## Rules

- Left-most input wins; same column → top-most wins.
- If the winning piece has no trim but the other does, that trim is kept instead —
  a trim is never dropped outright.
- Neither trimmed → vanilla result, untouched.
- The anvil is not changed: vanilla already keeps the left item's trim there.

No config, no commands, no items. Works on the 2x2 inventory grid, the 3x3 crafting
table and any modded grid.

Server-side logic — a vanilla client sees the trimmed result normally. Install on the
server (singleplayer counts); installing on the client as well is harmless.

## Building

```bash
./gradlew build
```

Jars land in `{fabric,forge,neoforge}/build/libs/keeptrim-<loader>-<version>.jar`.
Unit tests: `./gradlew :common:test`.

## Licence

PolyForm Shield 1.0.0 — see `LICENSE`.
