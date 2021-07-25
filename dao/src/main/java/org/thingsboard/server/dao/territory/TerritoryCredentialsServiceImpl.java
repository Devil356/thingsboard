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
package org.thingsboard.server.dao.territory;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.common.data.device.credentials.BasicMqttCredentials;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.id.TerritoryId;
import org.thingsboard.server.common.data.security.TerritoryCredentials;
import org.thingsboard.server.common.msg.EncryptionUtil;
import org.thingsboard.server.dao.entity.AbstractEntityService;
import org.thingsboard.server.dao.exception.DataValidationException;
import org.thingsboard.server.dao.service.DataValidator;
import org.thingsboard.server.dao.util.mapping.JacksonUtil;

@Service
@Slf4j
public class TerritoryCredentialsServiceImpl extends AbstractEntityService implements TerritoryCredentialsService {

    @Autowired
    private TerritoryCredentialsDao territoryCredentialsDao;

    @Autowired
    private TerritoryService territoryService;

    @Override
    public TerritoryCredentials findTerritoryCredentialsByTerritoryId(TenantId tenantId, TerritoryId territoryId) {
        return null;
    }

    @Override
    public TerritoryCredentials findTerritoryCredentialsByCredentialsId(String credentialsId) {
        return null;
    }

    @Override
    public TerritoryCredentials updateTerritoryCredentials(TenantId tenantId, TerritoryCredentials territoryCredentials) {
        return null;
    }

    @Override
    public TerritoryCredentials createTerritoryCredentials(TenantId tenantId, TerritoryCredentials territoryCredentials) {
        return saveOrUpdate(tenantId, territoryCredentials);
    }

    @Override
    public void deleteTerritoryCredentials(TenantId tenantId, TerritoryCredentials territoryCredentials) {

    }

    private TerritoryCredentials saveOrUpdate(TenantId tenantId, TerritoryCredentials territoryCredentials) {
        if(territoryCredentials.getCredentialsType() == null){
            throw new DataValidationException("Device credentials type should be specified");
        }
        switch (territoryCredentials.getCredentialsType()) {
            case X509_CERTIFICATE:
                formatCertData(territoryCredentials);
                break;
            case MQTT_BASIC:
                formatSimpleMqttCredentials(territoryCredentials);
                break;
        }
        log.trace("Executing updateDeviceCredentials [{}]", territoryCredentials);
        credentialsValidator.validate(territoryCredentials, id -> tenantId);
        try {
            return territoryCredentialsDao.save(tenantId, territoryCredentials);
        } catch (Exception t) {
            ConstraintViolationException e = extractConstraintViolationException(t).orElse(null);
            if (e != null && e.getConstraintName() != null
                    && (e.getConstraintName().equalsIgnoreCase("territory_credentials_id_unq_key") || e.getConstraintName().equalsIgnoreCase("territory_credentials_territory_id_unq_key"))) {
                throw new DataValidationException("Specified credentials are already registered!");
            } else {
                throw t;
            }
        }
    }

    private void formatSimpleMqttCredentials(TerritoryCredentials territoryCredentials) {
        BasicMqttCredentials mqttCredentials;
        try {
            mqttCredentials = JacksonUtil.fromString(territoryCredentials.getCredentialsValue(), BasicMqttCredentials.class);
            if (mqttCredentials == null) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            throw new DataValidationException("Invalid credentials body for simple mqtt credentials!");
        }
        if (StringUtils.isEmpty(mqttCredentials.getClientId()) && StringUtils.isEmpty(mqttCredentials.getUserName())) {
            throw new DataValidationException("Both mqtt client id and user name are empty!");
        }
        if (StringUtils.isEmpty(mqttCredentials.getClientId())) {
            territoryCredentials.setCredentialsId(mqttCredentials.getUserName());
        } else if (StringUtils.isEmpty(mqttCredentials.getUserName())) {
            territoryCredentials.setCredentialsId(EncryptionUtil.getSha3Hash(mqttCredentials.getClientId()));
        } else {
            territoryCredentials.setCredentialsId(EncryptionUtil.getSha3Hash("|", mqttCredentials.getClientId(), mqttCredentials.getUserName()));
        }
        if (!StringUtils.isEmpty(mqttCredentials.getPassword())) {
            mqttCredentials.setPassword(mqttCredentials.getPassword());
        }
        territoryCredentials.setCredentialsValue(JacksonUtil.toString(mqttCredentials));
    }

    private void formatCertData(TerritoryCredentials territoryCredentials) {
        String cert = EncryptionUtil.trimNewLines(territoryCredentials.getCredentialsValue());
        String sha3Hash = EncryptionUtil.getSha3Hash(cert);
        territoryCredentials.setCredentialsId(sha3Hash);
        territoryCredentials.setCredentialsValue(cert);
    }


    private DataValidator<TerritoryCredentials> credentialsValidator =
            new DataValidator<TerritoryCredentials>() {

                @Override
                protected void validateCreate(TenantId tenantId, TerritoryCredentials territoryCredentials) {
                    if (territoryCredentialsDao.findByTerritoryId(tenantId, territoryCredentials.getTerritoryId().getId()) != null) {
                        throw new DataValidationException("Credentials for this territory are already specified!");
                    }
                    if (territoryCredentialsDao.findByCredentialsId(tenantId, territoryCredentials.getCredentialsId()) != null) {
                        throw new DataValidationException("Territory credentials are already assigned to another territory!");
                    }
                }

                @Override
                protected void validateUpdate(TenantId tenantId, TerritoryCredentials territoryCredentials) {
                    if (territoryCredentialsDao.findById(tenantId, territoryCredentials.getUuidId()) == null) {
                        throw new DataValidationException("Unable to update non-existent device credentials!");
                    }
                    TerritoryCredentials existingCredentials = territoryCredentialsDao.findByCredentialsId(tenantId, territoryCredentials.getCredentialsId());
                    if (existingCredentials != null && !existingCredentials.getId().equals(territoryCredentials.getId())) {
                        throw new DataValidationException("Territory credentials are already assigned to another territory!");
                    }
                }

                @Override
                protected void validateDataImpl(TenantId tenantId, TerritoryCredentials territoryCredentials) {
                    if (territoryCredentials.getTerritoryId() == null) {
                        throw new DataValidationException("Territory credentials should be assigned to device!");
                    }
                    if (territoryCredentials.getCredentialsType() == null) {
                        throw new DataValidationException("Territory credentials type should be specified!");
                    }
                    if (StringUtils.isEmpty(territoryCredentials.getCredentialsId())) {
                        throw new DataValidationException("Territory credentials id should be specified!");
                    }
                    Territory territory = territoryService.findTerritoryById(tenantId, territoryCredentials.getTerritoryId());
                    if (territory == null) {
                        throw new DataValidationException("Can't assign territory credentials to non-existent territory!");
                    }
                }
            };

}
