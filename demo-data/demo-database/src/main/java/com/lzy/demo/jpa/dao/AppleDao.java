/*
 * Created by LZY on 2017/9/3 15:20.
 */
package com.lzy.demo.jpa.dao;

import com.lzy.demo.jpa.entity.Apple;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author LZY
 * @version v1.0
 */
public interface AppleDao extends JpaRepository<Apple, Integer> {
}
