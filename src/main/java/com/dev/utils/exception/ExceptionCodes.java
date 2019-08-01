package com.dev.utils.exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ProtocolException;
import java.sql.SQLException;

/**
 * Created on 2019-07-31 16:01.
 *
 * @author zgq7
 * @apiNote 自定义的code - XxxException.class
 */
public enum ExceptionCodes {
    //RuntimeException 类型异常
    RUNTIME_EXCEPTION(100, "运行时异常", RuntimeException.class),
    NULL_POINT_EXCEPTION(101, "空指针异常", NullPointerException.class),
    ARITHMETIC_EXCEPTION(102, "数学错误，被0除", ArithmeticException.class),
    INDEX_OUT_OF_BOUNDS_EXCEPTION(103, "当某对象的索引超出范围时抛出异常", IndexOutOfBoundsException.class),
    ARRAY_INDEX_OUT_OF_EXCEPTION(103001, "数组下标越界", ArrayIndexOutOfBoundsException.class),
    CLASS_CAST_EXCEPTION(104, "强制转换异常", ClassCastException.class),
    ILLEGAL_ARGUMENT_EXCEPTION(105, "非法转换", IllegalArgumentException.class),
    NUMBER_FORMAT_EXCEPTION(105001, "字符串转换为数字异常类", NumberFormatException.class),
    PROTOCOL_EXCEPTION(106, "网络协议有错误", ProtocolException.class),

    //IOException 类型异常
    IO_EXCEPTION(200, "IO 流异常", IOException.class),
    FILE_NOT_FOUND_EXCEPTION(201, "文件找不到", FileNotFoundException.class),

    //数据库 sql 操作异常
    SQL_EXCEPTION(300, "操作数据库异常", SQLException.class),

    //其他相关异常
    REFLECTIVE_OPERATION_EXCEPTION(400, "", ReflectiveOperationException.class),
    ILLEGAL_ACESS_EXCEPTION(401, "访问某类被拒绝时抛出的异常", IllegalAccessException.class);


    private int code;

    private String msg;

    private Class<Exception> klass;

    ExceptionCodes(int code, String msg, Class klass) {
        this.code = code;
        this.msg = msg;
        this.klass = klass;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public Class<Exception> getKlass() {
        return this.klass;
    }

    /**
     * 通过异常码获取对应异常类
     **/
    public static Class<Exception> getKlassByCode(int code) {
        for (ExceptionCodes exceptionCodes : ExceptionCodes.values()) {
            if (exceptionCodes.getCode() == code)
                return exceptionCodes.getKlass();
        }
        return null;
    }

    /**
     * 根据异常码获取对应异常信息
     **/
    public static String getMsgByCode(int code) {
        for (ExceptionCodes exceptionCodes : ExceptionCodes.values()) {
            if (exceptionCodes.getCode() == code)
                return exceptionCodes.getMsg();
        }
        return null;
    }

    /**
     * 根据异常类获取异常码
     **/
    public static int getCodeByKlass(Class<? extends Exception> klass) {
        for (ExceptionCodes exceptionCodes : ExceptionCodes.values()) {
            if (exceptionCodes.getKlass() == klass)
                return exceptionCodes.getCode();
        }
        return 3000;
    }

    /**
     * 根据异常类获取异常信息
     **/
    public static String getMsgByKlass(Class<? extends Exception> klass) {
        for (ExceptionCodes exceptionCodes : ExceptionCodes.values()) {
            if (exceptionCodes.getKlass() == klass)
                return exceptionCodes.getMsg();
        }
        return null;
    }


}
