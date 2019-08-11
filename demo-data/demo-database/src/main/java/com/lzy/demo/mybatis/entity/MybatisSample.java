/*
 * Created by lzy on 2019-07-27 09:40.
 */
package com.lzy.demo.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lzy.demo.mybatis.enums.UseEnumValueEnum;
import com.lzy.demo.mybatis.enums.UseIndexEnum;
import com.lzy.demo.mybatis.enums.UseStringEnum;
import com.lzy.demo.mybatis.typehandler.CustomUseIndexEnumTypeHandler;

import java.io.Serializable;

/**
 * 注解是mybatis-plus需要的
 *
 * @author lzy
 * @version v1.0
 */
@TableName(value = "mybatis_sample", resultMap = "com.lzy.demo.mybatis.mapper.MybatisSampleMapper.mybatisSampleResultMap")
public class MybatisSample implements Serializable {

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
     * 使用枚举的索引值,这边指定的typeHandler只对修改有效,查询的话,需要使用@TableName的resultMap
     * 由于typeHandler泛型的限制,不能直接使用EnumOrdinalTypeHandler,因此需要继承EnumOrdinalTypeHandler
     */
    @TableField(typeHandler = CustomUseIndexEnumTypeHandler.class)
    private UseIndexEnum useIndexEnum;

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
     * Sets use index enum.
     *
     * @param useIndexEnum the use index enum
     */
    public void setUseIndexEnum(UseIndexEnum useIndexEnum) {
        this.useIndexEnum = useIndexEnum;
    }

    /**
     * Gets use string enum.
     *
     * @return the use string enum
     */
    public UseStringEnum getUseStringEnum() {
        return useStringEnum;
    }

    /**
     * Gets use index enum.
     *
     * @return the use index enum
     */
    public UseIndexEnum getUseIndexEnum() {
        return useIndexEnum;
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
        return "MybatisSample{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", useStringEnum=" + useStringEnum +
                ", useIndexEnum=" + useIndexEnum +
                ", useEnumValueEnum=" + useEnumValueEnum +
                ", version=" + version +
                ", delFlg=" + delFlg +
                '}';
    }
}
