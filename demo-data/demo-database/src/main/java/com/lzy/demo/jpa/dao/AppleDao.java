package com.lzy.demo.jpa.dao;

import com.lzy.demo.jpa.entity.Apple;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppleDao extends JpaRepository<Apple, Integer> {
}
