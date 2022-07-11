package net.suqatri.cloud.api.impl;

import lombok.Getter;
import net.suqatri.cloud.api.CloudAPI;
import net.suqatri.cloud.api.event.ICloudEventManager;
import net.suqatri.cloud.api.group.ICloudGroupManager;
import net.suqatri.cloud.api.impl.event.CloudEventManager;
import net.suqatri.cloud.api.impl.group.CloudGroup;
import net.suqatri.cloud.api.impl.group.CloudGroupManager;
import net.suqatri.cloud.api.impl.network.NetworkComponentInfo;
import net.suqatri.cloud.api.impl.network.NetworkComponentManager;
import net.suqatri.cloud.api.impl.node.CloudNode;
import net.suqatri.cloud.api.impl.node.CloudNodeManager;
import net.suqatri.cloud.api.impl.packet.CloudPacketManager;
import net.suqatri.cloud.api.network.INetworkComponentInfo;
import net.suqatri.cloud.api.network.INetworkComponentManager;
import net.suqatri.cloud.api.node.ICloudNodeManager;
import net.suqatri.cloud.api.packet.ICloudPacketManager;
import net.suqatri.cloud.api.redis.IRedisConnection;
import net.suqatri.cloud.api.utils.ApplicationType;

@Getter
public abstract class CloudDefaultAPIImpl extends CloudAPI {

    @Getter
    private static CloudDefaultAPIImpl instance;

    private final ICloudEventManager eventManager;
    private final ICloudPacketManager packetManager;
    private final INetworkComponentManager<NetworkComponentInfo> networkComponentManager;
    private final ICloudNodeManager<CloudNode> nodeManager;
    private final ICloudGroupManager<CloudGroup> groupManager;

    public CloudDefaultAPIImpl(ApplicationType type) {
        super(type);
        instance = this;
        this.eventManager = new CloudEventManager();
        this.packetManager = new CloudPacketManager();
        this.networkComponentManager = new NetworkComponentManager();
        this.nodeManager = new CloudNodeManager();
        this.groupManager = new CloudGroupManager();
    }

    @Override
    public void initShutdownHook(){
        if(this.isShutdownHookAdded()) return;
        Runtime.getRuntime().addShutdownHook(new Thread("node-shutdown-hook"){
            @Override
            public void run(){
                CloudAPI.getInstance().shutdown(true);
            }
        });
    }

    public abstract IRedisConnection getRedisConnection();

}
