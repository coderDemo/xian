package info.xiancloud.yy;

import info.xiancloud.core.thread_pool.ThreadPoolManager;
import info.xiancloud.core.util.LOG;

/**
 * 超时线程池idle时自行销毁
 */
public class TestThreadPoolManagerIdleShutdown {

    public static void main(String[] args) {
        testThreadPoolManagerIdleShutdown();
    }

    public static void testThreadPoolManagerIdleShutdown() {
        ThreadPoolManager.execute(() -> LOG.info("123"));
    }
}
