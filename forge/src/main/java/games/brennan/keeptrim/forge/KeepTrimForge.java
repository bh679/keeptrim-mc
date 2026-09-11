package games.brennan.keeptrim.forge;

import games.brennan.keeptrim.KeepTrim;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

/**
 * Forge entrypoint. Runs common init.
 * All gameplay logic is mixin-driven from the common module.
 */
@Mod("keeptrim")
public final class KeepTrimForge {

    public KeepTrimForge(IEventBus modBus) {
        KeepTrim.init();
    }
}
