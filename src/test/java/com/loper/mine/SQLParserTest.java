package com.loper.mine;

import com.alibaba.druid.sql.SQLUtils;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CodePointBuffer;
import org.antlr.v4.runtime.CodePointCharStream;
import org.junit.Test;

import java.nio.CharBuffer;

public class SQLParserTest {
    private static final String SQL1 = "select rank from table ";
    private static final String SQL2 = "SELECT RANK FROM TABLE ";
    private static final String DB = "MySQL";

    @Test
    public void antlr4() {
    }

    @Test
    public void druid() {
        System.out.println(SQLUtils.format(SQL1, DB));
    }

    private static CharStream getSQLCharStream(final String sql) {
        CodePointBuffer buffer = CodePointBuffer.withChars(CharBuffer.wrap(sql.toCharArray()));
        return CodePointCharStream.fromBuffer(buffer);
    }
}
