package com.lzy.demo.jdbi.mapper;

import com.lzy.demo.enums.UseEnumValueEnum;
import org.jdbi.v3.core.argument.AbstractArgumentFactory;
import org.jdbi.v3.core.argument.Argument;
import org.jdbi.v3.core.config.ConfigRegistry;

import java.sql.Types;

/**
 * java对象到sql的映射
 *
 * @author lzy
 * @version v1.0
 */
public class UseEnumValueEnumArgumentFactory extends AbstractArgumentFactory<UseEnumValueEnum> {
    public UseEnumValueEnumArgumentFactory() {
        // 设置类型
        super(Types.INTEGER);
    }

    @Override
    protected Argument build(UseEnumValueEnum value, ConfigRegistry config) {
        return (position, statement, ctx) -> statement.setInt(position, value.getCode());
    }
}
