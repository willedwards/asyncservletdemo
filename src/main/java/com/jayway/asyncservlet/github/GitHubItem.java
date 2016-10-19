package com.jayway.asyncservlet.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubItem {

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("html_url")
    private URL url;

    @JsonProperty("description")
    private String description;

    @JsonProperty("owner")
    private Map owner;

    public String fullName() {
        return fullName;
    }

    public URL getUrl() {
        return url;
    }

    public String description() {
        return description;
    }

    public Map owner() {
        return owner;
    }
}
