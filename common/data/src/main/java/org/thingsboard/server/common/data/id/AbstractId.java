package org.thingsboard.server.common.data.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.thingsboard.server.common.data.EntityType;

import java.util.UUID;

public class AbstractId extends UUIDBased implements EntityId {
    private static final long serialVersionUID = 1L;

    @JsonCreator
    public AbstractId(@JsonProperty("id") UUID id) {
        super(id);
    }

    public static AbstractId fromString(String abstractId) {
        return new AbstractId(UUID.fromString(abstractId));
    }

    @Override
    public EntityType getEntityType() {
        return null;
    }
}
