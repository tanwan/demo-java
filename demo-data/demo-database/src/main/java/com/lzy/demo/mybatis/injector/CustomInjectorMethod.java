package com.lzy.demo.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;

public class CustomInjectorMethod extends AbstractMethod {
    /**
     * {@inheritDoc}
     *
     * @see SelectById#injectMappedStatement(java.lang.Class, java.lang.Class, com.baomidou.mybatisplus.core.metadata.TableInfo)
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlSource sqlSource = new RawSqlSource(configuration, String.format("SELECT %s FROM %s WHERE %s=#{%s} %s",
                // 所有字段
                sqlSelectColumns(tableInfo, false),
                // 表名,id列名,id属性
                tableInfo.getTableName(), tableInfo.getKeyColumn(), tableInfo.getKeyProperty(),
                // 逻辑删除
                tableInfo.getLogicDeleteSql(true, false)), Object.class);
        return this.addSelectMappedStatementForTable(mapperClass, "customInjectorMethod", sqlSource, tableInfo);
    }
}
