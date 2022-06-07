package com.lzy.demo.atomikos;

import com.lzy.demo.atomikos.first.FirstSimpleAtomikosDao;
import com.lzy.demo.atomikos.second.SecondSimpleAtomikosDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class AtomikosService {


    @Resource
    private FirstSimpleAtomikosDao firstDao;

    @Resource
    private SecondSimpleAtomikosDao secondDao;


    /**
     * 插入
     *
     * @param name           name
     * @param throwException throwException
     */
    @Transactional
    public void insert(String name, boolean throwException) {
        com.lzy.demo.atomikos.first.SimpleAtomikos first = new com.lzy.demo.atomikos.first.SimpleAtomikos();
        first.setName(name);
        firstDao.save(first);

        com.lzy.demo.atomikos.second.SimpleAtomikos second = new com.lzy.demo.atomikos.second.SimpleAtomikos();
        second.setName(name);
        secondDao.save(second);
        if (throwException) {
            throw new RuntimeException("expect exception");
        }
    }
}
