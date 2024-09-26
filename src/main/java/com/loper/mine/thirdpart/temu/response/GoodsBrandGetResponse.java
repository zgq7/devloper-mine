package com.loper.mine.thirdpart.temu.response;

import lombok.Data;

import java.util.List;

@Data
public class GoodsBrandGetResponse {

    private Long total;

    private List<PageItem> pageItems;

    @Data
    private static class PageItem {
        private Long vid;

        private Long brandId;

        private String brandNameEn;

        private Long pid;

        private String regSerialCode;
    }
}
