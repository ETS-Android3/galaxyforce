package com.danosoftware.galaxyforce.waves.config.aliens.exploding;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class DelayedFollowerExplosionConfig extends ExplosionConfig {

    private final Float delayTime;

    @Builder
    public DelayedFollowerExplosionConfig(
            @NonNull final Float delayTime
    ) {
        super(ExplosionConfigType.FOLLOWER_DELAYED);
        this.delayTime = delayTime;
    }
}
