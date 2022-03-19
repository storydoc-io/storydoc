package io.storydoc.server.infra;

import io.storydoc.stitch.CodeExecutionTracer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import java.util.UUID;

//@Aspect
//@Component
public class StitchAspect {

    private CodeExecutionTracer tracer = StitchFactory.INSTANCE.getCodeExecutionTracer();

    @Pointcut("within(@org.springframework.stereotype.Service *)"
            + " || within(@org.springframework.stereotype.Component *)")
    public void springBeanPointcut() {
    }

    @Pointcut("within(io.storydoc.server..*)")
    public void serverPackagePointcut() {
    }


    @Around("springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String cid = UUID.randomUUID().toString();
        if (tracer.isActive()) {
            enterJoinPoint(joinPoint, cid);
        }
        try {
            Object result = joinPoint.proceed();
            if (tracer.isActive()) {
                exitJoinPoint(joinPoint, cid, result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            throw e;
        } finally {
        }
    }

    private void enterJoinPoint(ProceedingJoinPoint joinPoint, String cid) {
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        int lineNr = 0;
            lineNr = getLineNr(typeName, methodName);
        tracer.enter(typeName, methodName, lineNr);
    }

    private int getLineNr(String className, String methodName) {
        try {
            throw new Exception();
        } catch (Exception e) {
            for(StackTraceElement stackTraceElement: e.getStackTrace()) {
                if (stackTraceElement.getClassName().equals(className) && stackTraceElement.getMethodName().equals(methodName)) {
                    return stackTraceElement.getLineNumber();
                }
            }
        }
        return 0;
    }

    private void exitJoinPoint(ProceedingJoinPoint joinPoint, String cid, Object result) {
        //log.info("*** exitJoinPoint ***");
    }

}
