package games.brennan.keeptrim.mixin;

import games.brennan.keeptrim.repair.RepairTrimPicker;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Vanilla's crafting-grid repair builds a fresh {@link ItemStack} and only carries over
 * durability and curses, so an armor trim and a leather dye are silently lost. This
 * re-applies the trim and dye of the left-most (then top-most) input to the assembled
 * result. Whatever that input lacks stays as vanilla built it — the other input's trim
 * or dye is deliberately NOT used.
 */
@Mixin(RepairItemRecipe.class)
public abstract class RepairItemRecipeMixin {

    @Inject(method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;",
            at = @At("RETURN"))
    private void keeptrim$keepTrim(CraftingInput input, HolderLookup.Provider registries,
                                   CallbackInfoReturnable<ItemStack> cir) {
        ItemStack result = cir.getReturnValue();
        if (result == null || result.isEmpty()) {
            return;
        }
        int priority = RepairTrimPicker.pickIndex(input.width(), input.height(),
            i -> !input.getItem(i).isEmpty());
        if (priority == RepairTrimPicker.NONE) {
            return;
        }
        ItemStack source = input.getItem(priority);
        keeptrim$copy(source, result, DataComponents.TRIM);
        keeptrim$copy(source, result, DataComponents.DYED_COLOR);
    }

    /** Copies one component from the priority input onto the result, if it has it. */
    private static <T> void keeptrim$copy(ItemStack source, ItemStack result, DataComponentType<T> type) {
        T value = source.get(type);
        if (value != null) {
            result.set(type, value);
        }
    }
}
