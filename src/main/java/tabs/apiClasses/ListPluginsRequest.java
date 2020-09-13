package tabs.apiClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * AUTO GENERATED DO NOT EDIT!
 * Generated from json schema.
 */


/**
 * listRequest
 * <p>
 */
public class ListPluginsRequest {

    @SerializedName("search")
    @Expose
    private String search;
    @SerializedName("userId")
    @Expose
    private Integer userId;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}