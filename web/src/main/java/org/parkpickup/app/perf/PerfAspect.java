package org.parkpickup.app.perf;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@ConditionalOnProperty("metrics.perf.enabled")
public class PerfAspect {
    @Autowired
    private PerfMetrics perfMetrics;

    @Around("execution(* org.parkpickup..*(..)) && !target(org.parkpickup.app.perf.PerfMetrics)")
    public Object logMetric(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        this.perfMetrics.add(joinPoint.getSignature().toShortString(), System.currentTimeMillis() - startTime);
        return result;
    }
}
