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
            //enterJoinPoint(joinPoint, cid);
        try {
            Object result = joinPoint.proceed();
               // exitJoinPoint(joinPoint, cid, result);
            return result;
        } catch (IllegalArgumentException e) {
            throw e;
        } finally {
        }
    }

    private void enterJoinPoint(ProceedingJoinPoint joinPoint, String cid) {
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        tracer.methodEnter(cid, typeName, methodName);
    }

    private void exitJoinPoint(ProceedingJoinPoint joinPoint, String cid, Object result) {
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        tracer.methodReturn(cid, typeName, methodName);
    }

}
