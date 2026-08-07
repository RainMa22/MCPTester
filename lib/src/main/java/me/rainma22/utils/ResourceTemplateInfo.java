package me.rainma22.utils;

import org.json.JSONArray;

import me.rainma22.constants.MimeTypes;

public class ResourceTemplateInfo {
    private String uriTemplate = null;
    private String name = null;
    private String title = "";
    private String description = "";
    private String mimeType = MimeTypes.PLAINTEXT_MIMETYPE;
    private JSONArray icons = new JSONArray();

    public String toString() {
        return "(template) " + mimeType + ": " + name + String.join(uriTemplate, "(", ")");
    }

    public String getUriTemplate() {
        return uriTemplate;
    }

    public void setUriTemplate(String uri) {
        this.uriTemplate = uri;
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
