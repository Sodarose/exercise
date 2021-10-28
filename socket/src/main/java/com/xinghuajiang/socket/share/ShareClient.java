package com.xinghuajiang.socket.share;

import com.xinghuajiang.socket.cross.TaskExecutor;
import com.xinghuajiang.socket.route.RouteTable;
import com.xinghuajiang.socket.rpc.RpcService;
import com.xinghuajiang.socket.rpc.RpcServiceFactory;

/**
 * @Auther: kangkang
 * @Date: 2021/10/27 11:35
 * @Description: 共享客户端
 */
public class ShareClient<SyncRpc, Data extends SyncRpc> {

    private ShareKey shareKey;

    private Data data;

    private Class<SyncRpc> syncRpcClass;

    private TaskExecutor updateThread;

    private Object extra;



    private RouteTable routeTable;
    private RpcService rpcService;
    private RpcServiceFactory rpcServiceFactory;

    public ShareKey getShareKey() {
        return shareKey;
    }

    public void setShareKey(ShareKey shareKey) {
        this.shareKey = shareKey;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Class<SyncRpc> getSyncRpcClass() {
        return syncRpcClass;
    }

    public void setSyncRpcClass(Class<SyncRpc> syncRpcClass) {
        this.syncRpcClass = syncRpcClass;
    }

    public TaskExecutor getUpdateThread() {
        return updateThread;
    }

    public void setUpdateThread(TaskExecutor updateThread) {
        this.updateThread = updateThread;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public RouteTable getRouteTable() {
        return routeTable;
    }

    public void setRouteTable(RouteTable routeTable) {
        this.routeTable = routeTable;
    }

    public RpcService getRpcService() {
        return rpcService;
    }

    public void setRpcService(RpcService rpcService) {
        this.rpcService = rpcService;
    }

    public RpcServiceFactory getRpcServiceFactory() {
        return rpcServiceFactory;
    }

    public void setRpcServiceFactory(RpcServiceFactory rpcServiceFactory) {
        this.rpcServiceFactory = rpcServiceFactory;
    }
}
