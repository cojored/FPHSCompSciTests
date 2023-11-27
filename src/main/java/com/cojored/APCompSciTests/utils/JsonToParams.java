package com.cojored.APCompSciTests.utils;

import com.cojored.APCompSciTests.params.*;
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
        for (Object ob : obj) {
            JSONObject o = (JSONObject) ob;
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
        return new Parameter(params.toArray(new Value[0]));
    }
}
