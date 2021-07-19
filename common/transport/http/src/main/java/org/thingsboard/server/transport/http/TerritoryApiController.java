package org.thingsboard.server.transport.http;

import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.thingsboard.server.common.data.DeviceTransportType;
import org.thingsboard.server.common.data.TerritoryTransportType;
import org.thingsboard.server.common.transport.TransportService;
import org.thingsboard.server.common.transport.adaptor.JsonConverter;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class TerritoryApiController {

    @Autowired
    private HttpTransportContext transportContext;

    @RequestMapping(value = "/territory", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity> postTerritory(@RequestBody String territoryJson) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();
        transportContext.getTransportService().process(TerritoryTransportType.DEFAULT, ValidateDeviceTokenRequestMsg.newBuilder().setToken(deviceToken).build(),
                new DeviceApiController.DeviceAuthCallback(transportContext, responseWriter, sessionInfo -> {
                    TransportService transportService = transportContext.getTransportService();
                    transportService.process(sessionInfo, JsonConverter.convertToTelemetryProto(new JsonParser().parse(json)),
                            new DeviceApiController.HttpOkCallback(responseWriter));
                    reportActivity(sessionInfo);
                }));
        return result;
    }
}
