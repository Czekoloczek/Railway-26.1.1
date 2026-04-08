package com.railwayteam.railways.registry.neoforge;

import com.railwayteam.railways.Railways;
import com.railwayteam.railways.content.fuel.psi.PortableFuelInterfaceBlock;
import com.railwayteam.railways.content.fuel.tank.FuelTankBlock;
import com.railwayteam.railways.content.fuel.tank.FuelTankItem;
import com.railwayteam.railways.content.fuel.tank.FuelTankModel;
import com.railwayteam.railways.content.fuel.tank.FuelTankMovementBehavior;
import com.simibubi.create.AllTags;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType;
import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceMovement;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

/**
 * Fabric implementation of CRBlocksImpl.
 *
 * Uses Registrate (provided transitively by Create Fly) for block registration.
 * FuelTankBlockStateGenerator is omitted; blockstate JSON lives under
 * src/main/resources/assets/railways/blockstates/ instead.
 */
public class CRBlocksImpl {

    private static final CreateRegistrate REGISTRATE = Railways.registrate();

    @SuppressWarnings("removal")
    public static final BlockEntry<FuelTankBlock> FUEL_TANK = REGISTRATE
            .block("fuel_tank", FuelTankBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.isRedstoneConductor((p1, p2, p3) -> true))
            .transform(pickaxeOnly())
            .onRegister(CreateRegistrate.blockModel(() -> FuelTankModel::standard))
            .transform(MountedFluidStorageType.mountedFluidStorage(CRMountedStorageTypesImpl.FUEL_TANK))
            .onRegister(MovementBehaviour.movementBehaviour(new FuelTankMovementBehavior()))
            .item(FuelTankItem::new)
            .build()
            .register();

    public static final BlockEntry<PortableFuelInterfaceBlock> PORTABLE_FUEL_INTERFACE = REGISTRATE
            .block("portable_fuel_interface", PortableFuelInterfaceBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY))
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> p.directionalBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
            .onRegister(movementBehaviour(new PortableStorageInterfaceMovement()))
            .item()
            .tag(AllTags.AllItemTags.CONTRAPTION_CONTROLLED.tag)
            .transform(customItemModel())
            .register();

    public static void init() {
        // Registration triggered by Railways.registrate().register() in entrypoint.
    }
}
