package me.rainma22.utils;

import org.json.JSONArray;

import me.rainma22.constants.MimeTypes;

public class ResourceInfo {
    private String uri = null;
    private String name = null;
    private String title = "";
    private String description = "";
    private String mimeType = MimeTypes.PLAINTEXT_MIMETYPE;
    private JSONArray icons = new JSONArray();

    public String toString() {
        return mimeType + ": " + name + String.join(uri, "(", ")");
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public JSONArray getIcons() {
        return icons;
    }

    public void setIcons(JSONArray icons) {
        this.icons = icons;
    }
}
