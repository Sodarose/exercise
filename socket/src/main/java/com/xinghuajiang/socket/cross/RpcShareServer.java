package com.xinghuajiang.socket.cross;

import com.xinghuajiang.socket.rpc.RpcResponseFuture;
import com.xinghuajiang.socket.share.ShareKey;

/**
 * Rpc共享服务,负责客户端与服务端连接以及维护共享服务端
 */
public interface RpcShareServer {

    RpcResponseFuture<Object> link(ShareKey shareKey, Object extra);

    void unlink(ShareKey shareKey) throws ClassNotFoundException;
}
