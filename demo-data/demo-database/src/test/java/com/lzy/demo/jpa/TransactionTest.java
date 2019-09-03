/*
 * Created by LZY on 2017/3/11 16:04.
 */
package com.lzy.demo.jpa;

import com.lzy.demo.jpa.application.JpaApplication;
import com.lzy.demo.jpa.service.SampleTransactionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.IllegalTransactionStateException;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 事务测试
 * PROPAGATION.REQUIRED:如果当前存在事务,就使用当前的事务,否则就新建一个事务,默认值
 * PROPAGATION.SUPPORTS:如果当前存在事务,就使用当前的事务,否则就以非事务方式执行
 * PROPAGATION.MANDATORY:如果当前存在事务,就使用当前的事务,否则就抛出异常
 * PROPAGATION.REQUIRES_NEW:新建事务,如果当前存在事务,把当前事务挂起,两个事务没有关联
 * PROPAGATION.NOT_SUPPORTED:以非事务方式执行操作,如果当前存在事务,就把当前事务挂起
 * PROPAGATION.NEVER:以非事务方式执行,如果当前存在事务,则抛出异常
 * PROPAGATION.NESTED:如果当前存在事务,就嵌套一个新事务,否则就新建一个事务,两个事务是父子关系
 *
 * @author LZY
 * @version v1.0
 */
@SpringBootTest(classes = JpaApplication.class)
@TestPropertySource(properties = "spring.config.location=classpath:jpa/jpa-hakari.yml")
@Commit
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class TransactionTest {
    private static final String EXCEPT_EXCEPTION = "expect exception";
    private boolean outerException;
    private boolean innerException;

    @Resource
    protected SampleTransactionService sampleTransactionService;

    /**
     * 测试手动提交
     */
    @Test
    public void testManualCommit() {
        sampleTransactionService.manualCommit(false);
        sampleTransactionService.manualCommit(true);
    }


    /**
     * 外层无事务,内层Required,RequireNew
     * 1. 内层抛出异常 结果: 外层不回滚,内层回滚
     * 2. 外层抛出异常 结果: 外层不回滚,内层不回滚
     */
    @Test
    public void testOuterNoAndInnerRequire() {
        // REQUIRED
        Assertions.assertThatCode(() -> sampleTransactionService.outerNoAndInnerRequired(outerException, innerException))
                .hasMessage(EXCEPT_EXCEPTION);
        // REQUIRE_NEW
        Assertions.assertThatCode(() -> sampleTransactionService.outerNoAndInnerRequiresNew(outerException, innerException))
                .hasMessage(EXCEPT_EXCEPTION);
    }

    /**
     * 外层无事务,内层SUPPORTS,NOT_SUPPORTED,NEVER(本质也是没有事务)
     * 1. 内层抛出异常 结果: 外层不回滚,内层不回滚(虽然事务结果返回回滚,实际上并没有回滚)
     * 2. 外层抛出异常 结果: 外层不回滚,内层不回滚
     */
    @Test
    public void testOuterNoAndInnerNo() {
        // SUPPORTS
        Assertions.assertThatCode(() -> sampleTransactionService.outerNoAndInnerSupports(outerException, innerException))
                .hasMessage(EXCEPT_EXCEPTION);
        // NOT_SUPPORT
        Assertions.assertThatCode(() -> sampleTransactionService.outerNoAndInnerNotSupported(outerException, innerException))
                .hasMessage(EXCEPT_EXCEPTION);
        // NEVER
        Assertions.assertThatCode(() -> sampleTransactionService.outerNoAndInnerNever(outerException, innerException))
                .hasMessage(EXCEPT_EXCEPTION);
    }

    /**
     * 外层无事务,内层MANDATORY(内层直接抛出IllegalTransactionStateException异常)
     * 1. 内层抛出异常 结果: 外层不回滚,内层抛出IllegalTransactionStateException异常
     * 2. 外层抛出异常 结果: 外层不回滚,内层抛出IllegalTransactionStateException异常
     */
    @Test
    public void testOuterNoAndInnerMandatory() {
        // MANDATORY
        Assertions.assertThatCode(() -> sampleTransactionService.outerNoAndInnerMandatory())
                .isInstanceOf(IllegalTransactionStateException.class);
    }

    /**
     * 外层有事务,内层没有事务,REQUIRED,SUPPORTS,MANDATORY(本质就是使用外层的事务)
     * 1. 内层抛出异常 结果: 外层回滚,内层回滚
     * 2. 外层抛出异常 结果: 外层回滚,内层回滚
     */
    @Test
    public void testOuterHasAndInnerSupport() {
        // NO
        Assertions.assertThatCode(() -> sampleTransactionService.outerHasAndInnerNo(outerException, innerException))
                .hasMessage(EXCEPT_EXCEPTION);
        // REQUIRED
        Assertions.assertThatCode(() -> sampleTransactionService.outerHasAndInnerRequired(outerException, innerException))
                .hasMessage(EXCEPT_EXCEPTION);
        // SUPPORTS
        Assertions.assertThatCode(() -> sampleTransactionService.outerHasAndInnerSupports(outerException, innerException))
                .hasMessage(EXCEPT_EXCEPTION);
        // MANDATORY
        Assertions.assertThatCode(() -> sampleTransactionService.outerHasAndInnerMandatory(outerException, innerException))
                .hasMessage(EXCEPT_EXCEPTION);
    }


    /**
     * 外层有事务,内层REQUIRES_NEW
     * 1. 内层抛出异常 结果: 外层回滚,内层回滚
     * 2. 外层抛出异常 结果: 外层回滚,内层不回滚
     */
    @Test
    public void testOuterHasAndInnerRequiresNew() {
        // REQUIRES_NEW
        Assertions.assertThatCode(() -> sampleTransactionService.outerHasAndInnerRequiresNew(outerException, innerException, false))
                .hasMessage(EXCEPT_EXCEPTION);
    }

    /**
     * 外层有事务,内层REQUIRES_NEW
     * 内层抛出异常,外层捕获内层抛出的异常 结果: 外层不回滚,内层回滚
     */
    @Test
    public void testOuterHasAndInnerRequiresNewCatchException() {
        // REQUIRES_NEW
        sampleTransactionService.outerHasAndInnerRequiresNew(outerException, innerException, true);
    }

    /**
     * 外层有事务,内层NOT_SUPPORTED
     * 1. 内层抛出异常 结果: 外层回滚,内层不回滚(虽然事务结果返回回滚,实际上并没有回滚)
     * 2. 外层抛出异常 结果: 外层回滚,内层不回滚
     */
    @Test
    public void testOuterHasAndInnerNotSupported() {
        // NOT_SUPPORTED
        Assertions.assertThatCode(() -> sampleTransactionService.outerHasAndInnerNotSupported(outerException, innerException, false))
                .hasMessage(EXCEPT_EXCEPTION);
    }

    /**
     * 外层有事务,内层NOT_SUPPORTED
     * 内层抛出异常,外层捕获内层抛出的异常 结果: 外层不回滚,内层不回滚(虽然事务结果返回回滚,实际上并没有回滚)
     */
    @Test
    public void testOuterHasAndInnerNotSupportedCatch() {
        // NOT_SUPPORTED
        sampleTransactionService.outerHasAndInnerNotSupported(outerException, innerException, true);
    }

    /**
     * 外层有事务,内层NEVER,内层直接抛出IllegalTransactionStateException异常
     */
    @Test
    public void testOuterHasAndInnerNever() {
        // NEVER
        Assertions.assertThatCode(() -> sampleTransactionService.outerHasAndInnerNever())
                .isInstanceOf(IllegalTransactionStateException.class);
    }


    /**
     * 内层抛出异常
     */
    public static class InnerThrowException extends TransactionTest {
        @BeforeAll
        public void init() {
            init(false, true);
        }
    }


    /**
     * 外层抛出异常
     */
    public static class OuterThrowException extends TransactionTest {

        @BeforeAll
        public void init() {
            init(true, false);
        }
    }


    protected void init(boolean outerException, boolean innerException) {
        this.outerException = outerException;
        this.innerException = innerException;
    }


    /**
     * The type Nested test.
     */
    @SpringBootTest(classes = JpaApplication.class)
    @TestPropertySource(properties = "spring.config.location=classpath:jpa/jpa-hakari.yml")
    @Commit
    @Import(NestedTest.TransactionManagerConfig.class)
    public static class NestedTest {

        @Resource
        protected SampleTransactionService sampleTransactionService;

        /**
         * 外层无事务,内层NESTED,内层抛出异常
         * 结果: 外层不回滚,内层回滚
         */
        @Test
        public void testOuterNoAndInnerNestedAtInnerThrowException() {
            Assertions.assertThatCode(() -> sampleTransactionService.outerNoAndInnerNested(false, true, false))
                    .hasMessage(EXCEPT_EXCEPTION);
        }

        /**
         * 外层无事务,内层NESTED,外层抛出异常
         * 结果: 外层不回滚,内层不回滚
         */
        @Test
        public void testOuterNoAndInnerNestedAtOuterThrowException() {
            Assertions.assertThatCode(() -> sampleTransactionService.outerNoAndInnerNested(true, false, false))
                    .hasMessage(EXCEPT_EXCEPTION);
        }

        /**
         * 外层有事务,内层NESTED,内层抛出异常
         * 结果: 外层回滚,内层回滚
         */
        @Test
        public void testOuterHasAndInnerNestedAtInnerThrowException() {
            Assertions.assertThatCode(() -> sampleTransactionService.outerHasAndInnerNested(false, true, false))
                    .hasMessage(EXCEPT_EXCEPTION);
        }

        /**
         * 外层有事务,内层NESTED,外层抛出异常
         * 结果: 外层回滚,内层回滚
         */
        @Test
        public void testOuterHasAndInnerNestedAtOuterThrowException() {
            Assertions.assertThatCode(() -> sampleTransactionService.outerHasAndInnerNested(true, false, false))
                    .hasMessage(EXCEPT_EXCEPTION);
        }

        /**
         * 外层有事务,内层NESTED,内层抛出异常
         * 结果: 外层不回滚,内层回滚(虽然事务结果返回提交成功,实际上是回滚)
         */
        @Test
        public void testOuterHasAndInnerNestedAtInnerThrowExceptionAndCatchException() {
            sampleTransactionService.outerHasAndInnerNested(false, true, true);
        }

        /**
         * The type Transaction manager config.
         */
        @Configuration
        public static class TransactionManagerConfig {
            /**
             * hibernate不支持NESTED事务,因此jpa也不能使用NESTED事务
             * 使用jdbc的事务,jpa无法使用此事务
             */
            @Bean
            public DataSourceTransactionManager transactionManager(DataSource dataSource) {
                return new DataSourceTransactionManager(dataSource);
            }
        }
    }

}
