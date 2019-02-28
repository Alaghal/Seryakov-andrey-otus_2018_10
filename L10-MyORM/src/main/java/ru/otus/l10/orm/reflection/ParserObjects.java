package ru.otus.l10.orm.reflection;

import java.util.Map;

public interface ParserObjects {
    Map<String, Object> reverseObjectForMap(Object inputObject);

    <T> T getValueOfAnnotationName(Class annotationType, Object inputObject);

}
