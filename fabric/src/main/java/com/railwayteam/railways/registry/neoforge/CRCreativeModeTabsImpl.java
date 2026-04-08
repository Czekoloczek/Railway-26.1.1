package com.railwayteam.railways.registry.neoforge;

import com.railwayteam.railways.Railways;
import com.railwayteam.railways.registry.CRBlocks;
import com.railwayteam.railways.registry.CRCreativeModeTabs.RegistrateDisplayItemsGenerator;
import com.railwayteam.railways.registry.CRCreativeModeTabs.Tabs;
import com.railwayteam.railways.registry.CRPalettes;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;

import java.util.function.Supplier;

import static com.railwayteam.railways.registry.CRItems.ITEM_CONDUCTOR_CAP;

/**
 * Fabric implementation of CRCreativeModeTabsImpl.
 * Uses Fabric's FabricItemGroup API instead of NeoForge's DeferredRegister.
 *
 * TODO: Verify FabricItemGroup API for MC 26.1.1 – the API may have changed.
 */
public class CRCreativeModeTabsImpl {

    private static CreativeModeTab mainTab;
    private static CreativeModeTab tracksTab;
    private static CreativeModeTab palettesTab;

    public static final ResourceKey<CreativeModeTab> MAIN_TAB_KEY =
            ResourceKey.create(Registries.CREATIVE_MODE_TAB, Railways.asResource("main"));
    public static final ResourceKey<CreativeModeTab> TRACKS_TAB_KEY =
            ResourceKey.create(Registries.CREATIVE_MODE_TAB, Railways.asResource("tracks"));
    public static final ResourceKey<CreativeModeTab> PALETTES_TAB_KEY =
            ResourceKey.create(Registries.CREATIVE_MODE_TAB, Railways.asResource("palettes"));

    // Holder-like accessor for compatibility with common code calling .get() / .getKey()
    public static final TabHolder<CreativeModeTab> MAIN_TAB = new TabHolder<>(
            MAIN_TAB_KEY, () -> mainTab);
    public static final TabHolder<CreativeModeTab> TRACKS_TAB = new TabHolder<>(
            TRACKS_TAB_KEY, () -> tracksTab);
    public static final TabHolder<CreativeModeTab> PALETTES_TAB = new TabHolder<>(
            PALETTES_TAB_KEY, () -> palettesTab);

    /** Register all creative tabs. Call from mod initializer. */
    public static void register() {
        mainTab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MAIN_TAB_KEY.location(),
                FabricItemGroup.builder()
                        .title(Component.translatable("itemGroup.railways"))
                        .icon(() -> ITEM_CONDUCTOR_CAP.get(DyeColor.BLUE).asStack())
                        .displayItems(new RegistrateDisplayItemsGenerator(Tabs.MAIN)::accept)
                        .build());

        tracksTab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TRACKS_TAB_KEY.location(),
                FabricItemGroup.builder()
                        .title(Component.translatable("itemGroup.railways_tracks"))
                        .icon(CRBlocks.DARK_OAK_TRACK::asStack)
                        .displayItems(new RegistrateDisplayItemsGenerator(Tabs.TRACK)::accept)
                        .build());

        palettesTab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, PALETTES_TAB_KEY.location(),
                FabricItemGroup.builder()
                        .title(Component.translatable("itemGroup.railways_palettes"))
                        .icon(() -> CRPalettes.Styles.BOILER.get(DyeColor.RED).asStack())
                        .displayItems(new RegistrateDisplayItemsGenerator(Tabs.PALETTES)::accept)
                        .build());
    }

    /** Also accepts Fabric client-side tab-switching by delegating to Railways.registrate(). */
    public static void useBaseTab() {
        Railways.registrate().setCreativeTab(MAIN_TAB_KEY);
    }

    public static void useTracksTab() {
        Railways.registrate().setCreativeTab(TRACKS_TAB_KEY);
    }

    public static void usePalettesTab() {
        Railways.registrate().setCreativeTab(PALETTES_TAB_KEY);
    }

    public static ResourceKey<CreativeModeTab> getBaseTabKey() { return MAIN_TAB_KEY; }
    public static ResourceKey<CreativeModeTab> getTracksTabKey() { return TRACKS_TAB_KEY; }
    public static ResourceKey<CreativeModeTab> getPalettesTabKey() { return PALETTES_TAB_KEY; }

    /** Minimal holder providing .get() and .getKey() used by common code. */
    public static class TabHolder<T> {
        private final ResourceKey<T> key;
        private final Supplier<T> supplier;

        TabHolder(ResourceKey<T> key, Supplier<T> supplier) {
            this.key = key;
            this.supplier = supplier;
        }

        public T get() { return supplier.get(); }
        public ResourceKey<T> getKey() { return key; }
    }
}
