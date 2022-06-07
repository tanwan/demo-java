package com.lzy.demo.jpa.dao;

import com.lzy.demo.jpa.entity.SimpleAuditing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleAuditingDao extends JpaRepository<SimpleAuditing, Integer> {
}
