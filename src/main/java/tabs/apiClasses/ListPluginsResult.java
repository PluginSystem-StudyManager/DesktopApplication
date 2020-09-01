package tabs.apiClasses;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * AUTO GENERATED DO NOT EDIT!
 * Generated from json schema.
 */


/**
 * listResult
 * <p>
 */
public class ListPluginsResult {

    /**
     * (Required)
     */
    @SerializedName("success")
    @Expose
    private Boolean success;
    /**
     * (Required)
     */
    @SerializedName("message")
    @Expose
    private String message;
    /**
     * (Required)
     */
    @SerializedName("data")
    @Expose
    private List<PluginData> data = null;

    /**
     * (Required)
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * (Required)
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * (Required)
     */
    public String getMessage() {
        return message;
    }

    /**
     * (Required)
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * (Required)
     */
    public List<PluginData> getData() {
        return data;
    }

    /**
     * (Required)
     */
    public void setData(List<PluginData> data) {
        this.data = data;
    }

}
