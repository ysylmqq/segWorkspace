package cc.chinagps.seat.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

public class SimpleTraceLogger {
	
	private final Log LOGGER = LogFactory.getLog(getClass());
	
	public Object profile(ProceedingJoinPoint call) throws Throwable {
		String methodName = getMethodName(call);
		StopWatch stopWatch = new StopWatch(methodName);
		boolean infoEnable = LOGGER.isInfoEnabled();
		if (infoEnable) {
			LOGGER.info("Entering " + methodName);
		}
		try {
			stopWatch.start(methodName);
			Object rval = call.proceed();
			if (infoEnable) {
				LOGGER.info("Exiting " + methodName);
			}
			return rval;
		} catch (Throwable ex) {
			LOGGER.warn("Exception thrown in " + methodName, ex);
			throw ex;
		} finally {
			stopWatch.stop();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(stopWatch.prettyPrint());
			}
		}
	}

	private String getMethodName(ProceedingJoinPoint call) {
		return call.getSignature().getDeclaringTypeName() + "." +
				call.getSignature().getName();
	}
}
