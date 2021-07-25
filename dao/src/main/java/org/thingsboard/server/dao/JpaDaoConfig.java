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
package org.thingsboard.server.dao;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Valerii Sosliuk
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan({
        "org.thingsboard.server.dao.sql",
        "org.thingsboard.server.dao.territory",
        "org.thingsboard.server.dao.building",
        "org.thingsboard.server.dao.room",
        "org.thingsboard.server.dao.megadevice"
})
@EnableJpaRepositories({
        "org.thingsboard.server.dao.sql",
        "org.thingsboard.server.dao.territory",
        "org.thingsboard.server.dao.building",
        "org.thingsboard.server.dao.room",
        "org.thingsboard.server.dao.megadevice"
})
@EntityScan({
        "org.thingsboard.server.dao.model.sql",
        "org.thingsboard.server.dao.territory",
        "org.thingsboard.server.dao.building",
        "org.thingsboard.server.dao.room",
        "org.thingsboard.server.dao.megadevice"
})
@EnableTransactionManagement
public class JpaDaoConfig {

}
