package com.danosoftware.galaxyforce.waves;

import com.danosoftware.galaxyforce.flightpath.Path;
import com.danosoftware.galaxyforce.flightpath.new_refactor.Path2;
import com.danosoftware.galaxyforce.flightpath.translators.FlipXPointTranslator;
import com.danosoftware.galaxyforce.flightpath.translators.OffsetYPointTranslator;
import com.danosoftware.galaxyforce.flightpath.translators.PointTranslatorChain;

import java.util.Arrays;
import java.util.List;

import static com.danosoftware.galaxyforce.constants.GameConstants.GAME_WIDTH;

public enum SubWaveBuilder2
{

    /*
     * Each sub-wave consists of one or more sub-wave properties. Each sub-wave
     * property has sprites, paths, x-invert, y-invert, x-Offset, y-Offset,
     * number of aliens, delay between each alien, delay offset and restart
     * immediately
     */

    WAVE_01(true,
            new SubWaveProperty2(
                    AlienType.OCTOPUS,
                    Path2.TEST,
                    0,
                    0,
                    10,
                    false
            ),
            new SubWaveProperty2(
                    AlienType.OCTOPUS,
                    Path2.TEST,
                    0,
                    0,
                    10,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
                            .add(new OffsetYPointTranslator(20))
            )
    );


    /* references list of sub-waves */
    private List<SubWaveProperty2> waveList;

    /* repeat sub-wave until all destroyed */
    private boolean repeatSubWave;

    /**
     * construct wave
     */
    SubWaveBuilder2(boolean repeatSubWave, SubWaveProperty2... waveArray)
    {
        this.waveList = Arrays.asList(waveArray);
        this.repeatSubWave = repeatSubWave;
    }

    public List<SubWaveProperty2> getWaveList()
    {
        return waveList;
    }

    public boolean isRepeatSubWave()
    {
        return repeatSubWave;
    }
}
