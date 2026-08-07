package me.rainma22.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class PromptInfo {
    private String name = null;
    private String title = null;
    private List<PromptArgument> arguments = new ArrayList<>();
    private JSONArray icons = new JSONArray();

    public String toString() {
        return name + "("
                + String.join(", ", arguments.stream()
                        .map(x -> x.toString()).toArray(String[]::new))
                + ")";
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

    public List<PromptArgument> getArguments() {
        return arguments;
    }

    public void setArguments(List<PromptArgument> arguments) {
        this.arguments = arguments;
    }

    public JSONArray getIcons() {
        return icons;
    }

    public void setIcons(JSONArray icons) {
        this.icons = icons;
    }

}
