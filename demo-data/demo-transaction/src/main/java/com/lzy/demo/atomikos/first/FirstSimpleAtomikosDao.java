package com.lzy.demo.atomikos.first;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirstSimpleAtomikosDao extends JpaRepository<SimpleAtomikos, Integer> {

    /**
     * 按name查找
     *
     * @param name name
     * @return SimpleAtomikos
     */
    SimpleAtomikos findByName(String name);
}
