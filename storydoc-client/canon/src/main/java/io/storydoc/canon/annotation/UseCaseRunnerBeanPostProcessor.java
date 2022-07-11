package io.storydoc.canon.annotation;

import io.storydoc.canon.ExecutionContext;
import io.storydoc.canon.RunnerRegistry;
import io.storydoc.canon.StepDef;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Slf4j
public class UseCaseRunnerBeanPostProcessor implements BeanPostProcessor {

    private final RunnerRegistry runnerRegistry;

    public UseCaseRunnerBeanPostProcessor(RunnerRegistry runnerRegistry) {
        this.runnerRegistry = runnerRegistry;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(UseCaseRunnerClass.class)) {
            log.info("!! bean " + beanName + " has UseCaseRunnerClass annotation ");
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(UseCaseRunnerMethod.class)) {
                    log.info("processing method " + beanName);
                    runnerRegistry.register(method.getAnnotation(UseCaseRunnerMethod.class).value(), (StepDef stepDef, ExecutionContext context) -> {
                        try {
                            method.invoke(bean, stepDef, context);
                        } catch (Exception e) {
                            throw new IllegalArgumentException(e);
                        }
                        ;
                    });
                }
            }
        }
        return bean;
    }

}
