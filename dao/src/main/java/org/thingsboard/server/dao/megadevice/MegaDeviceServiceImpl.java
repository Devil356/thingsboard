/**
 * Copyright © 2016-2021 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.dao.megadevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thingsboard.server.common.data.Megadevice;
import org.thingsboard.server.dao.building.BuildingJpaRepository;
import org.thingsboard.server.dao.model.sql.MegaDeviceEntity;
import org.thingsboard.server.dao.model.sql.RoomEntity;
import org.thingsboard.server.dao.room.RoomJpaRepository;
import org.thingsboard.server.dao.room.RoomService;

@Service
public class MegaDeviceServiceImpl implements MegaDeviceService {

    @Autowired
    private MegaDeviceJpaRepository megaDeviceJpaRepository;

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Override
    public Megadevice save(Megadevice megadevice, Integer roomId) {
        megadevice.setRoom(roomJpaRepository.getOne(roomId.longValue()));
        return megaDeviceJpaRepository.save(megadevice);
    }
}
