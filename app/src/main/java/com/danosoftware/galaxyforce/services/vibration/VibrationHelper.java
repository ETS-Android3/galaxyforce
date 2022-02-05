package com.danosoftware.galaxyforce.services.vibration;

import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.os.Vibrator;
import android.os.VibratorManager;
import androidx.annotation.RequiresApi;

public class VibrationHelper {

  /**
   * This helper uses the VibratorManager class that is not available to older handsets so has been
   * moved in a separate class to avoid errors on these handsets.
   */
  @RequiresApi(api = VERSION_CODES.S)
  public static Vibrator getModernVibrator(Context context) {
    VibratorManager vbMgr = (VibratorManager) context
        .getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
    return vbMgr.getDefaultVibrator();
  }
}
