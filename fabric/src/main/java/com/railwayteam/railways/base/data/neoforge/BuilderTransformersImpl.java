package com.railwayteam.railways.base.data.neoforge;

import com.railwayteam.railways.content.buffer.MonoTrackBufferBlock;
import com.railwayteam.railways.content.buffer.TrackBufferBlock;
import com.railwayteam.railways.content.buffer.headstock.CopycatHeadstockBarsBlock;
import com.railwayteam.railways.content.buffer.headstock.CopycatHeadstockBlock;
import com.railwayteam.railways.content.buffer.headstock.HeadstockBlock;
import com.railwayteam.railways.content.buffer.single_deco.GenericDyeableSingleBufferBlock;
import com.railwayteam.railways.content.buffer.single_deco.LinkPinBlock;
import com.railwayteam.railways.content.conductor.vent.VentBlock;
import com.railwayteam.railways.content.conductor.whistle.ConductorWhistleFlagBlock;
import com.railwayteam.railways.content.coupling.coupler.TrackCouplerBlock;
import com.railwayteam.railways.content.custom_bogeys.blocks.base.CRBogeyBlock;
import com.railwayteam.railways.content.custom_bogeys.special.invisible.InvisibleBogeyBlock;
import com.railwayteam.railways.content.custom_bogeys.special.monobogey.AbstractMonoBogeyBlock;
import com.railwayteam.railways.content.custom_bogeys.special.monobogey.InvisibleMonoBogeyBlock;
import com.railwayteam.railways.content.custom_bogeys.special.monobogey.MonoBogeyBlock;
import com.railwayteam.railways.content.custom_tracks.casing.CasingCollisionBlock;
import com.railwayteam.railways.content.custom_tracks.generic_crossing.GenericCrossingBlock;
import com.railwayteam.railways.content.handcar.HandcarBlock;
import com.railwayteam.railways.content.palettes.boiler.BoilerBlock;
import com.railwayteam.railways.content.palettes.boiler.BoilerBlock.Wrapping;
import com.railwayteam.railways.content.palettes.smokebox.PalettesSmokeboxBlock;
import com.railwayteam.railways.content.semaphore.SemaphoreBlock;
import com.railwayteam.railways.content.smokestack.block.AbstractSmokeStackBlock;
import com.railwayteam.railways.content.smokestack.block.DieselSmokeStackBlock;
import com.railwayteam.railways.content.smokestack.block.SmokeStackBlock;
import com.railwayteam.railways.content.switches.TrackSwitchBlock;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * Fabric implementation of BuilderTransformersImpl.
 *
 * NeoForge uses custom OBJ/JSON models defined in content/**/neoforge/ subpackages.
 * On Fabric those model files are excluded. This implementation returns identity
 * (no-op) transformers as stubs so that blocks are registered without model
 * configuration – they will render as missing-texture cubes until per-block
 * Fabric models are implemented.
 *
 * TODO: Implement each method with proper Fabric blockstate/model generation.
 *       Reference: content/**/fabric/ directories from the 1.20.1 Fabric build.
 */
public class BuilderTransformersImpl {

    // ---------- Bogey transformers ----------

    public static <B extends MonoBogeyBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> monobogey() {
        return b -> b;
    }

    public static <B extends InvisibleBogeyBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> invisibleBogey() {
        return b -> b;
    }

    public static <B extends InvisibleMonoBogeyBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> invisibleMonoBogey() {
        return b -> b;
    }

    public static <B extends CRBogeyBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> standardBogey() {
        return b -> b;
    }

    public static <B extends CRBogeyBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> wideBogey() {
        return b -> b;
    }

    public static <B extends CRBogeyBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> narrowBogey() {
        return b -> b;
    }

    // ---------- Smoke stacks ----------

    public static <B extends SmokeStackBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> smokestack(
            boolean rotates, ResourceLocation modelLoc) {
        return b -> b;
    }

    public static NonNullBiConsumer<DataGenContext<Block, SmokeStackBlock>, RegistrateBlockstateProvider>
    defaultSmokeStack(String variant, SmokeStackBlock.RotationType rotType) {
        return (c, p) -> {};
    }

    public static <B extends DieselSmokeStackBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> dieselSmokeStack() {
        return b -> b;
    }

    // ---------- Misc content ----------

    public static <B extends SemaphoreBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> semaphore() {
        return b -> b;
    }

    public static <B extends TrackCouplerBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> trackCoupler() {
        return b -> b;
    }

    public static <B extends TrackSwitchBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> trackSwitch(boolean andesite) {
        return b -> b;
    }

    public static <B extends ConductorWhistleFlagBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> conductorWhistleFlag() {
        return b -> b;
    }

    public static <B extends VentBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> conductorVent() {
        return b -> b;
    }

    public static <B extends CasingCollisionBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> casingCollision() {
        return b -> b;
    }

    public static <B extends HandcarBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> handcar() {
        return b -> b;
    }

    public static <B extends GenericCrossingBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> genericCrossing() {
        return b -> b;
    }

    // ---------- Loco metal palette ----------

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> locoMetalBase(
            @Nullable DyeColor color, @Nullable String type) {
        return b -> b;
    }

    public static <B extends RotatedPillarBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> locoMetalPillar(
            @Nullable DyeColor color) {
        return b -> b;
    }

    public static <B extends PalettesSmokeboxBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> locoMetalSmokeBox(
            @Nullable DyeColor color) {
        return b -> b;
    }

    public static <B extends BoilerBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> locoMetalBoiler(
            @Nullable DyeColor color, @Nullable Wrapping wrapping) {
        return b -> b;
    }

    // ---------- Buffer transformers ----------

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> variantBuffer() {
        return b -> b;
    }

    public static <I extends Item, P> NonNullUnaryOperator<ItemBuilder<I, P>> variantBufferItem() {
        return b -> b;
    }

    public static <B extends CopycatHeadstockBarsBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> copycatHeadstockBars() {
        return b -> b;
    }

    public static <B extends TrackBufferBlock<?>, P> NonNullUnaryOperator<BlockBuilder<B, P>> bufferBlockState(
            Function<BlockState, ResourceLocation> modelFunc,
            Function<BlockState, net.minecraft.core.Direction> facingFunc) {
        return b -> b;
    }

    public static <B extends MonoTrackBufferBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> monoBuffer() {
        return b -> b;
    }

    public static <B extends LinkPinBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> linkAndPin() {
        return b -> b;
    }

    public static <B extends HeadstockBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> headstock() {
        return b -> b;
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> invisibleBlockState() {
        return b -> b.blockstate((c, p) -> p.simpleBlock(c.get(), p.models().getExistingFile(
                net.minecraft.resources.ResourceLocation.parse("minecraft:block/air"))));
    }

    public static <B extends CopycatHeadstockBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> copycatHeadstock() {
        return b -> b;
    }

    public static <I extends Item, P> NonNullUnaryOperator<ItemBuilder<I, P>> copycatHeadstockItem() {
        return b -> b;
    }

    public static <B extends GenericDyeableSingleBufferBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> bigBuffer() {
        return b -> b;
    }

    public static <B extends GenericDyeableSingleBufferBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> smallBuffer() {
        return b -> b;
    }
}
