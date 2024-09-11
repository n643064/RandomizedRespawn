package n643064.randomized_respawn;

import net.fabricmc.api.ModInitializer;

public class RandomizedRespawn implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        Config.setup();
    }
}
