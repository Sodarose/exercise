package com.xinghuajiang.socket.share;

/**
 * @Auther: kangkang
 * @Date: 2021/10/27 11:45
 * @Description: 共享key
 */
public class ShareKey {

    /**
     * RPC 共享类名
     */
    private String className;

    public String getClassName() {
        return className;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public long getDataUid() {
        return 0;
    }

    public int getSlaveRouteId() {
        return 0;
    }
}
