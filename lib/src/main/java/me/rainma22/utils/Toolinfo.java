package me.rainma22.utils;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class Toolinfo {
    private String name = null;
    private String title = null;
    private InputSchema inputSchema = new InputSchema();
    private JSONObject outputSchema = null;
    private String annotation = null;
    private JSONObject execution = null;
    private Set<String> required = Set.of();

    public Set<String> getRequired() {
        return required;
    }

    public void setRequired(Set<String> required) {
        this.required = required;
    }

    public Toolinfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JSONObject getOutputSchema() {
        return outputSchema;
    }

    public void setOutputSchema(JSONObject outputSchema) {
        this.outputSchema = outputSchema;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public JSONObject getExecution() {
        return execution;
    }

    public void setExecution(JSONObject execution) {
        this.execution = execution;
    }

    public String toString() {
        return this.getName() + this.inputSchema.toString() + ", required: " + new JSONArray(required).toString();
    }

    public InputSchema getInputSchema() {
        return inputSchema;
    }

    public void setInputSchema(InputSchema inputSchema) {
        this.inputSchema = inputSchema;
    }
}
