package com.x.bp.common.utils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 15:19
 * @Description
 */
public class SnowFlakeIdGenerator {
    /** 定义起始时间 2015-01-01 00:00:00 */
    private static final long START_TIME = 1672502400000L;
    /** 上次生成ID的时间截 */
    private static long LAST_TIME_STAMP = -1L;
    /** 上一次的毫秒内序列值 */
    private static long LAST_SEQ = 0L;

    public synchronized static long nextId(long dataId,long wordId){

        long now = System.currentTimeMillis();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (now < LAST_TIME_STAMP) {
            throw new RuntimeException(String.format("系统时间错误！ %d 毫秒内拒绝生成雪花ID！", START_TIME - now));
        }

        if (now == LAST_TIME_STAMP) {
            LAST_SEQ = LAST_SEQ + 1;
            if (LAST_SEQ > 4095L){
                now = nextMillis(LAST_TIME_STAMP);
                LAST_SEQ = 0;
            }
        } else {
            LAST_SEQ = 0;
        }

        //上次生成ID的时间截
        LAST_TIME_STAMP = now;

        //时间部分
        long timePart = now - START_TIME;

        return (timePart << 22) | (dataId << 17) | (wordId << 12) | LAST_SEQ;
    }

    public synchronized static long nextId(){
        return nextId(getDataId(),getWorkId());
    }


    /**
     * 获取下一不同毫秒的时间戳，不能与最后的时间戳一样
     */
    public static long nextMillis(long lastMillis) {
        long now = System.currentTimeMillis();
        while (now <= lastMillis) {
            now = System.currentTimeMillis();
        }
        return now;
    }

    /**
     * 获取字符串s的字节数组，然后将数组的元素相加，对（max+1）取余
     */
    private static int getHostId(String s, int max){
        byte[] bytes = s.getBytes();
        int sums = 0;
        for(int b : bytes){
            sums += b;
        }
        return sums % (max+1);
    }

    /**
     * 根据 host address 取余，发生异常就获取 0到31之间的随机数
     */
    public static int getWorkId(){
        try {
            return getHostId(Inet4Address.getLocalHost().getHostAddress(), 31);
        } catch (UnknownHostException e) {
            return new Random().nextInt(32);
        }
    }

    /**
     * 根据 host name 取余，发生异常就获取 0到31之间的随机数
     */
    public static int getDataId() {
        try {
            return getHostId(Inet4Address.getLocalHost().getHostName(), 31);
        } catch (UnknownHostException e) {
            return new Random().nextInt(32);
        }
    }

}

