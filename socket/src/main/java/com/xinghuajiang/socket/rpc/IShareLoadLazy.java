package com.xinghuajiang.socket.rpc;

import com.xinghuajiang.socket.cross.TaskExecutor;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @Auther: kangkang
 * @Date: 2021/10/27 15:16
 * @Description:
 */
public interface IShareLoadLazy<Data> {

    public void load(long uid, Object extra, BiConsumer<TaskExecutor,Data> loadOver, Consumer<Throwable> exceptionConsumer);
}
