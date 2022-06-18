package com.jasong.yanfaq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author jasonG
 * @since 2022-05-04
 */
@Getter
@Setter
@ApiModel(value = "Courses对象", description = "")
public class Courses implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String value;
    private String label;

    @TableField(exist = false)
    private List<Courses> children;
    private Integer pid;


}
