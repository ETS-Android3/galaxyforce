package com.danosoftware.galaxyforce.vibration;

import com.danosoftware.galaxyforce.options.OptionVibration;

public interface Vibration
{

    /**
     * Vibrate for specified time.
     * 
     * Will only vibrate if enabled and supported by device.
     * 
     * @param vibrateTime
     */
    public void vibrate(VibrateTime vibrateTime);

    /**
     * Set whether device should vibrate.
     * 
     * Will only have an impact if vibration is supported by device.
     * 
     * @param vibrateEnabled
     */
    public void setVibrationEnabled(OptionVibration optionVibration);

}