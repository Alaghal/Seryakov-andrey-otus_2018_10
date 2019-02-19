package ru.otus.l9.jsonwriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import ru.otus.l4.framework.mytestframework.reflaction.ReflectionHelper;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.stream.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ru.otus.l4.framework.mytestframework.reflaction.ReflectionHelper.getFieldValue;
import static ru.otus.l4.framework.mytestframework.reflaction.ReflectionHelper.setFieldValue;

public class MyJson {


    public <T> T fromJson(String json, Class<T> classInputeObject) {
        T objectForConvert = null;
        JSONObject jsonObject = (JSONObject) JSONValue.parse( json );
        if (jsonObject.keySet().contains( classInputeObject.getSimpleName() )) {
            try {
                var constructors = classInputeObject.getConstructors();
                int countArgs = constructors[0].getParameterCount();
                Object[] args = new Object[countArgs];
                for (int i = 0; i < countArgs; i++) {
                    var nameConstructor = constructors[0].getParameters()[i];
                    var value = constructors[0].getParameters()[i];
                    args[i] = fillPrimitiveArg(value.getType());
                }
                objectForConvert = ReflectionHelper.instantiate( classInputeObject, args );
                objectForConvert = (T) setValueToObjectOfJson( json, objectForConvert );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return objectForConvert;
    }

    private <T> T fillPrimitiveArg(Class<T> argType) {
        Object object = new Object();
        String initialValue = "0";

        if (argType == int.class && argType == Integer.class) {
            object = 0;
        } else if (argType == String.class) {
            object = initialValue;
        } else if (argType == double.class && argType == Double.class) {
            object = Double.valueOf( initialValue );
        } else if (argType == boolean.class && argType == Boolean.class){
            object = true;
        } else if (argType == byte.class && argType == Byte.class){
            object = Byte.valueOf( initialValue );
        }
        return (T) object;
    }


    public JSONObject toJsonObjectList(List inputList) {
        JSONArray array = new JSONArray();
        JSONObject jObject = new JSONObject();
        for (var value : inputList) {
            JSONObject jsonVslue = toJsonObject( value );
            array.add( jsonVslue );
        }
        jObject.put( "List", array );
        return jObject;
    }

    private JSONObject toJsonObjectArray(Object[] inputArray) {
        JSONArray array = new JSONArray();
        JSONObject jObject = new JSONObject();
        for (var value : inputArray) {
            JSONObject jsonVslue = toJsonObject( value );
            array.add( jsonVslue );
        }
        jObject.put( "Array", array );
        return jObject;
    }

    public JSONObject toJsonObject(Object inputObject) {
        JSONObject objectCollector = new JSONObject();
        if(inputObject != null) {
            var simpleName = inputObject.getClass().getSimpleName();
            if(inputObject.getClass().isArray()){
                var lenghtArray = Array.getLength( inputObject );
                Object[] array = new Object[lenghtArray];
                for(int i=0; i< lenghtArray; i++){
                   array[i] = Array.get( inputObject,i );
                }
                objectCollector =  toJsonObjectArray(array);
            } else if(Collection.class.isAssignableFrom(inputObject.getClass()) ) {                                    //   simpleName.equals("ListN"
                objectCollector = toJsonObjectList((List<Object>)inputObject);

            } else {
                String inputObjectName = inputObject.getClass().getSimpleName();
                var inputObjectClass = inputObject.getClass();
                if (inputObjectClass.getDeclaredFields().length > 0
                        && inputObjectClass != Integer.class
                        && inputObjectClass != String.class
                        && inputObjectClass != Double.class
                        && inputObjectClass != Boolean.class
                        && inputObjectClass != Byte.class
                ) {
                    objectCollector = reverseCurentClass( inputObject, objectCollector, inputObjectName );
                } else objectCollector.put( inputObjectClass.getSimpleName(), inputObject );
            }
        }
        return objectCollector;

    }

    private JSONObject reverseCurentClass(Object inputObject, JSONObject jObject, String nameObject) {
        if (inputObject.getClass().getDeclaredFields().length > 0) {
            JSONArray jArray = new JSONArray();
            for (int i = 0; i < inputObject.getClass().getDeclaredFields().length; i++) {
                JSONObject jObjectField = new JSONObject();
                String nameField = inputObject.getClass().getDeclaredFields()[i].getName();
                var field = inputObject.getClass().getDeclaredFields()[i];
                if (field.getType().isPrimitive() || (field.getType() == String.class)) {
                    jObjectField.put( nameField, getFieldValue( inputObject, nameField ) );
                    jArray.add( jObjectField );

                } else jObjectField = reverseCurentClass( field, jObjectField, nameField );

            }
            jObject.put( nameObject, jArray );

        } else {

            jObject.put( nameObject, getFieldValue( inputObject, nameObject ) );

        }

        return jObject;
    }


    private Object setValueToObjectOfJson(String JSON, Object inputeObject) {

        JsonParser parser = Json.createParser( new StringReader( JSON ) );
        String keyName = null;

        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case VALUE_FALSE:
                    setFieldValue( inputeObject, keyName, false );
                    break;
                case VALUE_NULL:
                    setFieldValue( inputeObject, keyName, null );
                    break;
                case VALUE_TRUE:
                    setFieldValue( inputeObject, keyName, true );
                    break;
                case KEY_NAME:
                    keyName = parser.getString();
                    break;
                case VALUE_STRING:
                    setFieldValue( inputeObject, keyName, parser.getString() );
                    break;
                case VALUE_NUMBER:
                    setFieldValue( inputeObject, keyName, parser.getInt() );
                    break;
            }
        }
        return inputeObject;
    }
    public static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
        try {
            return clazz.cast(o);
        } catch(ClassCastException e) {
            return null;
        }
    }
}
