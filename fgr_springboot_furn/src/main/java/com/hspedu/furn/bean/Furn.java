package com.hspedu.furn.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: guorui fu
 * @versiion: 1.0
 * @TableName(value = "furn")  表名和类名不一致时进行指定
 */
//@TableName(value = "furn")
@Data
public class Furn {
    @TableId(type = IdType.AUTO)//主键标识 对应表的字段为主键 主键类型为自增长
    private Integer id;
    //根据业务需求，配置相应注解
    //字符串非空校验使用@NotEmpty
    @NotEmpty(message = "请输入家居名")
    private String name;
    @NotEmpty(message = "请输入厂商名")
    private String maker;
    @NotNull(message = "请输入数字")
    @Range(min = 0,message = "价格不能 < 0")
    private Double price;
    @NotNull(message = "请输入数字")
    @Range(min = 0,message = "销量不能 < 0")
    private Integer sales;
    @NotNull(message = "请输入数字")
    @Range(min = 0,message = "库存不能 < 0")
    private Integer stock;

}
