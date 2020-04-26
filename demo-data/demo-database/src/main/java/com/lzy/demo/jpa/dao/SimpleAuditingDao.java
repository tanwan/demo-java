/*
 * Created by LZY on 2017/9/5 21:20.
 */
package com.lzy.demo.jpa.dao;

import com.lzy.demo.jpa.entity.SimpleAuditing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author LZY
 * @version v1.0
 */
public interface SimpleAuditingDao extends JpaRepository<SimpleAuditing, Integer> {
}
