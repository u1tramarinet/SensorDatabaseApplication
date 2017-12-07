package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.json;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by takumi on 2017/12/02.
 */

public class JsonParameters {

    public static String parametersToJson(String sensorName, int nParam){
        Gson gson = new Gson();
        List<Parameter> parameters = new ArrayList<>();
        for (int i = 0; i < nParam; i++) {
            String name = String.format(Locale.JAPAN, "%s%2d", sensorName, i);
            parameters.add(new Parameter(i, name));
        }
        return gson.toJson(parameters);
    }

    public static String parametersToJson(String[] names){
        Gson gson = new Gson();
        List<Parameter> parameters = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            String name = String.format(Locale.JAPAN, "%s", names[i]);
            parameters.add(new Parameter(i, name));
        }
        return gson.toJson(parameters);
    }

    public static List<Parameter> parametersFromJson(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Parameter>>(){}.getType();
        return gson.fromJson(json, type);
    }

    private static class Parameter {
        @SerializedName("parameter_id")
        private int id;
        @SerializedName("parameter_name")
        private String name;

        Parameter(int paramId, String paramName){
            this.id = paramId;
            this.name = paramName;
        }
    }
}
