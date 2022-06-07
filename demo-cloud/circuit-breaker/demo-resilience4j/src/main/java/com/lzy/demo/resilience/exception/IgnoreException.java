package com.lzy.demo.resilience.exception;

/**
 * 忽略的异常
 *
 * @author lzy
 * @version v1.0
 */
public class IgnoreException extends RuntimeException {

    /**
     * Instantiates a new Ignore exception.
     *
     * @param message the message
     */
    public IgnoreException(String message) {
        super(message);
    }
}
