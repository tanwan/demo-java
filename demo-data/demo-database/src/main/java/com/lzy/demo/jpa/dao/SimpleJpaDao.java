package com.lzy.demo.jpa.dao;

import com.lzy.demo.jpa.entity.SimpleJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.QueryHint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface SimpleJpaDao extends JpaRepository<SimpleJpa, Integer>, SimpleCustomJpaDao, JpaSpecificationExecutor<SimpleJpa> {
    /**
     * 使用排序 Sort里的属性是实体类的属性名,而不是表的属性名
     *
     * @param name the name
     * @param sort the sort
     * @return the list
     */
    List<SimpleJpa> findByName(String name, Sort sort);

    /**
     * 使用分页
     *
     * @param name     the name
     * @param pageable the pageable
     * @return the page
     */
    Page<SimpleJpa> findByName(String name, Pageable pageable);

    /**
     * 使用IgnoreCase
     *
     * @param name the name
     * @return the list
     */
    List<SimpleJpa> findByNameIgnoreCase(String name);

    /**
     * 使用OrderBy
     *
     * @param name the name
     * @return the list
     */
    List<SimpleJpa> findByNameOrderByAgeDesc(String name);


    /**
     * 使用Top也可以使用First
     *
     * @return the SimpleJpa
     */
    SimpleJpa findTopByOrderByAgeDesc();


    /**
     * 使用Top加数字
     *
     * @param name the name
     * @param sort the sort
     * @return the list
     */
    List<SimpleJpa> findTop2ByName(String name, Sort sort);


    /**
     * 使用Pageable
     *
     * @param name     the name
     * @param pageable the pageable
     * @return the page
     */
    @Query("SELECT u FROM SimpleJpa u WHERE u.name = :name")
    Page<SimpleJpa> pageQuery(@Param("name") String name, Pageable pageable);

    /**
     * 使用Sort,?1表示第一个参数,?2表示第二个参数,依此类推
     *
     * @param name the name
     * @param sort the sort
     * @return the list
     */
    @Query("SELECT u FROM SimpleJpa u WHERE u.name = ?1")
    List<SimpleJpa> sortQuery(String name, Sort sort);

    /**
     * 使用in
     *
     * @param ages the ages
     * @return the list
     */
    @Query("SELECT u FROM SimpleJpa u WHERE u.age in (:ages)")
    List<SimpleJpa> inQuery(@Param("ages") List<Integer> ages);

    /**
     * 返回<code>List<String></code>
     *
     * @param name the name
     * @return the list
     */
    @Query("SELECT u.name FROM SimpleJpa u WHERE u.name = :name ")
    List<String> returnList(@Param("name") String name);

    /**
     * 返回<code>List<Map<String,Object>></code>
     * select必须使用as
     *
     * @param name the name
     * @return the list
     */
    @Query("SELECT u.name AS name ,u.age AS age FROM SimpleJpa u WHERE u.name = :name")
    List<Map<String, Object>> returnListMap(@Param("name") String name);

    /**
     * 返回<code>List<Object[]></code>
     *
     * @return the list
     */
    @Query("SELECT u.name,u.age FROM SimpleJpa u ")
    List<Object[]> returnListObjects();

    /**
     * 返回<code>Object</code>
     * 把一行记录当作是一个Object,而Object其实是个Object[]
     * 这里只会返回一条记录,因此返回值是Object,而Object是一个Object[]
     *
     * @return the list
     */
    @Query("SELECT MAX(u.age),MAX(u.createTime) FROM SimpleJpa u ")
    Object returnObject();

    /**
     * 返回<code> Object[]</code>
     * 把一行记录当作是一个Object,而Object其实是个Object[]
     * 这里会返回多条记录,因此返回值是Object[]
     *
     * @return the object [ ]
     */
    @Query("SELECT u.name,u.age FROM SimpleJpa u")
    Object[] returnObjects();

    /**
     * 返回<code>Map<String, Object></code>
     * select必须使用as
     *
     * @return the list
     */
    @Query("SELECT MAX(u.age) AS maxAge,MAX(u.createTime) AS maxCreateTime FROM SimpleJpa u ")
    Map<String, Object> returnMap();

    /**
     * 使用原生sql
     *
     * @param age the age
     * @return the list
     */
    @Query(value = "SELECT * FROM simple_jpa WHERE age > :age", nativeQuery = true)
    List<SimpleJpa> nativeQuery(@Param("age") Integer age);

    /**
     * 使用原生sql
     * NativeJpaQuery要求查询语句要有#pageable或者#sort
     *
     * @param name     the name
     * @param pageable the pageable
     * @return the list
     */
    @Query(value = "SELECT * FROM simple_jpa where name = ?1",
            countQuery = "SELECT COUNT(*) FROM simple_jpa where name = ?1",
            nativeQuery = true)
    Page<SimpleJpa> nativeQueryPage(String name, Pageable pageable);


    /**
     * 使用Modify 更新SimpleJpa
     * 失败返回0，成功返回1
     * 调用类如果没有事务管理，那么这个方法就必须要有@Transactional
     *
     * @param name the name
     * @param id   the id
     * @return the int
     */
    @Modifying
    @Query("update SimpleJpa u set u.name = ?1 where u.id = ?2")
    @Transactional
    int useModifying(String name, Integer id);

    /**
     * 异步调用 CompletableFuture
     *
     * @param name the name
     * @return the completable future
     */
    @Async
    CompletableFuture<List<SimpleJpa>> findByName(String name);

    /**
     * 查询缓存
     *
     * @param name the name
     * @return the SimpleJpa
     */
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true"), @QueryHint(name = "org.hibernate.cacheRegion", value = "queryCacheRegion")})
    @Query("SELECT u FROM SimpleJpa u WHERE  u.name = ?1")
    List<SimpleJpa> queryHint(String name);
}
