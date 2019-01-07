package com.danosoftware.galaxyforce.sprites.game.behaviours.explode;

import com.danosoftware.galaxyforce.services.sound.SoundEffect;
import com.danosoftware.galaxyforce.services.sound.SoundPlayerService;
import com.danosoftware.galaxyforce.services.vibration.VibrateTime;
import com.danosoftware.galaxyforce.services.vibration.VibrationService;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;
import com.danosoftware.galaxyforce.view.Animation;

public class ExplodeSimple implements ExplodeBehaviour {

    // explosion animation
    // NOTE: not static. each instance needs it's own animation
    private final Animation animation = new Animation(0.15f, GameSpriteIdentifier.EXPLODE_01,
            GameSpriteIdentifier.EXPLODE_02, GameSpriteIdentifier.EXPLODE_03);

    // reference to sound player
    private final SoundPlayerService sounds;

    // reference to vibrator
    private final VibrationService vibrator;

    // time since explosion started
    private float explosionTime;

    public ExplodeSimple(
            SoundPlayerService sounds,
            VibrationService vibrator) {
        this.sounds = sounds;
        this.vibrator = vibrator;
    }

    @Override
    public void startExplosion() {
        explosionTime = 0f;

        sounds.play(SoundEffect.EXPLOSION);
        vibrator.vibrate(VibrateTime.TINY);
    }

    @Override
    public ISpriteIdentifier getExplosion(float deltaTime) {
        explosionTime += deltaTime;
        return animation.getKeyFrame(explosionTime, Animation.ANIMATION_NONLOOPING);
    }

    @Override
    public boolean finishedExploding() {
        return animation.isAnimationComplete();
    }
}
