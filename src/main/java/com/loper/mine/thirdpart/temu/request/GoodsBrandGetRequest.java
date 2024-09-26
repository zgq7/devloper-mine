package com.loper.mine.thirdpart.temu.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsBrandGetRequest extends RequestBody {

    private Integer pageSize;

    private Integer page;

    private Integer vid;

    private String brandName;

    @Override
    public String getType() {
        return "bg.goods.brand.get";
    }
}
