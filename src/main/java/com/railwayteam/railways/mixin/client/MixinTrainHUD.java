/*
 * Steam 'n' Rails
 * Copyright (c) 2022-2024 The Railways Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.railwayteam.railways.mixin.client;

import com.railwayteam.railways.content.switches.TrainHUDSwitchExtension;
import com.simibubi.create.content.contraptions.actors.trainControls.ControlsHandler;
import com.simibubi.create.content.trains.TrainHUD;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.Train;
import java.util.UUID;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = TrainHUD.class, remap = false)
public class MixinTrainHUD {
    @Unique private static boolean railways$hasLastSpeedPos = false;
    @Unique private static double railways$lastSpeedX;
    @Unique private static double railways$lastSpeedZ;
    @Unique private static UUID railways$lastTrain;

    @Inject(method = "tick", at = @At("HEAD"))
    private static void tickHook(CallbackInfo ci) {
        TrainHUDSwitchExtension.tick();
        railways$syncDisplayedSpeed();
    }

    @Unique
    private static void railways$syncDisplayedSpeed() {
        if (!(ControlsHandler.getContraption() instanceof CarriageContraptionEntity cce)) {
            railways$hasLastSpeedPos = false;
            railways$lastTrain = null;
            return;
        }

        Carriage carriage = cce.getCarriage();
        if (carriage == null)
            return;

        Train train = carriage.train;
        if (train == null)
            return;

        UUID trainId = train.id;
        double x = cce.getX();
        double z = cce.getZ();

        if (!trainId.equals(railways$lastTrain)) {
            railways$hasLastSpeedPos = false;
            railways$lastTrain = trainId;
        }

        if (railways$hasLastSpeedPos) {
            double dx = x - railways$lastSpeedX;
            double dz = z - railways$lastSpeedZ;
            double computed = Math.sqrt(dx * dx + dz * dz);
            train.speed = computed;
        }

        railways$lastSpeedX = x;
        railways$lastSpeedZ = z;
        railways$hasLastSpeedPos = true;
    }
}
