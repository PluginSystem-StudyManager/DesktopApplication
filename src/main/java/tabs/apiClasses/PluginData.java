package tabs.apiClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
 * AUTO GENERATED DO NOT EDIT!
 * Generated from json schema.
 */

/**
 * pluginData
 * <p>
 */
public class PluginData {

    /**
     * (Required)
     */
    @SerializedName("name")
    @Expose
    private String name;
    /**
     * (Required)
     */
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    /**
     * (Required)
     */
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    /**
     * (Required)
     */
    @SerializedName("userIds")
    @Expose
    private List<Integer> userIds = null;

    /**
     * (Required)
     */
    public String getName() {
        return name;
    }

    /**
     * (Required)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * (Required)
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * (Required)
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * (Required)
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * (Required)
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * (Required)
     */
    public List<Integer> getUserIds() {
        return userIds;
    }

    /**
     * (Required)
     */
    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

}
