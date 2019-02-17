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
               // objectForConvert = classInputeObject.getConstructor().newInstance( args );
                objectForConvert = ReflectionHelper.instantiate( classInputeObject, args );
                objectForConvert = (T) setValueToObjectOfJson( json, objectForConvert );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return objectForConvert;
    }

    private Object fillPrimitiveArg(Class argType) {
        Object object = new Object();
        String initialValue = "0";

        if (argType.getTypeName()== "java.lang.Integer") {
            object = Integer.valueOf( initialValue );
        } else if (argType.getTypeName() == "java.lang.String") {
            object = initialValue;
        } else if (argType.getTypeName() == "java.lang.Double") {
            object = Double.valueOf( initialValue );
        }
        return object;
    }


    public JSONObject forJsonObject(List inputList) {
        JSONArray array = new JSONArray();
        JSONObject jObject = new JSONObject();
        for (var value : inputList) {
            JSONObject jsonVslue = new JSONObject();
            jsonVslue = forJsonObject( value );
            array.add( jsonVslue );
        }
        jObject.put( "List", array );
        return jObject;
    }

    public JSONObject forJsonObject(Object[] inputArray) {
        JSONArray array = new JSONArray();
        JSONObject jObject = new JSONObject();
        for (var value : inputArray) {
            JSONObject jsonVslue = new JSONObject();
            jsonVslue = forJsonObject( value );
            array.add( jsonVslue );
        }
        jObject.put( "Array", array );
        return jObject;
    }

    public JSONObject forJsonObject(Object inputObject) {
        JSONObject objectCollector = new JSONObject();
        String inputObjectName = inputObject.getClass().getSimpleName();
        var t = inputObject.getClass().getDeclaredFields();
        if (inputObject.getClass().getDeclaredFields().length > 0) {
            objectCollector = reverseCurentClass( inputObject, objectCollector, inputObjectName );
        } else objectCollector.put( objectCollector.getClass().getSimpleName(), inputObject );
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

    private boolean checkPrimitiveWarper(Object inputObject) {
        boolean flag = true;

        if (Integer.TYPE.isInstance( inputObject ) && String.class.isInstance( inputObject )) {
            flag = false;
        }
        return flag;
    }

    private static JsonStructure read() throws FileNotFoundException {
        JsonReader reader = Json.createReader( new FileReader( "jsondata.txt" ) );
        return reader.read();
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
                    setFieldValue( inputeObject, keyName, parser.getLong() );
                    break;
            }
        }
        return inputeObject;
    }

}
