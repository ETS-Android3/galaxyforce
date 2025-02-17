package com.danosoftware.galaxyforce.waves.config;

import static com.danosoftware.galaxyforce.constants.GameConstants.GAME_HEIGHT;
import static com.danosoftware.galaxyforce.constants.GameConstants.GAME_WIDTH;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.danosoftware.galaxyforce.enumerations.AlienMissileCharacter;
import com.danosoftware.galaxyforce.enumerations.AlienMissileSpeed;
import com.danosoftware.galaxyforce.enumerations.AlienMissileType;
import com.danosoftware.galaxyforce.enumerations.AlienSpeed;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.models.screens.game.GameModel;
import com.danosoftware.galaxyforce.services.sound.SoundPlayerService;
import com.danosoftware.galaxyforce.services.vibration.VibrationService;
import com.danosoftware.galaxyforce.sprites.game.aliens.IAlien;
import com.danosoftware.galaxyforce.sprites.game.aliens.implementations.FollowableHunterAlien;
import com.danosoftware.galaxyforce.sprites.game.aliens.implementations.FollowerAlien;
import com.danosoftware.galaxyforce.sprites.game.factories.AlienFactory;
import com.danosoftware.galaxyforce.waves.AlienCharacter;
import com.danosoftware.galaxyforce.waves.AlienType;
import com.danosoftware.galaxyforce.waves.config.aliens.AlienConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.missiles.MissileConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.missiles.MissileFiringConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spawning.SpawnConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spawning.SpawningAlienConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spinning.SpinningConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spinning.SpinningFixedAngularConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.BoundariesConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.FollowableHunterConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.FollowerConfig;
import com.danosoftware.galaxyforce.waves.utilities.PowerUpAllocator;
import com.danosoftware.galaxyforce.waves.utilities.PowerUpAllocatorFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

public class FollowableHunterConfigTest {

    private final PowerUpAllocatorFactory powerUpAllocatorFactory = mock(PowerUpAllocatorFactory.class);
    private final AlienFactory factory = new AlienFactory(
            mock(GameModel.class),
            powerUpAllocatorFactory,
            mock(SoundPlayerService.class),
            mock(VibrationService.class)
    );
    private final PowerUpAllocator powerUpAllocator = mock(PowerUpAllocator.class);

    @Before
    public void setUp() {
        when(powerUpAllocatorFactory.createAllocator(
            ArgumentMatchers.anyList(),
            anyInt()))
                .thenReturn(powerUpAllocator);
    }


    @Test
    public void shouldCreateBasicConfiguredFollowableHunter() {
        FollowableHunterConfig config = FollowableHunterConfig
                .builder()
                .alienCharacter(AlienCharacter.OCTOPUS)
                .energy(10)
                .speed(AlienSpeed.SLOW)
                .followerPowerUps(Collections.singletonList(PowerUpType.LIFE))
                .numberOfFollowers(1)
                .followerConfig(FollowerConfig
                        .builder()
                        .alienCharacter(AlienCharacter.OCTOPUS)
                        .energy(10)
                        .speed(AlienSpeed.SLOW)
                        .build())
                .boundaries(BoundariesConfig.builder().build())
                .build();

        assertThat(config.getAlienCharacter(), equalTo(AlienCharacter.OCTOPUS));
        assertThat(config.getEnergy(), equalTo(10));
        assertThat(config.getAlienType(), equalTo(AlienType.HUNTER_FOLLOWABLE));
        assertThat(config.getSpeed(), equalTo(AlienSpeed.SLOW));
        assertThat(config.getFollowerConfig(), not(nullValue()));
        assertThat(config.getFollowerPowerUps(), not(nullValue()));
        assertThat(config.getNumberOfFollowers(), equalTo(1));
        assertThat(config.getSpawnConfig(), nullValue());
        assertThat(config.getMissileConfig(), nullValue());
        assertThat(config.getBoundaries().getMinX(), equalTo(0));
        assertThat(config.getBoundaries().getMaxX(), equalTo(GAME_WIDTH));
        assertThat(config.getBoundaries().getMinY(), equalTo(0));
        assertThat(config.getBoundaries().getMaxY(), equalTo(GAME_HEIGHT));

        List<IAlien> aliens = factory.createAlien(
                config,
                PowerUpType.LIFE,
                false,
                false,
                0,
                0,
                0f,
                false);

        assertThat(aliens, not(nullValue()));
        assertThat(aliens.size(), equalTo(2));
        assertThat(aliens.get(0) instanceof FollowableHunterAlien, is(true));
        assertThat(aliens.get(1) instanceof FollowerAlien, is(true));
    }

    @Test
    public void shouldCreateFullyConfiguredFollowableHunter() {
        FollowableHunterConfig config = FollowableHunterConfig
                .builder()
                .alienCharacter(AlienCharacter.OCTOPUS)
                .energy(10)
                .speed(AlienSpeed.SLOW)
                .followerPowerUps(Collections.singletonList(PowerUpType.LIFE))
                .numberOfFollowers(1)
                .boundaries(
                        BoundariesConfig
                                .builder()
                                .minX(100)
                                .maxX(400)
                                .minY(50)
                                .maxY(250)
                                .build())
                .followerConfig(FollowerConfig
                        .builder()
                        .alienCharacter(AlienCharacter.OCTOPUS)
                        .energy(10)
                        .speed(AlienSpeed.SLOW)
                        .build())
                .spawnConfig(new SpawningAlienConfig(
                    mock(AlienConfig.class),
                    new ArrayList<>(),
                    0f,
                    0f))
                .missileConfig(new MissileFiringConfig(
                        AlienMissileType.DOWNWARDS,
                        AlienMissileSpeed.MEDIUM,
                        AlienMissileCharacter.LASER,
                        0f))
                .spinningConfig(new SpinningFixedAngularConfig(
                        10))
                .build();

        assertThat(config.getAlienCharacter(), equalTo(AlienCharacter.OCTOPUS));
        assertThat(config.getEnergy(), equalTo(10));
        assertThat(config.getAlienType(), equalTo(AlienType.HUNTER_FOLLOWABLE));
        assertThat(config.getSpeed(), equalTo(AlienSpeed.SLOW));
        assertThat(config.getFollowerConfig(), not(nullValue()));
        assertThat(config.getFollowerPowerUps(), not(nullValue()));
        assertThat(config.getNumberOfFollowers(), equalTo(1));
        assertThat(config.getSpawnConfig().getType(), equalTo(SpawnConfig.SpawnType.SPAWN));
        assertThat(config.getSpawnConfig() instanceof SpawningAlienConfig, is(true));
        assertThat(config.getMissileConfig().getType(), equalTo(MissileConfig.MissileConfigType.MISSILE));
        assertThat(config.getMissileConfig() instanceof MissileFiringConfig, is(true));
        assertThat(config.getSpinningConfig().getType(), equalTo(SpinningConfig.SpinningConfigType.FIXED_ANGULAR_ROTATION));
        assertThat(config.getSpinningConfig() instanceof SpinningFixedAngularConfig, is(true));
        assertThat(config.getBoundaries().getMinX(), equalTo(100));
        assertThat(config.getBoundaries().getMaxX(), equalTo(400));
        assertThat(config.getBoundaries().getMinY(), equalTo(50));
        assertThat(config.getBoundaries().getMaxY(), equalTo(250));

        List<IAlien> aliens = factory.createAlien(
                config,
                PowerUpType.LIFE,
                false,
                false,
                0,
                0,
                0f,
                false);

        assertThat(aliens, not(nullValue()));
        assertThat(aliens.size(), equalTo(2));
        assertThat(aliens.get(0) instanceof FollowableHunterAlien, is(true));
        assertThat(aliens.get(1) instanceof FollowerAlien, is(true));
    }
}
