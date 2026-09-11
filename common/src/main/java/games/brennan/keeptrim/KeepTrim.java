package games.brennan.keeptrim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common init. The whole feature is one mixin on the vanilla crafting-grid repair
 * recipe (see {@code games.brennan.keeptrim.mixin.RepairItemRecipeMixin}); this class
 * only owns the id and logger and announces the mod at startup.
 */
public final class KeepTrim {

    public static final String MOD_ID = "keeptrim";
    public static final Logger LOGGER = LoggerFactory.getLogger("KeepTrim");

    private KeepTrim() {}

    public static void init() {
        LOGGER.info("[KeepTrim] initialised — crafting-grid repair keeps the left-most (then top-most) item's armor trim");
    }
}
