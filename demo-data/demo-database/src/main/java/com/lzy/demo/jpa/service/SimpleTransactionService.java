package com.lzy.demo.jpa.service;

import com.lzy.demo.jpa.entity.SimpleJpa;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;

@Service
public class SimpleTransactionService extends AbstractTransactionService {

    private static final String SERVICE_NAME = "outer";

    @Resource
    private InnerTransactionService innerTransactionService;

    @Resource
    private PlatformTransactionManager transactionManager;

    /**
     * 不需要显示调用保存
     *
     * @param name the name
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(String name) {
        SimpleJpa simpleJpa = simpleJpaDao.findById(1).get();
        simpleJpa.setName(name);
    }

    /**
     * 手动提交事务
     *
     * @param throwException the throw exception
     */
    public void manualCommit(boolean throwException) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // 返回已激活或者创建一个事务
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        registerCallback("manualCommit");
        save("manualCommit");
        try {
            throwException(throwException);
            // 提交TransactionStatus代表的事务
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            // 回滚TransactionStatus代表的事务
            transactionManager.rollback(transactionStatus);
        }
    }

    /**
     * 外层无事务,内层Required
     *
     * @param outerException the outer exception
     * @param innerException the inner exception
     */
    public void outerNoAndInnerRequired(boolean outerException, boolean innerException) {
        save(getName(SERVICE_NAME));
        innerTransactionService.required(innerException);
        throwException(outerException);
    }

    /**
     * 外层无事务,内层RequireNew
     *
     * @param outerException the outer exception
     * @param innerException the inner exception
     */
    public void outerNoAndInnerRequiresNew(boolean outerException, boolean innerException) {
        save(getName(SERVICE_NAME));
        innerTransactionService.requiresNew(innerException);
        throwException(outerException);
    }

    /**
     * 外层无事务,内层SUPPORTS
     *
     * @param outerException the outer exception
     * @param innerException the inner exception
     */
    public void outerNoAndInnerSupports(boolean outerException, boolean innerException) {
        save(getName(SERVICE_NAME));
        innerTransactionService.supports(innerException);
        throwException(outerException);
    }

    /**
     * 外层无事务,内层NOT_SUPPORTED
     *
     * @param outerException the outer exception
     * @param innerException the inner exception
     */
    public void outerNoAndInnerNotSupported(boolean outerException, boolean innerException) {
        save(getName(SERVICE_NAME));
        innerTransactionService.notSupported(innerException);
        throwException(outerException);
    }

    /**
     * 外层无事务,内层NEVER
     *
     * @param outerException the outer exception
     * @param innerException the inner exception
     */
    public void outerNoAndInnerNever(boolean outerException, boolean innerException) {
        save(getName(SERVICE_NAME));
        innerTransactionService.never(innerException);
        throwException(outerException);
    }

    /**
     * 外层无事务,内层MANDATORY
     */
    public void outerNoAndInnerMandatory() {
        save(getName(SERVICE_NAME));
        innerTransactionService.mandatory(false);
    }

    /**
     * 外层无事务,内层NESTED
     *
     * @param outerException the outer exception
     * @param innerException the inner exception
     * @param catchException the catch exception
     */
    public void outerNoAndInnerNested(boolean outerException, boolean innerException, boolean catchException) {
        saveUseJDBC(getName(SERVICE_NAME));
        try {
            innerTransactionService.nested(innerException);
        } catch (Exception e) {
            if (!catchException) {
                throw e;
            }
        }
        throwException(outerException);
    }


    /**
     * 外层有事务,内层没有
     *
     * @param outerException the outer exception
     * @param innerException the inner exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void outerHasAndInnerNo(boolean outerException, boolean innerException) {
        registerCallback(SERVICE_NAME);
        save(getName(SERVICE_NAME));
        innerTransactionService.no(innerException);
        throwException(outerException);
    }

    /**
     * 外层有事务,内层REQUIRED
     *
     * @param outerException the outer exception
     * @param innerException the inner exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void outerHasAndInnerRequired(boolean outerException, boolean innerException) {
        registerCallback(SERVICE_NAME);
        save(getName(SERVICE_NAME));
        innerTransactionService.required(innerException);
        throwException(outerException);
    }

    /**
     * 外层有事务,内层SUPPORTS
     *
     * @param outerException the outer exception
     * @param innerException the inner exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void outerHasAndInnerSupports(boolean outerException, boolean innerException) {
        save(getName(SERVICE_NAME));
        innerTransactionService.supports(innerException);
        throwException(outerException);
    }

    /**
     * 外层有事务,内层MANDATORY
     *
     * @param outerException the outer exception
     * @param innerException the inner exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void outerHasAndInnerMandatory(boolean outerException, boolean innerException) {
        registerCallback(SERVICE_NAME);
        save(getName(SERVICE_NAME));
        innerTransactionService.mandatory(innerException);
        throwException(outerException);
    }


    /**
     * 外层有事务,内层REQUIRES_NEW
     *
     * @param outerException the outer exception
     * @param innerException the inner exception
     * @param catchException the catch exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void outerHasAndInnerRequiresNew(boolean outerException, boolean innerException, boolean catchException) {
        registerCallback(SERVICE_NAME);
        save(getName(SERVICE_NAME));
        try {
            innerTransactionService.requiresNew(innerException);
        } catch (Exception e) {
            if (!catchException) {
                throw e;
            }
        }
        throwException(outerException);
    }

    /**
     * 外层有事务,内层NOT_SUPPORTED
     *
     * @param outerException the outer exception
     * @param innerException the inner exception
     * @param catchException the catch exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void outerHasAndInnerNotSupported(boolean outerException, boolean innerException, boolean catchException) {
        registerCallback(SERVICE_NAME);
        save(getName(SERVICE_NAME));
        try {
            innerTransactionService.notSupported(innerException);
        } catch (Exception e) {
            if (!catchException) {
                throw e;
            }
        }
        throwException(outerException);
    }

    /**
     * 外层有事务,内层NEVER
     */
    @Transactional(rollbackFor = Exception.class)
    public void outerHasAndInnerNever() {
        outerNoAndInnerNever(true, true);
    }


    /**
     * 外层有事务,内层NESTED
     *
     * @param outerException the outer exception
     * @param innerException the inner exception
     * @param catchException the catch exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void outerHasAndInnerNested(boolean outerException, boolean innerException, boolean catchException) {
        registerCallback(SERVICE_NAME);
        outerNoAndInnerNested(outerException, innerException, catchException);
    }

}
