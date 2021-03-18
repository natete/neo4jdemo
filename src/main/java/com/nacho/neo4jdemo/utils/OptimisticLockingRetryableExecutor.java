package com.nacho.neo4jdemo.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;


@Slf4j
public class OptimisticLockingRetryableExecutor<T> {

    @Nonnull
    private OptimisticLockingRetryableAction<T> action;

    @Nonnull
    private OptimisticLockingStopCriteria stopCriteria;

    @Getter
    private int retries;

    @Getter
    private boolean logStacktrace;
    
    public OptimisticLockingRetryableExecutor(
        @Nonnull OptimisticLockingRetryableAction<T> action, int retries) {
        this(action, retries, false);
    }

    public OptimisticLockingRetryableExecutor(
            @Nonnull OptimisticLockingRetryableAction<T> action, int retries, boolean logStacktrace) {
        this(action, retries, new NoStopCriteria(), logStacktrace);
    }

    public OptimisticLockingRetryableExecutor(
            @Nonnull OptimisticLockingRetryableAction<T> action,
            int retries,
            @Nonnull OptimisticLockingStopCriteria stopCriteria,
            boolean logStacktrace) {
        Assert.isTrue(retries >= 1, "retries must be >= 1");
        this.action = action;
        this.retries = retries;
        this.stopCriteria = stopCriteria;
        this.logStacktrace = logStacktrace;
    }

    public T execute() {
        RuntimeException lastException;

        do {
            try {
                log.trace("Trying to apply a retryable action");
                return action.apply();
            } catch (OptimisticLockingFailureException e) {
                logOptimisticLocking(e);
                lastException = e;
            } catch (RuntimeException e) {
                if (!stopCriteria.treatAsOptimisticLock(e)) {
                    throw e;
                }

                lastException = e;
                logOptimisticLocking(e);
            }
            --retries;
        } while (stopCriteria.continueTrying() && retries > 0);

        log.warn("OptimisticLockingFailure, retries number reached 0");
        throw lastException;
    }

    private void logOptimisticLocking(@Nonnull Exception e) {
        if (logStacktrace) {
            log.warn("OptimisticLockingFailure.", e);
        } else {
            log.warn("OptimisticLockingFailure detected. This executor was configured not to print the stacktrace. {}: {}", e.getClass(), e.getMessage());
        }

    }

    public interface OptimisticLockingRetryableAction<T> {
        T apply();
    }

    public interface OptimisticLockingStopCriteria {
        boolean continueTrying();
        boolean treatAsOptimisticLock(@Nonnull RuntimeException e);
    }

    public static class NoStopCriteria implements OptimisticLockingStopCriteria {

        @Override
        public boolean continueTrying() {
            return true;
        }

        @Override
        public boolean treatAsOptimisticLock(@Nonnull RuntimeException e) {
            return false;
        }
    }
}
