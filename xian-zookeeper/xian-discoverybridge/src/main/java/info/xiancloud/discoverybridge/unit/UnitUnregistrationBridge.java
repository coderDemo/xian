package info.xiancloud.discoverybridge.unit;

import info.xiancloud.core.Group;
import info.xiancloud.core.Handler;
import info.xiancloud.core.Input;
import info.xiancloud.core.Unit;
import info.xiancloud.core.distribution.NodeStatus;
import info.xiancloud.core.distribution.UnitProxy;
import info.xiancloud.core.distribution.service_discovery.UnitDiscovery;
import info.xiancloud.core.distribution.service_discovery.UnitInstance;
import info.xiancloud.core.message.UnitRequest;
import info.xiancloud.core.message.UnitResponse;
import info.xiancloud.core.util.LOG;
import info.xiancloud.discoverybridge.DiscoveryBridgeGroup;

/**
 * 注销unit
 *
 * @author happyyangyuan
 */
public class UnitUnregistrationBridge implements Unit {
    @Override
    public Input getInput() {
        return Input.create()
                .add("unit", Unit.class, "待注册的unit", REQUIRED)
                .add("nodeStatus", NodeStatus.class, "节点状态", REQUIRED);
    }

    @Override
    public Group getGroup() {
        return DiscoveryBridgeGroup.singleton;
    }

    @Override
    @SuppressWarnings("all")
    public void execute(UnitRequest msg, Handler<UnitResponse> handler) {
        UnitProxy unitProxy = msg.get("unit", UnitProxy.class);
        NodeStatus nodeStatus = msg.get("nodeStatus", NodeStatus.class);
        UnitInstance unitInstance = UnitRegistrationBridge.unitInstance(unitProxy, nodeStatus);
        try {
            UnitDiscovery.singleton.unregister(unitInstance);//Warning for blocking method.
            handler.handle(UnitResponse.createSuccess());
            return;
        } catch (Exception e) {
            LOG.error(e);
            handler.handle(UnitResponse.createUnknownError(e, "unit un registration failure."));
            return;
        }
    }
}
