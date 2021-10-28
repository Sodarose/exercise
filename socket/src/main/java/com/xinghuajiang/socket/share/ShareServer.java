package com.xinghuajiang.socket.share;

import ch.qos.logback.core.net.server.Client;
import com.sun.corba.se.impl.orbutil.concurrent.Sync;
import com.xinghuajiang.socket.cross.TaskExecutor;
import com.xinghuajiang.socket.observer.ObserverSupport;
import com.xinghuajiang.socket.route.RouteListener;
import com.xinghuajiang.socket.route.RouteTable;
import com.xinghuajiang.socket.rpc.RpcResponseFuture;
import com.xinghuajiang.socket.rpc.RpcService;

import java.util.Map;

/**
 * @Auther: kangkang
 * @Date: 2021/10/27 11:35
 * @Description: 共享服务端
 */
public class ShareServer<SyncRpc, Data extends SyncRpc> {

    private long uid;

    private Data data;

    private Class<SyncRpc> syncRpcClass;

    private ObserverSupport<SyncRpc> syncClient;

    private Map<ShareKey,Client> routeSyncClientMap;

    private TaskExecutor taskExecutor;

    private RpcService rpcService;
    private RouteTable routeTable;


    public static <Data extends SyncRpc, SyncRpc> ShareServer<SyncRpc, Data> valueOf(RpcService rpcService, RouteTable routeTable, long dataUid, Class<?> syncRpcClass, TaskExecutor taskExecutor) {
        return null;
    }

    public void addSyncClient(ShareKey shareKey, RpcResponseFuture<Object> future) {
        if(taskExecutor.isExecutor()){
            addSyncClient0(shareKey,future);
        }else{
            taskExecutor.execute("addSyncClient",()->addSyncClient0(shareKey,future));
        }
    }

    private void addSyncClient0(ShareKey shareKey, RpcResponseFuture<Object> future) {
        SyncRpc syncRpc = rpcService.create(syncRpcClass,shareKey.getSlaveRouteId(),shareKey.getDataUid());

        Client client = new Client(shareKey,syncRpc);
        routeTable.addRouteListener(shareKey.getSlaveRouteId(),client);
        syncClient.attachForever(syncRpc);

        routeSyncClientMap.put(shareKey,client);
        if(future!=null){
            future.set(data);
        }
    }

    public void initData(Data data) {

    }

    class Client implements RouteListener {

        public Client(ShareKey shareKey, SyncRpc syncRpc) {

        }
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
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

    public ObserverSupport<SyncRpc> getSyncClient() {
        return syncClient;
    }

    public void setSyncClient(ObserverSupport<SyncRpc> syncClient) {
        this.syncClient = syncClient;
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public Map<ShareKey, Client> getRouteSyncClientMap() {
        return routeSyncClientMap;
    }

    public void setRouteSyncClientMap(Map<ShareKey, Client> routeSyncClientMap) {
        this.routeSyncClientMap = routeSyncClientMap;
    }

    public RpcService getRpcService() {
        return rpcService;
    }

    public void setRpcService(RpcService rpcService) {
        this.rpcService = rpcService;
    }

    public RouteTable getRouteTable() {
        return routeTable;
    }

    public void setRouteTable(RouteTable routeTable) {
        this.routeTable = routeTable;
    }
}
