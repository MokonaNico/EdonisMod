package net.mokona.edonis.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab {
    public static final CreativeModeTab EDONIS_TAB = new CreativeModeTab("edonistab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.CITRINE.get());
        }
    };
}
