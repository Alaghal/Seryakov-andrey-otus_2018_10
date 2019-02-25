package ru.otus.l10.orm.reflection;

import ru.otus.l10.orm.enums.TypePrimitibeFields;
import ru.otus.l10.orm.interfaces.ParserObjects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Logger;

import static ru.otus.l4.framework.mytestframework.reflaction.ReflectionHelper.getFieldValue;
import static ru.otus.l4.framework.mytestframework.reflaction.ReflectionHelper.setFieldValue;

public class MyParser implements ParserObjects {


    private Map<String, Object> reverseCurentClass(Object inputObject, Map<String, Object> mapOject, String nameObject) {
        if (inputObject.getClass().getDeclaredFields().length > 0) {
            List listField = new ArrayList();
            for (int i = 0; i < inputObject.getClass().getDeclaredFields().length; i++) {
                Map<String, Object> mapObjectField = new HashMap<>();
                String nameField = inputObject.getClass().getDeclaredFields()[i].getName();
                var field = inputObject.getClass().getDeclaredFields()[i];
                if (field.getType().isPrimitive() || (field.getType() == String.class)) {
                    mapObjectField.put( nameField, getFieldValue( inputObject, nameField ) );
                    listField.add( mapObjectField );
                } else if (field.getType() == List.class) {
                    Object list = getFieldValue( inputObject, field.getName() );
                    mapObjectField = reversIterableObject( (List<Object>) list, field.getName() );
                    listField.add( mapObjectField );
                } else if (field.getType().isArray()) {
                    Object array = getFieldValue( inputObject, field.getName() );
                    mapObjectField = reversIterableObject( Arrays.asList( convertObjectToArray( array ) ), field.getName() );
                    listField.add( mapObjectField );
                } else
                    mapObjectField = reverseCurentClass( field, mapObjectField, nameField );

            }
            mapOject.put( nameObject, listField );

        } else {

            mapOject.put( nameObject, getFieldValue( inputObject, nameObject ) );
        }
        return mapOject;
    }

    private Map<String, Object> reversIterableObject(List inputIterableObject, String nameKey) {
        List<Object> list = new ArrayList<>();
        Map<String, Object> mapFieldList = new HashMap<>();
        for (var value : inputIterableObject) {
            Map<String, Object> elemtList = reverseObjectForMap( value );
            list.add( elemtList );
        }
        mapFieldList.put( nameKey, list );
        return mapFieldList;
    }


    private Object[] convertObjectToArray(Object inputObject) {

        var lenghtArray = Array.getLength( inputObject );
        Object[] array = new Object[lenghtArray];
        for (int i = 0; i < lenghtArray; i++) {
            array[i] = Array.get( inputObject, i );
        }
        return array;
    }

    private boolean checkObjectHaveFields(Class<?> clazz) {
        if (clazz.getDeclaredFields().length > 0) {
            return true;
        } else
            return false;
    }

    private boolean IsWrapperPrimitive(Class<?> clazz) {
        if (clazz == Integer.class
                && clazz == String.class
                && clazz == Double.class
                && clazz == Boolean.class
                && clazz == Byte.class) {
            return true;
        } else
            return false;
    }

    private boolean IsNumber(Class clazz) {
        if (clazz.isPrimitive()) {
            if (clazz == int.class || clazz == float.class || clazz == double.class || clazz == byte.class) {
                return true;
            }
        } else if (clazz == Integer.class || clazz == Float.class || clazz == Double.class || clazz == Byte.class) {
            return true;
        }
        return false;
    }

    private boolean IsBooleanTrue(Field inputField) {
        if (inputField.getType() == Boolean.class || inputField.getType() == boolean.class) {
            Boolean valueField = (boolean) getFieldValue( inputField, inputField.getName() );
            if (valueField) {
                return true;
            }
        }
        return false;
    }

    private boolean IsBooleanFalse(Field inputField) {
        if (inputField.getType() == Boolean.class || inputField.getType() == boolean.class) {
            Boolean valueField = (boolean) getFieldValue( inputField, inputField.getName() );
            if (!valueField) {
                return true;
            }
        }
        return false;
    }


    public Map<String, TypePrimitibeFields> getTypeFieldOfObject(Class clazz) {
        Map<String, TypePrimitibeFields> mapTypeField = new HashMap<>();
        var fields = clazz.getDeclaredFields();
        for (var field : fields) {
            if (IsNumber( field.getType() )) {
                mapTypeField.put( field.getName(), TypePrimitibeFields.VALUE_NUMBER );
            } else if (field.getType() == String.class) {
                mapTypeField.put( field.getName(), TypePrimitibeFields.VALUE_STRING );
            } else if (IsBooleanTrue( field )){
                mapTypeField.put( field.getName(), TypePrimitibeFields.VALUE_TRUE );
            } else if (IsBooleanFalse( field )){
                mapTypeField.put( field.getName(), TypePrimitibeFields.VALUE_FALSE );
            }
        }
        return mapTypeField;
    }

    @Override
    public Map<String, Object> reverseObjectForMap(Object inputObject) {
        Map<String, Object> mapObjectField = new HashMap<>();
        if (inputObject != null) {
            var inputObjectClass = inputObject.getClass();
            String inputObjectName = inputObjectClass.getSimpleName();

            if (checkObjectHaveFields( inputObjectClass ) && !IsWrapperPrimitive( inputObjectClass )) {
                mapObjectField = reverseCurentClass( inputObject, mapObjectField, inputObjectName );
            } else
                mapObjectField.put( inputObjectName, inputObject );
        }

        return mapObjectField;
    }

    public <T> T CreateObjectOfCurrentType(Class<T> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        var constructors = clazz.getConstructors();
        int countArgs = constructors[0].getParameterCount();
        Object[] args = new Object[countArgs];
        for (int i = 0; i < countArgs; i++) {
            var value = constructors[0].getParameters()[i];
            args[i] = fillPrimitiveArg( value.getType() );
        }
        T object = (T) constructors[0].newInstance( args );

        return object;

    }

    private <R> R fillPrimitiveArg(Class<R> argType) {
        Object object = new Object();
        String initialValue = "1";
        if (argType == int.class || argType == Integer.class) {
            object = 0;
        }else if(argType==long.class || argType == Logger.class ){
            object = Long.valueOf( initialValue);
        } else if (argType == String.class) {
            object = initialValue;
        } else if (argType == double.class || argType == Double.class) {
            object = Double.valueOf( initialValue );
        } else if (argType == boolean.class || argType == Boolean.class) {
            object = true;
        } else if (argType == byte.class || argType == Byte.class) {
            object = Byte.valueOf( initialValue );
        }
        return (R) object;
    }

    @Override
    public <T> T getValueOfAnnotationName(Class annotationType, Object inputObject) {
        Field[] fields = inputObject.getClass().getDeclaredFields();
        Object returnedValue = null;
        for (var field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (var annotantion : annotations) {
                if (annotantion.annotationType().equals( annotationType )) {
                    returnedValue = getFieldValue( inputObject, field.getName() );
                }

            }
        }

        return (T) returnedValue;
    }
}
