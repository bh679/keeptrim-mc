package games.brennan.keeptrim.repair;

import java.util.function.IntPredicate;

/**
 * Decides which crafting-grid slot's trim wins when two items are combined.
 *
 * <p>The rule is "left-most item, and if both share a column, top-most": the grid is
 * scanned column by column (x outer, y inner) and the first slot that passes the
 * predicate wins. Slots are indexed row-major ({@code y * width + x}), matching
 * {@code CraftingInput#getItem(int)}.
 *
 * <p>Pure integers and a predicate — no Minecraft types — so it is unit-testable off
 * the game classpath.
 */
public final class RepairTrimPicker {

    /** Returned when no slot passes the predicate. */
    public static final int NONE = -1;

    private RepairTrimPicker() {}

    /**
     * @param width     grid width (columns)
     * @param height    grid height (rows)
     * @param candidate true for a slot index that is eligible (e.g. non-empty)
     * @return the row-major index of the left-most, then top-most, eligible slot, or
     *         {@link #NONE} when nothing is eligible or the grid is degenerate
     */
    public static int pickIndex(int width, int height, IntPredicate candidate) {
        if (width <= 0 || height <= 0 || candidate == null) {
            return NONE;
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int index = y * width + x;
                if (candidate.test(index)) {
                    return index;
                }
            }
        }
        return NONE;
    }
}
