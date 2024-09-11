package n643064.randomized_respawn.mixin;

import n643064.randomized_respawn.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(PlayerList.class)
public class PlayerListMixin
{
    @Inject(method = "respawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getLevelData()Lnet/minecraft/world/level/storage/LevelData;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void respawn(ServerPlayer serverPlayer, boolean bl, CallbackInfoReturnable<ServerPlayer> cir, BlockPos blockPos, float f, boolean bl2, ServerLevel serverLevel, Optional optional, ServerLevel serverLevel2, ServerPlayer serverPlayer2, boolean bl3, byte b)
    {
        if (optional.isPresent()) return;
        final BlockPos bp = roll(serverLevel2, false, 0, 0);
        serverPlayer2.moveTo(bp.getX(), bp.getY(), bp.getZ(), 0, 0f);
        serverPlayer2.setRespawnPosition(serverLevel2.dimension(), bp, 0, false, false);
    }

    @Unique @NotNull
    private BlockPos roll(ServerLevel level, boolean correct, int x, int y)
    {
        if (correct)
        {
            x += level.random.nextInt(-Config.INSTANCE.correctionOffset(), Config.INSTANCE.correctionOffset());
            y += level.random.nextInt(-Config.INSTANCE.correctionOffset(), Config.INSTANCE.correctionOffset());
        } else
        {
            x = level.random.nextInt(-Config.INSTANCE.xRange(), Config.INSTANCE.xRange());
            y = level.random.nextInt(-Config.INSTANCE.zRange(), Config.INSTANCE.zRange());
        }
        final BlockPos bp = PlayerRespawnLogic.getOverworldRespawnPos(level, x, y);
        //System.out.println(x + " " + y);
        if (bp == null) return roll(level, true, x, y);
        return bp;
    }
}
