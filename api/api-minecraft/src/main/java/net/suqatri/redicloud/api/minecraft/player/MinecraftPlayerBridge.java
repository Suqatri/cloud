package net.suqatri.redicloud.api.minecraft.player;

import net.suqatri.redicloud.api.CloudAPI;
import net.suqatri.redicloud.api.impl.player.RequestPlayerBridge;
import net.suqatri.redicloud.api.player.ICloudPlayer;
import net.suqatri.redicloud.api.redis.bucket.IRBucketHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MinecraftPlayerBridge extends RequestPlayerBridge {

    public MinecraftPlayerBridge(IRBucketHolder<ICloudPlayer> playerHolder) {
        super(playerHolder);
    }

    @Override
    public void sendTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        Player player = Bukkit.getPlayer(this.getPlayerHolder().get().getUniqueId());
        if(player == null || !player.isOnline()) {
            if(this.getPlayerHolder().get().getLastConnectedServerId()
                    .equals(UUID.fromString(CloudAPI.getInstance().getNetworkComponentInfo().getIdentifier()))
                    && this.getPlayerHolder().get().isConnected()){
                super.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
            }
            return;
        }
        //TODO: send title to player
    }

    @Override
    public void sendActionbar(String message) {
        Player player = Bukkit.getPlayer(this.getPlayerHolder().get().getUniqueId());
        if(player == null || !player.isOnline()) {
            if(this.getPlayerHolder().get().getLastConnectedServerId()
                    .equals(UUID.fromString(CloudAPI.getInstance().getNetworkComponentInfo().getIdentifier()))
                    && this.getPlayerHolder().get().isConnected()){
                super.sendActionbar(message);
            }
            return;
        }
        //TODO: send actionbar to player
    }

}
