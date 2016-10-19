package com.jayway.asyncservlet.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.MalformedURLException;
import java.net.URL;

class RepoDto {

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

    public RepoDto(String name, URL url, String description, Object owner, Object ownerUrl, Object ownerAvatar) throws MalformedURLException
    {
        this.name = name;
        this.url = url;
        this.description = description;
        this.owner = (String)owner;
        this.ownerUrl = new URL((String)ownerUrl);
        this.ownerAvatar = new URL((String)ownerAvatar);
    }
    
    String getName() {
        return name;
    }

    URL getUrl() {
        return url;
    }

    String getDescription() {
        return description;
    }

    String getOwner() {
        return owner;
    }

    URL getOwnerUrl() {
        return ownerUrl;
    }

    URL getOwnerAvatar() {
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
        return ownerUrl.equals(repoDto.ownerUrl) && ownerAvatar.equals(repoDto.ownerAvatar);

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
