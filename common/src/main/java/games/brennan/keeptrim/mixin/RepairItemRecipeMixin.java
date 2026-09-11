package games.brennan.keeptrim.mixin;

import games.brennan.keeptrim.repair.RepairTrimPicker;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Vanilla's crafting-grid repair builds a fresh {@link ItemStack} and only carries over
 * durability and curses, so an armor trim is silently lost. This re-applies the trim of
 * the left-most (then top-most) input to the assembled result. If that input carries no
 * trim, the other input's trim is used instead so a trim is never dropped outright.
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
        ArmorTrim trim = keeptrim$pickTrim(input);
        if (trim != null) {
            result.set(DataComponents.TRIM, trim);
        }
    }

    /** Priority input's trim, falling back to any other trimmed input; null if none. */
    private static ArmorTrim keeptrim$pickTrim(CraftingInput input) {
        int priority = RepairTrimPicker.pickIndex(input.width(), input.height(),
            i -> !input.getItem(i).isEmpty());
        if (priority == RepairTrimPicker.NONE) {
            return null;
        }
        ArmorTrim trim = input.getItem(priority).get(DataComponents.TRIM);
        if (trim != null) {
            return trim;
        }
        int fallback = RepairTrimPicker.pickIndex(input.width(), input.height(),
            i -> i != priority && input.getItem(i).has(DataComponents.TRIM));
        return fallback == RepairTrimPicker.NONE ? null : input.getItem(fallback).get(DataComponents.TRIM);
    }
}
