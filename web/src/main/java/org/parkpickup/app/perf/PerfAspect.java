package org.parkpickup.app.perf;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

public class PerfAspect {
	@Autowired
	private PerfMetrics perfMetrics;

//	@Around("execution(* org.parkpickup.*.*(..))")
	public Object logMetric(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		this.perfMetrics.add(joinPoint.getSignature().getName(), System.currentTimeMillis() - startTime);

		return result;
	}
}
