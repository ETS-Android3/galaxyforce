package com.danosoftware.macrobenchmark.test

import android.content.Intent
import android.util.Log
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val PACKAGE = "com.danosoftware.galaxyforce"
private const val TAG = "BenchmarkTest"
private const val DEFAULT_TIMEOUT_MS = 5000L

@RunWith(AndroidJUnit4::class)
class SampleStartupBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
            packageName = PACKAGE,
            metrics = listOf(StartupTimingMetric()),
            iterations = 5,
            startupMode = StartupMode.COLD
    ) { // this = MacrobenchmarkScope
        pressHome()
        val intent = Intent()
        intent.setPackage(PACKAGE)
        intent.setAction("$PACKAGE.BENCHMARK")
        startActivityAndWait(intent)
    }

    @Test
    fun timing() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
        benchmarkRule.measureRepeated(
                packageName = PACKAGE,
                metrics = listOf(FrameTimingMetric()),
                iterations = 5,
                startupMode = StartupMode.WARM
        ) {
            pressHome()
            val intent = Intent()
            intent.setPackage(PACKAGE)
            intent.setAction("$PACKAGE.BENCHMARK")
            startActivityAndWait(intent)
            Log.i(TAG, "prepare to click")
            device.click(50, 50)
            Log.i(TAG, "click")
            device.waitForIdle()
            Log.i(TAG, "idle")
            device.wait(Until
                    .findObject(By.text("foo")), DEFAULT_TIMEOUT_MS)
            device.click(device.displayWidth / 2, device.displayHeight / 2)
            device.wait(Until
                    .findObject(By.text("foo")), DEFAULT_TIMEOUT_MS)
            device.swipe(0, device.displayHeight / 2, device.displayWidth, device.displayHeight / 2, 500)
            device.wait(Until
                    .findObject(By.text("foo")), DEFAULT_TIMEOUT_MS)
        }
    }
}