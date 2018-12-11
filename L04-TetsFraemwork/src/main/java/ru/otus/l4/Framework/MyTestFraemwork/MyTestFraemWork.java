package ru.otus.l4.Framework.MyTestFraemwork;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyTestFraemWork {

    Class<?> clazz;

    public void Run(String pathClass) throws ClassNotFoundException {

        clazz = Class.forName(pathClass);

        CallAllMetodsWithAnnotation("Before");
        CallAllMetodsWithAnnotation("Test");
        CallAllMetodsWithAnnotation("After");



    }

    private  void CallAllMetodsWithAnnotation(String nameAnnotation){
        List<Method> listMethodsAnnotation = GetListMethodsAnnotation(nameAnnotation);
        for (Method m : listMethodsAnnotation) {
            var clazzForCallMethod = HelperOtus.instantiate(clazz);
            HelperOtus.callMethod(clazzForCallMethod,m.getName());

        }
    }


    private List<Method> GetListMethodsAnnotation(String nameAnnotation) {

        List<Method> listMethodsAnnotation = new ArrayList<>();

        Method[] classMethods = clazz.getMethods();
        for (Method method : classMethods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.toString() == nameAnnotation) {
                    listMethodsAnnotation.add(method);
                }
            }
        }
        return listMethodsAnnotation;
    }


}
