package games.brennan.keeptrim.neoforge;

import games.brennan.keeptrim.KeepTrim;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/**
 * NeoForge entrypoint. Runs common init.
 * All gameplay logic is mixin-driven from the common module.
 */
@Mod(KeepTrimNeoForge.MOD_ID)
public final class KeepTrimNeoForge {

    public static final String MOD_ID = "keeptrim";

    public KeepTrimNeoForge(IEventBus modBus) {
        KeepTrim.init();
    }
}
