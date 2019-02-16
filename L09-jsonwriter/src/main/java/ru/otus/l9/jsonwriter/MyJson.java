package ru.otus.l9.jsonwriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import ru.otus.l4.framework.mytestframework.reflaction.ReflectionHelper;

import javax.json.*;
import javax.json.stream.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

import static ru.otus.l4.framework.mytestframework.reflaction.ReflectionHelper.getFieldValue;

public class MyJson {


    public <T> T fromJson(String json, Class<T> classInputeObject) {
        T objectForTest = null;
        JSONObject jsonObject = (JSONObject) JSONValue.parse( json );
        if (jsonObject.keySet().contains( classInputeObject.getName() )) {
            try {
                var parametrsConstructors = classInputeObject.getDeclaredConstructor().getParameters();
                int countArgs =  classInputeObject.getDeclaredConstructor().getParameters().length;
                Object[] args = new Object[countArgs];
                for (int i =0; i< countArgs; i++) {
                    var value= jsonObject.get( parametrsConstructors[i].getName());
                    args[i]= value;
                }
                objectForTest = ReflectionHelper.instantiate( classInputeObject,args );

            } catch ( NoSuchMethodException  e) {
                e.printStackTrace();
            }
        }
        return objectForTest;
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
        var t =inputObject.getClass().getFields();
        if (inputObject.getClass().getFields().length > 0) {
            objectCollector = reverseCurentClass( inputObject, objectCollector, inputObjectName );
        } else objectCollector.put( objectCollector.getClass().getSimpleName(), inputObject );
        return objectCollector;

    }

    private JSONObject reverseCurentClass(Object inputObject, JSONObject jObject, String nameObject) {
        if (inputObject.getClass().getFields().length > 0 && checkPrimitiveWarper(inputObject)) {
            JSONArray jArray = new JSONArray();
            for (int i = 0; i < inputObject.getClass().getFields().length; i++) {
                JSONObject jObjectField = new JSONObject();
                String nameField = inputObject.getClass().getFields()[i].getName();
                var field = inputObject.getClass().getFields()[i];
               var name = field.getName();
               jObjectField = reverseCurentClass( field, jObjectField, nameField );
               jArray.add( jObjectField );
            }
            jObject.put( nameObject, jArray );

        } else {

            jObject.put( nameObject, getFieldValue( inputObject, nameObject ) );

        }

        return jObject;
    }

    private boolean checkPrimitiveWarper(Object inputObject){
        boolean flag = false;

        if(  inputObject.getClass().getComponentType().isPrimitive()){
            flag =true;
        }
        return flag;
    }

    private static JsonStructure read() throws FileNotFoundException {
        JsonReader reader = Json.createReader( new FileReader( "jsondata.txt" ) );
        return reader.read();
    }

    private static void parseJsonString( String JSON) {

        JsonParser parser = Json.createParser(new StringReader(JSON));

        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case START_ARRAY:
                case END_ARRAY:
                case START_OBJECT:
                case END_OBJECT:
                case VALUE_FALSE:
                case VALUE_NULL:
                case VALUE_TRUE:
                    System.out.println(event.toString());
                    break;
                case KEY_NAME:
                    System.out.print(event.toString() + " " + parser.getString() + " - ");
                    break;
                case VALUE_STRING:
                case VALUE_NUMBER:
                    System.out.println(event.toString() + " " + parser.getString());
                    break;
            }
        }
    }

}
