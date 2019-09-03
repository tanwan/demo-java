/*
 * Created by LZY on 2017/3/11 15:56.
 */
package com.lzy.demo.jpa.dao.impl;

import com.lzy.demo.jpa.dao.CustomSampleJpaDao;
import com.lzy.demo.jpa.entity.SampleJpaRelevance;
import com.lzy.demo.jpa.entity.SampleJpa;
import org.hibernate.jpa.QueryHints;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 自定义查询，默认以Impl结尾
 *
 * @author LZY
 * @version v1.0
 * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository
 */
public class SampleJpaDaoImpl implements CustomSampleJpaDao {
    @Resource
    private EntityManager entityManager;


    /**
     * 自定义查询
     *
     * @param name
     * @return the list
     */
    @Override
    public List<SampleJpa> customQuery(String name) {
        Query query = entityManager.createQuery("SELECT u from SampleJpa u where u.name=:name");
        query.setParameter("name", "lzy");
        return query.getResultList();
    }

    /**
     * 使用缓存
     *
     * @param name the name
     * @return the list
     */
    @Override
    public List<SampleJpa> queryCache(String name) {
        Query query = entityManager.createQuery("SELECT u from SampleJpa u where u.name=:name");
        query.setParameter("name", name);
        //设置查询缓存
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        //设置缓存区名称(默认为otherwise),cache region对应ehcache的缓存名,这边不能同@org.hibernate.annotations.Cache共用同一个缓存
        query.setHint(QueryHints.HINT_CACHE_REGION, "queryCacheRegion");
        return query.getResultList();
    }

    /**
     * Criteria query list.
     *
     * @param name the name
     * @param age  the age
     * @return the list
     */
    @Override
    public List<SampleJpa> criteriaQuery(String name, Integer age) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //这里的类型确定最终查询结果的类型
        CriteriaQuery<SampleJpa> query = cb.createQuery(SampleJpa.class);
        Root<SampleJpa> root = query.from(SampleJpa.class);
        Predicate where = cb.conjunction();
        where = cb.and(where, cb.equal(root.get("name").as(String.class), name));
        where = cb.and(where, cb.greaterThan(root.get("age").as(Integer.class), age));
        query.where(where);
        TypedQuery<SampleJpa> typeQuery = entityManager.createQuery(query);
        return typeQuery.getResultList();
    }

    /**
     * 返回自定义的类
     *
     * @param name the name
     * @return the object
     */
    @Override
    public Object criteriaQueryReturnCustom(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //这里的类型确定最终查询结果的类型
        CriteriaQuery<Custom> query = cb.createQuery(Custom.class);
        Root<SampleJpa> root = query.from(SampleJpa.class);
        query.where(cb.equal(root.get("name").as(String.class), name));
        //custom需要有对应的构造函数
        query.select(cb.construct(Custom.class, root.get("name"), root.get("age")));
        //query.multiselect(root.get("name"),root.get("age"));
        TypedQuery<Custom> typeQuery = entityManager.createQuery(query);
        return typeQuery.getResultList();
    }

    /**
     * 返回Object
     *
     * @param name the name
     * @return the object
     */
    @Override
    public List<Object[]> criteriaQueryReturnObject(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //这里的类型确定最终查询结果的类型
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<SampleJpa> root = query.from(SampleJpa.class);
        query.where(cb.equal(root.get("name").as(String.class), name));
        query.select(cb.array(root.get("name"), root.get("age")));
        //query.multiselect(root.get("name"),root.get("age"));
        TypedQuery<Object[]> typeQuery = entityManager.createQuery(query);
        return typeQuery.getResultList();
    }

    /**
     * 返回Tuple
     *
     * @param name the name
     * @return the object
     */
    @Override
    public List<Tuple> criteriaQueryReturnTuple(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //这里的类型确定最终查询结果的类型
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<SampleJpa> root = query.from(SampleJpa.class);
        query.where(cb.equal(root.get("name").as(String.class), name));
        query.select(cb.tuple(root.get("name").alias("name"), root.get("age")));
        TypedQuery<Tuple> typeQuery = entityManager.createQuery(query);
        return typeQuery.getResultList();
    }

    /**
     * 多表查询
     *
     * @param userId    the user id
     * @param imageName the image name
     * @return the object
     */
    @Override
    public List<Tuple> criteriaQueryJoin(Integer userId, String imageName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //这里的类型确定最终查询结果的类型
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<SampleJpaRelevance> sampleJpaRelevanceRoot = query.from(SampleJpaRelevance.class);
        Join<SampleJpaRelevance, SampleJpa> join = sampleJpaRelevanceRoot.join("sampleJpa");
        query.where(
                cb.equal(sampleJpaRelevanceRoot.get("name"), imageName));
        query.select(cb.tuple(sampleJpaRelevanceRoot.get("id"), sampleJpaRelevanceRoot.get("name"), join.get("name")));
        TypedQuery<Tuple> typeQuery = entityManager.createQuery(query);
        return typeQuery.getResultList();
    }

    public static class Custom {
        private String name;
        private Integer age;

        public Custom(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Custom{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
