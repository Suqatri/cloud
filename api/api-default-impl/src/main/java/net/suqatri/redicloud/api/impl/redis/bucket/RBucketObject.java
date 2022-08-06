package net.suqatri.redicloud.api.impl.redis.bucket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.suqatri.redicloud.api.redis.bucket.IRBucketObject;
import net.suqatri.redicloud.api.redis.bucket.IRedissonBucketManager;
import net.suqatri.redicloud.commons.function.future.FutureAction;

public abstract class RBucketObject implements IRBucketObject {

    @JsonIgnore
    @Setter @Getter
    private IRedissonBucketManager manager;

    @Override
    public IRBucketObject update() {
        return this.manager.publishChanges(this);
    }

    @Override
    public FutureAction<IRBucketObject> updateAsync() {
        return this.manager.publishChangesAsync(this);
    }

    @Override
    public String getRedisKey() {
        return this.manager.getRedisKey(this.getIdentifier());
    }

    @Override
    public String getRedisPrefix() {
        return this.manager.getRedisPrefix();
    }
}
