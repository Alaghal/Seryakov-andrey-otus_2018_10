package ru.otus.l4.framework.mytestframework;

import ru.otus.l4.framework.mytestframework.annotations.After;
import ru.otus.l4.framework.mytestframework.annotations.Before;
import ru.otus.l4.framework.mytestframework.annotations.Test;
import ru.otus.l4.framework.mytestframework.reflaction.ReflectionHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyTestFramework {

    private Class<?> clazz;

    public  MyTestFramework(Class typeClass){
        this.clazz = typeClass;
    }

    public void run() {
        callTestMetods();
    }

    private void callTestMetods(){
        List<Method> listMethodsAnnotation = getListMethodsAnnotation(Test.class);
        for (Method m : listMethodsAnnotation) {
            var objectForTest = ReflectionHelper.instantiate(clazz);
            objectForTest = callBeforeMetod(objectForTest);
            ReflectionHelper.callMethod(objectForTest, m.getName());
            callAfteMetod(objectForTest);
        }
    }

    private Object callBeforeMetod (Object testObject){
        List<Method> listMethodsAnnotation = getListMethodsAnnotation(Before.class);
        for (Method m : listMethodsAnnotation) {
            ReflectionHelper.callMethod(testObject, m.getName());
        }
        return testObject;
    }

    private void callAfteMetod(Object testOject){
        List<Method> listMethodsAnnotation = getListMethodsAnnotation(After.class);
        for (Method m : listMethodsAnnotation) {
            ReflectionHelper.callMethod(testOject, m.getName());
        }
    }

    private List<Method> getListMethodsAnnotation(Class annotationClass) {
        List<Method> listMethodsAnnotation = new ArrayList<>();
        Method[] classMethods = clazz.getMethods();
        for (Method method : classMethods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(annotationClass)) {
                    listMethodsAnnotation.add(method);
                }
            }
        }
        return listMethodsAnnotation;
    }

}
