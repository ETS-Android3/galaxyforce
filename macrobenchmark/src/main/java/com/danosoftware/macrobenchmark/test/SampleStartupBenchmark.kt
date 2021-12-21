package com.danosoftware.macrobenchmark.test

import android.content.Intent
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleStartupBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
            packageName = "com.danosoftware.galaxyforce",
            metrics = listOf(StartupTimingMetric()),
            iterations = 5,
            startupMode = StartupMode.COLD
    ) { // this = MacrobenchmarkScope
        pressHome()
        val intent = Intent()
        intent.setPackage("com.danosoftware.galaxyforce")
        intent.setAction("com.danosoftware.galaxyforce.MAIN_ACTIVITY")
        startActivityAndWait(intent)
    }

    @Test
    fun timing() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
        benchmarkRule.measureRepeated(
                packageName = "com.danosoftware.galaxyforce",
                metrics = listOf(FrameTimingMetric()),
                iterations = 5,
                startupMode = StartupMode.WARM
        ) {
            pressHome()
            val intent = Intent()
            intent.setPackage("com.danosoftware.galaxyforce")
            intent.setAction("com.danosoftware.galaxyforce.MAIN_ACTIVITY")
            startActivityAndWait(intent)
            device.click(50, 50)
            device.waitForIdle()
        }
    }
}