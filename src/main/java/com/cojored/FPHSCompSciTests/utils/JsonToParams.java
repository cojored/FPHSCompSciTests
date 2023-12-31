package com.cojored.FPHSCompSciTests.utils;

import com.cojored.FPHSCompSciTests.params.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonToParams {
    public static Parameter[] parse(JSONArray obj) {
        List<Parameter> params = new ArrayList<>();
        for (Object o : obj)
            params.add(parseParam((JSONArray) o));

        return params.toArray(new Parameter[0]);
    }

    public static Parameter parseParam(JSONArray obj) {
        List<Value<?>> params = new ArrayList<>();
        String methodName = null;
        String paramTypes = null;
        for (Object ob : obj) {
            JSONObject o = (JSONObject) ob;
            if (o.has("methodName")) methodName = (String) o.get("methodName");
            if (o.has("paramTypes") && !o.has("construct")) paramTypes = (String) o.get("paramTypes");
            if (o.has("construct"))
                params.add(new Constructor(parseParam((JSONArray) o.get("params")).setParamType((String) o.get("paramTypes")), (String) o.get("construct")));
            if (o.has("static")) params.add(new Static<>(o.get("static")));
            if (o.has("range")) {
                JSONObject r = (JSONObject) o.get("range");
                params.add(new Range((int) r.get("min"), (int) r.get("max"), (boolean) r.get("inclusive")));
            }
            if (o.has("random")) {
                JSONArray arr = (JSONArray) o.get("random");
                String[] stringArr = new String[arr.length()];
                for (int i = 0; i < arr.length(); i++)
                    stringArr[i] = arr.getString(i);
                params.add(new Random(stringArr));
            }
        }
        return new Parameter(params.toArray(new Value[0]), methodName, paramTypes);
    }
}
