package com.lzy.demo.jooq;

import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectOrderByStep;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import org.jooq.conf.ParamType;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Method;
import java.util.List;

public abstract class BaseDAO<R extends UpdatableRecord<R>, P, T> extends DAOImpl<R, P, T> {
    private Method idMethod;

    /**
     * Instantiates a new Base dao.
     *
     * @param table the table
     * @param type  the type
     */
    protected BaseDAO(Table<R> table, Class<P> type) {
        super(table, type);
        try {
            idMethod = type.getDeclaredMethod("getId");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    @Override
    public void setConfiguration(Configuration configuration) {
        super.setConfiguration(configuration);
    }

    /**
     * 分页
     *
     * @param <S>             the type parameter
     * @param pageable        the pageable
     * @param selectLimitStep the select limit step
     * @return the page
     */
    public <S extends Record> Page<P> fetchPage(Pageable pageable, SelectOrderByStep<S> selectLimitStep) {
        //处理排序
        if (pageable.getSort() != null) {
            pageable.getSort().stream()
                    .map(s -> s.getDirection().isAscending() ? getTable().field(s.getProperty()).asc() : getTable().field(s.getProperty()).desc()
                    ).forEach(selectLimitStep::orderBy);
        }

        //使用mysql的SQL_CALC_FOUND_ROWS获取条数和结果
        String pageSql = selectLimitStep.getSQL(ParamType.INLINED);
        pageSql = pageSql.replaceFirst("select", "select SQL_CALC_FOUND_ROWS ") + " limit ?,?";
        List<P> records = ctx().fetch(pageSql, pageable.getOffset(), pageable.getPageSize()).map(r -> r.into(getType()));
        long totalCount = ctx().fetchOne("SELECT FOUND_ROWS()").into(Long.class);
        return new PageImpl<>(records, pageable, totalCount);
    }

    @Override
    public T getId(P object) {
        try {
            return (T) idMethod.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
