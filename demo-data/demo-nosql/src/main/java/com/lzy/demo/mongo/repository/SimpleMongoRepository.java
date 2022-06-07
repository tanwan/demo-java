package com.lzy.demo.mongo.repository;

import com.lzy.demo.mongo.model.SimpleMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SimpleMongoRepository extends MongoRepository<SimpleMongo, Integer> {
    /**
     * Find users by age between list.
     *
     * @param ageGT the age gt
     * @param ageLT the age lt
     * @return the list
     */
    @Query("{ 'age' : { $gt: ?0, $lt: ?1} }")
    List<SimpleMongo> findUsersByAgeBetween(int ageGT, int ageLT);
}
