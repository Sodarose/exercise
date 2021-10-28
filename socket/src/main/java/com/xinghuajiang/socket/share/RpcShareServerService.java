package com.xinghuajiang.socket.share;

import com.xinghuajiang.socket.cross.RpcShareServer;
import com.xinghuajiang.socket.route.RouteTable;
import com.xinghuajiang.socket.rpc.IShareLoadLazy;
import com.xinghuajiang.socket.rpc.RPCFutures;
import com.xinghuajiang.socket.rpc.RpcResponseFuture;
import com.xinghuajiang.socket.rpc.RpcService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: kangkang
 * @Date: 2021/10/27 11:45
 * @Description:
 */
public class RpcShareServerService implements RpcShareServer {

    private RpcService rpcService;

    private RouteTable routeTable;

    @Override
    public RpcResponseFuture<Object> link(ShareKey shareKey, Object extra) {
        Class<?> syncRpcClass = null;
        try {
            syncRpcClass = Class.forName(shareKey.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ShareData<?, ?> shareData = getOrCreateShare(syncRpcClass);
        return shareData.link(shareKey, extra);
    }

    class ShareData<SyncRpc, Data extends SyncRpc> {

        private Class<SyncRpc> syncRpcClass = null;

        private Map<Long, ShareServer<SyncRpc, Data>> dataMap = new ConcurrentHashMap<>(1);

        private IShareLoadLazy<Data> shareLoadLazy;

        public RpcResponseFuture<Object> link(ShareKey shareKey, Object extra) {
            RpcResponseFuture<Object> future = RPCFutures.newSettableFuture();
            long dataUid = shareKey.getDataUid();
            ShareServer<SyncRpc, Data> shareServer = dataMap.get(dataUid);
            if (shareServer != null) {
                shareServer.addSyncClient(shareKey, future);
                return future;
            } else if (shareLoadLazy != null) {
                shareLoadLazy.load(dataUid, extra, ((taskExecutor, data) -> {
                    ShareServer<SyncRpc, Data> dataShareServer = dataMap.computeIfAbsent(dataUid, aLong -> {
                        ShareServer<SyncRpc, Data> share = ShareServer.valueOf(rpcService, routeTable,
                                dataUid, syncRpcClass, taskExecutor);
                        share.initData(data);
                        return share;
                    });
                    dataShareServer.addSyncClient(shareKey, future);
                }), future::setException);
                return future;
            } else {
                // 没有数据的处理 省略.......
            }
            return future;
        }
    }


    @Override
    public void unlink(ShareKey shareKey) throws ClassNotFoundException {

    }

    private ShareData<?, ?> getOrCreateShare(Class<?> syncRpcClass) {
        return new ShareData<>();
    }

}
