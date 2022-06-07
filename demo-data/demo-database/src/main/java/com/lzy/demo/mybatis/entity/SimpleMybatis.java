package com.lzy.demo.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.lzy.demo.mybatis.enums.UseEnumValueEnum;
import com.lzy.demo.mybatis.enums.UseStringEnum;

import java.io.Serializable;

/**
 * 注解是mybatis-plus需要的
 *
 * @author lzy
 * @version v1.0
 */
@TableName(value = "simple_mybatis")
public class SimpleMybatis implements Serializable {

    /**
     * 使用@TableId的IdType可以指定id的类型,自增,自行输入,uuid,分布式生成整型id,分布式生成字符串型id,不设置
     */
    @TableId
    private Integer id;

    /**
     * 使用@TableField还可以设置很多属性
     */
    @TableField
    private String name;

    /**
     * 使用枚举值
     */
    private UseStringEnum useStringEnum;

    /**
     * mybatis-plus默认使用枚举值,使用@EnumValue来指定使用枚举的变量
     */
    private UseEnumValueEnum useEnumValueEnum;

    /**
     * 乐观锁标记
     */
    @Version
    private int version;

    /**
     * 逻辑删除标记
     */
    @TableLogic(value = "1", delval = "0")
    private int delFlg;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets use string enum.
     *
     * @param useStringEnum the use string enum
     */
    public void setUseStringEnum(UseStringEnum useStringEnum) {
        this.useStringEnum = useStringEnum;
    }

    /**
     * Gets use string enum.
     *
     * @return the use string enum
     */
    public UseStringEnum getUseStringEnum() {
        return useStringEnum;
    }


    public UseEnumValueEnum getUseEnumValueEnum() {
        return useEnumValueEnum;
    }

    public void setUseEnumValueEnum(UseEnumValueEnum useEnumValueEnum) {
        this.useEnumValueEnum = useEnumValueEnum;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(int delFlg) {
        this.delFlg = delFlg;
    }

    @Override
    public String toString() {
        return "SimpleMybatis{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", useStringEnum=" + useStringEnum +
                ", useEnumValueEnum=" + useEnumValueEnum +
                ", version=" + version +
                ", delFlg=" + delFlg +
                '}';
    }
}
