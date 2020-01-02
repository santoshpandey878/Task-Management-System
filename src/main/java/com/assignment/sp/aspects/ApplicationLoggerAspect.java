package com.assignment.sp.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Spring aspect class to print application logs.
 */
@Aspect
@Component
public class ApplicationLoggerAspect {

	/**
	 * Advice, used to print log when client interact through application APIs.
	 * Also print the time taken by an API.
	 * Although, I have applied advice only for APIs, but we can also apply for service layer or dao as well.
	 */
	@Around(" execution(* com.assignment.sp.api.*.*(..))")
	public Object logMethod( ProceedingJoinPoint joinPoint)
			throws Throwable {
		Class<?> targetClass = joinPoint.getTarget().getClass();
		Logger logger = LoggerFactory.getLogger(targetClass);
		try {
			String className = targetClass.getSimpleName();
			logger.info(getPreMessage(joinPoint, className));
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			Object retVal = joinPoint.proceed();
			stopWatch.stop();
			logger.info(getPostMessage(joinPoint, className, stopWatch.getTotalTimeMillis()));
			return retVal;
		} catch(Throwable ex) {
			logger.error(getErrorMessage(ex), ex);
			throw ex;
		}
	}

	/**
	 * Method to print logs before execution of method
	 * @param joinPoint
	 * @param className
	 * @return
	 */
	private static String getPreMessage( JoinPoint joinPoint,  String className) {
		StringBuilder builder = new StringBuilder()
				.append("Entered in ").append(className).append(".")
				.append(joinPoint.getSignature().getName())
				.append("(");
		appendTo(builder, joinPoint);
		return builder
				.append(")")
				.toString();
	}

	/**
	 * Method to print logs after execution of method
	 * @param joinPoint
	 * @param className
	 * @return
	 */
	private static String getPostMessage( JoinPoint joinPoint,  String className,  long millis) {
		return new StringBuilder()
				.append("Exit from ").append(className).append(".")
				.append(joinPoint.getSignature().getName())
				.append("(..); Execution time: ")
				.append(millis)
				.append(" ms;")
				.toString();
	}

	/**
	 * Method to get exception error message
	 * @param ex
	 * @return
	 */
	private static String getErrorMessage( Throwable ex) {
		return ex.getMessage();
	}

	private static void appendTo( StringBuilder builder,  JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		for ( int i = 0; i < args.length; i++ ) {
			if ( i != 0 ) {
				builder.append(", ");
			}
			builder.append(args[i]);
		}
	}
}
