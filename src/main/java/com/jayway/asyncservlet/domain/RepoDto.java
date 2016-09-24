package com.jayway.asyncservlet.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;

public class RepoDto {

    @JsonProperty("name")
    private final String name;
    @JsonProperty("url")
    private final URL url;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("owner")
    private final String owner;
    @JsonProperty("owner_url")
    private final URL ownerUrl;
    @JsonProperty("owner_avatar")
    private final URL ownerAvatar;

    public RepoDto(String name, URL url, String description, String owner, URL ownerUrl, URL ownerAvatar) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.owner = owner;
        this.ownerUrl = ownerUrl;
        this.ownerAvatar = ownerAvatar;
    }
    
    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }

    public URL getOwnerUrl() {
        return ownerUrl;
    }

    public URL getOwnerAvatar() {
        return ownerAvatar;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof RepoDto))
            return false;

        RepoDto repoDto = (RepoDto) o;

        if (!name.equals(repoDto.name))
            return false;
        if (!url.equals(repoDto.url))
            return false;
        if (!description.equals(repoDto.description))
            return false;
        if (!owner.equals(repoDto.owner))
            return false;
        if (!ownerUrl.equals(repoDto.ownerUrl))
            return false;
        return ownerAvatar.equals(repoDto.ownerAvatar);

    }

    @Override
    public int hashCode()
    {
        int result = name.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + owner.hashCode();
        result = 31 * result + ownerUrl.hashCode();
        result = 31 * result + ownerAvatar.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RepoDto{" + "name='" + name + '\'' + ", url=" + url + ", description='" + description + '\'' + ", owner='" + owner + '\'' + ", ownerUrl=" + ownerUrl + ", ownerAvatar=" + ownerAvatar + '}';
    }
}
