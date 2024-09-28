package com.example.linkshorten.models;

import com.example.linkshorten.contraint.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShortenURLTDO {
    @Nullable
    private String id;
    @Nullable
    @PasswordMinTenCharacter
    @PasswordAtLeastOneLowercase
    @PasswordAtLeastTwoUppercase
    @PasswordAtLeastThreeNumber
    @PasswordAtLeastFourSpecialCharacter
    private String password;
    @NotBlank
    @Size(min = 5, max = 20, message = "{com.example.linkshorten.NameLengthConstraint.message}" )
    private String name;
    @NotNull
    @UrlIsCorrect
    @UrlIsUnique
    @UrlIsHTTPS
    private String targetUrl;
    @Nullable
    @UrlIsUnique

    private String redirectUrl;
    @Nullable
    private int visits;

    public ShortenURLTDO(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "ShortenURLTDO{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", targetUrl='" + targetUrl + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", visits=" + visits +
                '}';
    }
}
