/*
 * Steam 'n' Rails
 * Copyright (c) 2022-2025 The Railways Team
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

package com.railwayteam.railways.util.packet;

import com.railwayteam.railways.multiloader.S2CPacket;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.trains.entity.Train;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.UUID;

public class SplitTrainEndPacket implements S2CPacket {
    final UUID newTrainId;
    final UUID originalTrainOwner;
    final double speed;

    public SplitTrainEndPacket(UUID newTrainId, UUID originalTrainOwner, double speed) {
        this.newTrainId = newTrainId;
        this.originalTrainOwner = originalTrainOwner;
        this.speed = speed;
    }

    public SplitTrainEndPacket(FriendlyByteBuf buf) {
        this.newTrainId = buf.readUUID();
        this.originalTrainOwner = buf.readUUID();
        this.speed = buf.readDouble();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeUUID(this.newTrainId);
        buffer.writeUUID(this.originalTrainOwner);
        buffer.writeDouble(this.speed);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle(Minecraft mc) {
        Level level = mc.level;
        if (level != null) {
            Train train = CreateClient.RAILWAYS.trains.get(newTrainId);
            if (train == null) {
                try {
                    train = new Train(newTrainId, null, null, new ArrayList<>(), new ArrayList<>(), false, 0);
                    CreateClient.RAILWAYS.trains.put(newTrainId, train);
                } catch (Exception e) {
                    // Failed to create new train
                }
            }
            if (train != null)
                train.speed = this.speed;
        }
    }
}

