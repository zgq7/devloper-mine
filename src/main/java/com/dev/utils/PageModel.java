package com.dev.utils;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by zgq7 on 2019/7/9.
 */
public final class PageModel implements Serializable {

    public PageModel(Instance instance) {
        this.pageNum = instance.pageNum;
        this.pageSize = instance.pageSize;
        this.count = instance.count;
        this.orderBy = instance.orderBy;
    }

    private static final int DEFAULT_PAGENUM = 1;

    private static final int DEFAULT_PAGESIZE = 10;

    private final int pageNum;

    private final int pageSize;

    private boolean count;

    private final LinkedHashMap orderBy;

    public static class Instance {

        private int pageNum;
        private int pageSize;
        private boolean count;
        private LinkedHashMap<String, String> orderBy = new LinkedHashMap<>();

        /**
         * 默认页号、默认页数、无排序
         **/
        public Instance() {
            this.pageNum = PageModel.DEFAULT_PAGENUM;
            this.pageSize = PageModel.DEFAULT_PAGESIZE;
            this.count = true;
        }

        /**
         * 自定义页号、自定义页数、无排序、查询总数
         **/
        public Instance(int pageSize, int pageNum) {
            this.pageSize = pageSize;
            this.pageNum = pageNum;
            this.count = true;
        }

        /**
         * 自定义页号、自定义页数、无排序、自定义是否查询总数
         **/
        public Instance(int pageSize, int pageNum, boolean count) {
            this.pageSize = pageSize;
            this.pageNum = pageNum;
            this.count = count;
        }

        public PageModel orderBy(LinkedHashMap<String, String> orderBy) {
            this.orderBy = orderBy;
            return new PageModel(this);
        }

/*        public PageModel orderBy(String[] fields,String[] sorts){

        }*/

        public PageModel newPageModel() {
            return new PageModel(this);
        }

    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isCount() {
        return count;
    }

    public LinkedHashMap getOrderBy() {
        return orderBy;
    }
}
