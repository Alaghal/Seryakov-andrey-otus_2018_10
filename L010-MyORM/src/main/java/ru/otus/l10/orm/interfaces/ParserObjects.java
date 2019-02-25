package ru.otus.l10.orm.interfaces;

import java.util.Map;

public interface ParserObjects {
    public Map<String,Object> reverseObjectForMap(Object inputObject);
    public <T> T getValueOfAnnotationName(Class annotationType, Object inputObject);

}
