package com.lzy.demo.jpa.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 内部事务
 *
 * @author LZY
 * @version v1.0
 */
@Service
public class InnerTransactionService extends AbstractTransactionService {

    private static final String SERVICE_NAME = "inner";


    /**
     * 内部无事务
     *
     * @param throwException the throw exception
     */
    public void no(boolean throwException) {
        registerCallback(SERVICE_NAME);
        save(getName(SERVICE_NAME));
        throwException(throwException);
    }

    /**
     * 内部Propagation.SUPPORTS
     *
     * @param throwException the throw exception
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void supports(boolean throwException) {
        registerCallback(SERVICE_NAME);
        save(getName(SERVICE_NAME));
        throwException(throwException);
    }

    /**
     * 内部Propagation.NOT_SUPPORTED
     *
     * @param throwException the throw exception
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public void notSupported(boolean throwException) {
        registerCallback(SERVICE_NAME);
        save(getName(SERVICE_NAME));
        throwException(throwException);
    }

    /**
     * 内部Propagation.MANDATORY
     *
     * @param throwException the throw exception
     */
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    public void mandatory(boolean throwException) {
        registerCallback(SERVICE_NAME);
        save(getName(SERVICE_NAME));
        throwException(throwException);
    }

    /**
     * 内部Propagation.REQUIRED
     *
     * @param throwException the throw exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void required(boolean throwException) {
        registerCallback(SERVICE_NAME);
        save(getName(SERVICE_NAME));
        throwException(throwException);
    }

    /**
     * 内部Propagation.REQUIRES_NEW
     *
     * @param throwException the throw exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void requiresNew(boolean throwException) {
        registerCallback(SERVICE_NAME);
        save(getName(SERVICE_NAME));
        throwException(throwException);
    }

    /**
     * 内部Propagation.NEVER
     *
     * @param throwException the throw exception
     */
    @Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
    public void never(boolean throwException) {
        registerCallback(SERVICE_NAME);
        save(getName(SERVICE_NAME));
        throwException(throwException);
    }

    /**
     * 内部事务
     *
     * @param throwException the throw exception
     */
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void nested(boolean throwException) {
        registerCallback(SERVICE_NAME);
        saveUseJDBC(getName(SERVICE_NAME));
        throwException(throwException);
    }
}
