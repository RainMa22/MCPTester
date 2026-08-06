package me.rainma22.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class InputSchema {

    private String type = "object";
    private Map<String, ArgumentProperty> properties = new HashMap<>();
    private boolean additionalProperties = true;

    public Map<String, ArgumentProperty> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, ArgumentProperty> properties) {
        this.properties = properties;
    }

    // public void setProperties(JSONObject properties) {
    // for (var key : properties.toMap().keySet()) {
    // this.properties.put(key,
    // properties.getJSONObject(key).fromJson(ArgumentProperty.class));
    // }
    // }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(boolean additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public String toString() {
        return "(" +
                properties.entrySet()
                        .stream()
                        .map((e) -> {
                            String argString = String.join(" ", e.getValue().getType(), e.getKey());
                            return argString;
                        })
                        .collect(() -> new StringJoiner(", "),
                                (acc, cur) -> acc.add(cur),
                                (a, b) -> a.add(b.toString()))
                        .toString()
                +")";
    }

}