package com.lzy.demo.jdbi.mapper;

import com.lzy.demo.enums.UseEnumValueEnum;
import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.mapper.ColumnMapperFactory;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 结果字段映射
 *
 * @see ColumnMapperFactory
 * @author lzy
 * @version v1.0
 */
public class UseEnumValueEnumMapper implements ColumnMapper<UseEnumValueEnum> {
    @Override
    public UseEnumValueEnum map(ResultSet r, int columnNumber, StatementContext ctx) throws SQLException {
        return UseEnumValueEnum.byCode(r.getInt(columnNumber));
    }
}
