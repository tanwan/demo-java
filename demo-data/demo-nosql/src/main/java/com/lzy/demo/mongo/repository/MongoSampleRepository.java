/*
 * Created by LZY on 2017/8/5 22:28.
 */
package com.lzy.demo.mongo.repository;

import com.lzy.demo.mongo.model.MongoSample;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * The interface Mongo sample repository.
 *
 * @author LZY
 * @version v1.0
 */
public interface MongoSampleRepository extends MongoRepository<MongoSample, Integer> {
    /**
     * Find users by age between list.
     *
     * @param ageGT the age gt
     * @param ageLT the age lt
     * @return the list
     */
    @Query("{ 'age' : { $gt: ?0, $lt: ?1} }")
    List<MongoSample> findUsersByAgeBetween(int ageGT, int ageLT);
}
