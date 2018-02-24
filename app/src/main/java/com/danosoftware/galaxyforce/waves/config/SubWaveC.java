package com.danosoftware.galaxyforce.waves.config;

import com.danosoftware.galaxyforce.waves.AlienType;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRule;

/**
 * Created by Danny on 24/02/2018.
 */

public interface SubWaveC<T> {

    T getSubWaveRule();

    AlienType getAlien();
}
