package me.rainma22.utils;

import org.json.JSONArray;

public class ClientInfo {
    private String name = "testClient";
    private String title = "Client";
    private String version = "0.0.1";
    private String description = "an MCP client";
    private JSONArray icons = new JSONArray();

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JSONArray getIcons() {
        return icons;
    }

    public void setIcons(JSONArray icons) {
        this.icons = icons;
    }

}