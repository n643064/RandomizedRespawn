package n643064.randomized_respawn.mixin;

import n643064.randomized_respawn.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRespawnLogic.class)
public abstract class PlayerRespawnLogicMixin
{

    @Inject(method = "getSpawnPosInChunk", at = @At("HEAD"), cancellable = true)
    private static void getSpawnPosInChunk(ServerLevel serverLevel, ChunkPos chunkPos, CallbackInfoReturnable<BlockPos> cir)
   {
       int x = serverLevel.random.nextInt(-Config.INSTANCE.xRange(), Config.INSTANCE.xRange());
       int y = serverLevel.random.nextInt(-Config.INSTANCE.zRange(), Config.INSTANCE.zRange());
       System.out.println(x + " " + y);
       cir.setReturnValue(PlayerRespawnLogic.getOverworldRespawnPos(serverLevel, x, y));
   }
}
