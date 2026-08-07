package me.rainma22.utils;

/**
 * PromptArgument
 */
public class PromptArgument {
    private String name = null;
    private String description = "";
    private boolean required = false;

    public String toString() {
        return required ? name : "[" + name + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

}
