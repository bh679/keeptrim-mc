package games.brennan.keeptrim.fabric;

import games.brennan.keeptrim.KeepTrim;
import net.fabricmc.api.ModInitializer;

/**
 * Fabric entrypoint. Runs common init.
 * All gameplay logic is mixin-driven from the common module.
 */
public final class KeepTrimFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        KeepTrim.init();
    }
}
