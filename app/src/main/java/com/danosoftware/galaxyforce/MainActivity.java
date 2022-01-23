package com.danosoftware.galaxyforce;

import static com.danosoftware.galaxyforce.constants.GameConstants.BACKGROUND_ALPHA;
import static com.danosoftware.galaxyforce.constants.GameConstants.BACKGROUND_BLUE;
import static com.danosoftware.galaxyforce.constants.GameConstants.BACKGROUND_GREEN;
import static com.danosoftware.galaxyforce.constants.GameConstants.BACKGROUND_RED;
import static com.danosoftware.galaxyforce.constants.GameConstants.RC_SIGN_IN;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.danosoftware.galaxyforce.billing.BillingManager;
import com.danosoftware.galaxyforce.billing.BillingManagerImpl;
import com.danosoftware.galaxyforce.billing.BillingService;
import com.danosoftware.galaxyforce.billing.BillingServiceImpl;
import com.danosoftware.galaxyforce.billing.BillingUpdatesListener;
import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.games.Game;
import com.danosoftware.galaxyforce.games.GameImpl;
import com.danosoftware.galaxyforce.games.GameLoopTest;
import com.danosoftware.galaxyforce.services.configurations.ConfigurationService;
import com.danosoftware.galaxyforce.services.configurations.ConfigurationServiceImpl;
import com.danosoftware.galaxyforce.services.googleplay.GooglePlayServices;
import com.danosoftware.galaxyforce.services.preferences.IPreferences;
import com.danosoftware.galaxyforce.services.preferences.PreferencesString;
import com.danosoftware.galaxyforce.view.GLGraphics;
import com.danosoftware.galaxyforce.view.GLShaderHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends Activity {

    private enum ActivityState {
        INITIALISED, RUNNING, PAUSED, FINISHED, IDLE
    }

    /* logger tag */
    private static final String ACTIVITY_TAG = "MainActivity";

    /* reference to game instance */
    private Game game;

    /* used for state synchronisation */
    private final Object stateChanged = new Object();

    /* application state */
    private ActivityState state = ActivityState.INITIALISED;

    /* GL Graphics reference */
    private GLGraphics glGraphics;

    /* GL Surface View reference */
    private GLSurfaceView glView;

    /* Billing Manager for In-App Billing Requests */
    private BillingManager mBillingManager;

    /* Google Play Games Services */
    private GooglePlayServices mPlayServices;

    /* runs when application initially starts */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(GameConstants.LOG_TAG, ACTIVITY_TAG + ": Create Application");
        super.onCreate(savedInstanceState);

        setupScreen();

        // set-up GL view
        glView = new GameGLSurfaceView(this);
        setContentView(glView);
        this.glGraphics = new GLGraphics(glView);

        // Create and initialize billing
        BillingService billingService = new BillingServiceImpl();
        BillingUpdatesListener billingListener = (BillingUpdatesListener) billingService;
        this.mBillingManager = new BillingManagerImpl(this, billingListener);

        // set-up configuration service that uses shared preferences
        // for persisting configuration
        IPreferences<String> configPreferences = new PreferencesString(this);
        ConfigurationService configurationService = new ConfigurationServiceImpl(configPreferences);

        // initialise play games services
        this.mPlayServices = new GooglePlayServices(this, configurationService);

        // create instance of game
        Intent launchIntent = getIntent();
        String launchIntentAction = launchIntent.getAction();
        if (launchIntentAction != null &&
            launchIntentAction.equals("com.google.intent.action.TEST_LOOP")) {
            game = new GameLoopTest(this, glGraphics, glView, billingService, mPlayServices,
                configurationService);
        } else {
            game = new GameImpl(this, glGraphics, glView, billingService, mPlayServices,
                configurationService);
        }

        Log.i(GameConstants.LOG_TAG, ACTIVITY_TAG + ": Application Created");
    }

    /* runs after onCreate or resuming after being in background */
    @Override
    protected void onResume() {

        Log.i(GameConstants.LOG_TAG, ACTIVITY_TAG + ": Resume Application");
        super.onResume();
        glView.onResume();

        // Note: We query purchases in onResume() to handle purchases completed while the activity
        // is inactive. For example, this can happen if the activity is destroyed during the
        // purchase flow. This ensures that when the activity is resumed it reflects the user's
        // current purchases.
        if (mBillingManager != null && mBillingManager.isConnected()) {
            mBillingManager.queryPurchases();
        }

        // sign-in to google play services
        mPlayServices.signInSilently();
    }

    /* runs when application is paused */
    @Override
    protected void onPause() {
        Log.i(GameConstants.LOG_TAG, ACTIVITY_TAG + ": Pause Application");

        synchronized (stateChanged) {
            if (isFinishing()) {
                Log.i(GameConstants.LOG_TAG, ACTIVITY_TAG + ": Finish Application");
                state = ActivityState.FINISHED;
            } else {
                state = ActivityState.PAUSED;
            }
            while (true) {
                try {
                    stateChanged.wait();
                    break;
                } catch (InterruptedException e) {
                    Log.w(GameConstants.LOG_TAG, ACTIVITY_TAG + ": Unexpected InterruptedException", e);
                }
            }

        }
        glView.onPause();
        super.onPause();
        Log.i(GameConstants.LOG_TAG, ACTIVITY_TAG + ": Application Paused");
    }

    @Override
    protected void onDestroy() {
        Log.i(GameConstants.LOG_TAG, ACTIVITY_TAG + ": Destroying Application");
        super.onDestroy();

        Log.i(ACTIVITY_TAG, "Destroying Billing Manager.");
        if (mBillingManager != null) {
            mBillingManager.destroy();
        }
        Log.i(GameConstants.LOG_TAG, ACTIVITY_TAG + ": Application Destroyed");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.i(GameConstants.LOG_TAG, ACTIVITY_TAG + ": Back Button Pressed");

            // if back button is handled internally then don't use normal
            // superclass's method
            if (game.handleBackButton()) {
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void setupScreen() {
        // set application to use full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // stop window dimming when window is visible (recommended over
        // deprecated full wake lock)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    // setSystemUiVisibility() is deprecated but WindowInsetsController replacement requires API 30.
    // not replaced to simply support older APIs.
    private void hideSystemUI() {
        // See https://developer.android.com/training/system-ui/immersive
        // Using "sticky immersive"
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // invoked with result on attempts to sign-in to Google Play Services.
    // we will pass on result to our service to handle correctly.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            mPlayServices.handleSignInResult(task);
        }
    }

    /**
     * Inner class to handle graphics
     */
    private class GLRenderer implements Renderer {

        private static final String LOCAL_TAG = "GLRenderer";
        long startTime;

        @Override
        public void onDrawFrame(GL10 unused) {
            ActivityState stateCheck;

            synchronized (stateChanged) {
                stateCheck = state;
            }

            if (stateCheck == ActivityState.RUNNING) {
                float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
                startTime = System.nanoTime();

                Trace.beginSection("onDrawFrame.update");
                game.update(deltaTime);
                Trace.endSection();
                Trace.beginSection("onDrawFrame.draw");
                game.draw();
                Trace.endSection();
            }

            if (stateCheck == ActivityState.PAUSED) {
                Log.i(GameConstants.LOG_TAG, "Render Pause");
                game.pause();

                synchronized (stateChanged) {
                    state = ActivityState.IDLE;
                    stateChanged.notifyAll();
                }
            }

            if (stateCheck == ActivityState.FINISHED) {
                Log.i(GameConstants.LOG_TAG, "Render Finish");
                game.pause();
                game.dispose();

                synchronized (stateChanged) {
                    state = ActivityState.IDLE;
                    stateChanged.notifyAll();
                }
                // delete our GL shaders program
                GLShaderHelper.deleteProgram();
            }
        }

        @Override
        public void onSurfaceChanged(GL10 unused, int width, int height) {
            Log.i(GameConstants.LOG_TAG,
                LOCAL_TAG + ": onSurfaceChanged. width: " + width + ". height: " + height + ".");
        }

        @Override
        public void onSurfaceCreated(GL10 unused, EGLConfig config) {
            Log.i(GameConstants.LOG_TAG, LOCAL_TAG + ": onSurfaceCreated");

            // create and initialise our GL shaders program
            GLShaderHelper.createProgram();

            // set game background colour.
            // i.e. colour used when screen is cleared before each frame
            GLES20.glClearColor(
                BACKGROUND_RED,
                BACKGROUND_GREEN,
                BACKGROUND_BLUE,
                BACKGROUND_ALPHA);

            // Disable depth testing -- we're 2D only.
            GLES20.glDisable(GLES20.GL_DEPTH_TEST);

            // Don't need backface culling.
            GLES20.glDisable(GLES20.GL_CULL_FACE);

            synchronized (stateChanged) {
                if (state == ActivityState.INITIALISED) {
                    game.start();
                }
                state = ActivityState.RUNNING;
                game.resume();
                startTime = System.nanoTime();
            }
        }
    }

    /**
     * Inner class for GL Surface View
     */
    private class GameGLSurfaceView extends GLSurfaceView {

        private final GLRenderer renderer;

        public GameGLSurfaceView(Context context) {
            super(context);

            // Create an OpenGL ES 2.0 context
            setEGLContextClientVersion(2);

            // Set the Renderer for drawing on the GLSurfaceView
            renderer = new GLRenderer();
            setRenderer(renderer);
        }
    }
}
