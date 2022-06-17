package com.lzy.demo.jdbi.mapper;

import com.lzy.demo.enums.UseEnumValueEnum;
import com.lzy.demo.enums.UseStringEnum;
import com.lzy.demo.jdbi.entity.SimpleJdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.mapper.RowMapperFactory;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper
 *
 * @see RowMapper
 * @see RowMapperFactory
 * @author lzy
 * @version v1.0
 */
public class SimpleJdbiRowMapper implements RowMapper<SimpleJdbi> {
    @Override
    public SimpleJdbi map(ResultSet rs, StatementContext ctx) throws SQLException {
        SimpleJdbi simpleJdbi = new SimpleJdbi();
        simpleJdbi.setId(rs.getInt("id"));
        simpleJdbi.setName(rs.getString("name"));
        simpleJdbi.setUseStringEnum(UseStringEnum.valueOf(rs.getString("use_string_enum")));
        simpleJdbi.setUseEnumValueEnum(UseEnumValueEnum.byCode(rs.getInt("use_enum_value_enum")));
        return simpleJdbi;
    }
}
