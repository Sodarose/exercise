package com.xinghuajiang.socket.cross;

public interface TaskExecutor {
    boolean isExecutor();

    void execute(String s, Runnable runnable);
}
