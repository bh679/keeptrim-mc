package games.brennan.keeptrim.repair;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import org.junit.jupiter.api.Test;

class RepairTrimPickerTest {

    /** Row-major index in a 3x3 grid. */
    private static int at(int x, int y) {
        return y * 3 + x;
    }

    private static int pick3x3(int... filled) {
        Set<Integer> set = new java.util.HashSet<>();
        for (int i : filled) set.add(i);
        return RepairTrimPicker.pickIndex(3, 3, set::contains);
    }

    @Test
    void leftMostWinsRegardlessOfRow() {
        // item A bottom-left, item B top-right: A is further left, so A wins
        assertEquals(at(0, 2), pick3x3(at(0, 2), at(2, 0)));
    }

    @Test
    void sameColumnTopMostWins() {
        assertEquals(at(1, 0), pick3x3(at(1, 2), at(1, 0)));
    }

    @Test
    void sameRowLeftMostWins() {
        assertEquals(at(0, 1), pick3x3(at(2, 1), at(0, 1)));
    }

    @Test
    void vanillaRowMajorOrderIsNotUsed() {
        // vanilla's "first non-empty" would pick (2,0); the rule picks (0,1)
        assertEquals(at(0, 1), pick3x3(at(2, 0), at(0, 1)));
    }

    @Test
    void twoByTwoGrid() {
        // 2x2: index = y*2+x. bottom-left (0,1)=2 beats top-right (1,0)=1
        assertEquals(2, RepairTrimPicker.pickIndex(2, 2, i -> i == 1 || i == 2));
    }

    @Test
    void singleColumnInput() {
        // a trimmed CraftingInput may be 1 wide: indices 0,1 are top,bottom
        assertEquals(0, RepairTrimPicker.pickIndex(1, 2, i -> true));
    }

    @Test
    void nothingEligible() {
        assertEquals(RepairTrimPicker.NONE, RepairTrimPicker.pickIndex(3, 3, i -> false));
    }

    @Test
    void degenerateGrid() {
        assertEquals(RepairTrimPicker.NONE, RepairTrimPicker.pickIndex(0, 3, i -> true));
        assertEquals(RepairTrimPicker.NONE, RepairTrimPicker.pickIndex(3, 3, null));
    }
}
