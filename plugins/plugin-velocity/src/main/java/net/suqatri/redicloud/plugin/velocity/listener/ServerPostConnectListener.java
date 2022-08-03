package net.suqatri.redicloud.plugin.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.suqatri.redicloud.api.CloudAPI;
import net.suqatri.redicloud.api.impl.player.CloudPlayer;
import net.suqatri.redicloud.api.player.ICloudPlayer;
import net.suqatri.redicloud.api.redis.bucket.IRBucketHolder;
import net.suqatri.redicloud.api.service.ICloudService;

import java.util.Optional;

public class ServerPostConnectListener {

    @Subscribe()
    public void onServerConnect(ServerPostConnectEvent event){
        Optional<ServerConnection > connection = event.getPlayer().getCurrentServer();
        if(!connection.isPresent()) return;
        RegisteredServer registeredServer = connection.get().getServer();
        CloudAPI.getInstance().getConsole().trace("Player " + event.getPlayer().getUsername() + " switched to server " + registeredServer.getServerInfo().getName());
        IRBucketHolder<ICloudPlayer> playerHolder= CloudAPI.getInstance().getPlayerManager().getPlayer(event.getPlayer().getUniqueId());
        if(playerHolder == null) return;
        IRBucketHolder<ICloudService> serviceHolder = CloudAPI.getInstance().getServiceManager()
                .getService(registeredServer.getServerInfo().getName());
        playerHolder.getImpl(CloudPlayer.class).setLastConnectedServerId(serviceHolder.get().getUniqueId());
        playerHolder.get().updateAsync();
    }

}
