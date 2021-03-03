package com.loper.mine.utils.time;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

/**
 * Created on 2019-07-30 10:52.
 *
 * @author zgq7
 */
public class TimeUtils {

    /**
     * 系统默认zoneId
     **/
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    /**
     * 开始时间枚举
     **/
    public enum START {
        MONTH((byte) 1),
        DAY((byte) 1),
        HOUR((byte) 0),
        MINUTE((byte) 0),
        SECOND((byte) 0),
        MILLS((byte) 0);

        Byte v;

        START(byte v) {
            this.v = v;
        }

        public Byte getValue() {
            return v;
        }
    }

    /**
     * 结束时间枚举
     **/
    public enum END {
        MONTH((byte) 12),
        //DAY((byte) 23),
        HOUR((byte) 23),
        MINUTE((byte) 59),
        SECOND((byte) 59);

        Byte v;

        END(byte v) {
            this.v = v;
        }

        public Byte getValue() {
            return v;
        }
    }

    /**
     * 获取当前时间戳
     **/
    public static long getTimestampNow() {
        return LocalDateTime.now().atZone(ZONE_ID).toInstant().toEpochMilli();
    }

    /**
     * @param year 查询年份
     * @return 含有 #{year} 年的第一天,精确到毫秒
     **/
    public static LocalDateTime getFristDateTime(int year) {
        return LocalDateTime.of(LocalDate.of(year, START.MONTH.getValue(), START.DAY.getValue()).with(TemporalAdjusters.firstDayOfYear())
                , LocalTime.of(START.HOUR.getValue(), START.MINUTE.getValue(), START.SECOND.getValue(), START.MILLS.getValue()));
    }

    /**
     * @param year 查询年份
     * @return 含有 #{year} 年的最后一天,精确到毫秒
     **/
    public static LocalDateTime getLastDateTime(int year) {
        return LocalDateTime.of(LocalDate.of(year, END.MONTH.getValue(), 1).with(TemporalAdjusters.lastDayOfYear())
                , LocalTime.of(END.HOUR.getValue(), END.MINUTE.getValue(), END.SECOND.getValue(), 999999999));
    }

    /**
     * @param year 查询年份
     * @return 含有 #{year} 年的第一天,精确到小时
     **/
    public static LocalDate getFristDate(int year) {
        return LocalDate.of(year, START.MONTH.getValue(), START.DAY.getValue()).with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * @param year 查询年份
     * @return 含有 #{year} 年的最后一天,精确到小时
     **/
    public static LocalDate getLastDate(int year) {
        return LocalDate.of(year, END.MONTH.getValue(), 1).with(TemporalAdjusters.lastDayOfYear());
    }

    /**
     * 获取到本周的第一天
     **/
    public static LocalDate getFristDateOfWeek() {
        return LocalDate.now().with(TemporalAdjusters.ofDateAdjuster(adjuster ->
                adjuster.minusDays(adjuster.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()))
        );
    }

    /**
     * 获取到本周的最后一天
     **/
    public static LocalDate getLastDateOfWeek() {
        return LocalDate.now().with(TemporalAdjusters.ofDateAdjuster(adjuster ->
                adjuster.plusDays(DayOfWeek.SUNDAY.getValue() - adjuster.getDayOfWeek().getValue()))
        );
    }

    /**
     * localDatetime 转为 时间戳,精确到秒
     *
     * @param localDateTime
     * @return 时间戳
     **/
    public static Long TimeToStampsOfSeconds(LocalDateTime localDateTime) {
        //return Timestamp.valueOf(localDateTime).toInstant().getEpochSecond();
        return localDateTime.atZone(ZONE_ID).toEpochSecond();
    }

    /**
     * localDatetime 转为 时间戳,精确到毫秒
     *
     * @param localDateTime
     * @return 时间戳
     **/
    public static Long TimeToStampsOfMills(LocalDateTime localDateTime) {
        //return Timestamp.valueOf(localDateTime).toInstant().toEpochMilli();
        return localDateTime.atZone(ZONE_ID).toInstant().toEpochMilli();
    }

    /**
     * @return 时间戳转时间，精确到毫秒
     * @author Leethea
     * @Description
     * @Date 2020/1/3 16:57
     **/
    public static LocalDateTime timeStampTotTimeMills(Long timeStamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZONE_ID);
    }

    /**
     * @return 时间戳转时间，精确到秒
     * @author Leethea
     * @Description
     * @Date 2020/1/3 16:57
     **/
    public static LocalDateTime timeStampToTimeSeconds(Long timeStamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timeStamp), ZONE_ID);
    }

}
