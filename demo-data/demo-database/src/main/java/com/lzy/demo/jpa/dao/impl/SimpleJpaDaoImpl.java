package com.lzy.demo.jpa.dao.impl;

import com.lzy.demo.jpa.dao.SimpleCustomJpaDao;
import com.lzy.demo.jpa.entity.SimpleJpaRelevance;
import com.lzy.demo.jpa.entity.SimpleJpa;
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
public class SimpleJpaDaoImpl implements SimpleCustomJpaDao {
    @Resource
    private EntityManager entityManager;


    /**
     * 自定义查询
     *
     * @param name
     * @return the list
     */
    @Override
    public List<SimpleJpa> customQuery(String name) {
        Query query = entityManager.createQuery("SELECT u from SimpleJpa u where u.name=:name");
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
    public List<SimpleJpa> queryCache(String name) {
        Query query = entityManager.createQuery("SELECT u from SimpleJpa u where u.name=:name");
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
    public List<SimpleJpa> criteriaQuery(String name, Integer age) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //这里的类型确定最终查询结果的类型
        CriteriaQuery<SimpleJpa> query = cb.createQuery(SimpleJpa.class);
        Root<SimpleJpa> root = query.from(SimpleJpa.class);
        Predicate where = cb.conjunction();
        where = cb.and(where, cb.equal(root.get("name").as(String.class), name));
        where = cb.and(where, cb.greaterThan(root.get("age").as(Integer.class), age));
        query.where(where);
        TypedQuery<SimpleJpa> typeQuery = entityManager.createQuery(query);
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
        Root<SimpleJpa> root = query.from(SimpleJpa.class);
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
        Root<SimpleJpa> root = query.from(SimpleJpa.class);
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
        Root<SimpleJpa> root = query.from(SimpleJpa.class);
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
        Root<SimpleJpaRelevance> simpleJpaRelevanceRoot = query.from(SimpleJpaRelevance.class);
        Join<SimpleJpaRelevance, SimpleJpa> join = simpleJpaRelevanceRoot.join("simpleJpa");
        query.where(
                cb.equal(simpleJpaRelevanceRoot.get("name"), imageName));
        query.select(cb.tuple(simpleJpaRelevanceRoot.get("id"), simpleJpaRelevanceRoot.get("name"), join.get("name")));
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
