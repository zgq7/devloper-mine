package com.dev.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created on 2019-10-29 10:55.
 *
 * @author zgq7
 */
public class TestDto {

    /**
     * group : 作用点，声明在哪个class上在何处生效
     * group : 必须使用 interface 进行分组
     **/
    @NotNull
    @NotBlank
    @Size(max = 10, min = 2, message = "参数不合法", groups = add.class)
    private String id;

    public interface add {
    }

    public interface delete {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
