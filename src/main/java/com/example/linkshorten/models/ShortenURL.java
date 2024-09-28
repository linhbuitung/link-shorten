package com.example.linkshorten.models;

import com.example.linkshorten.contraint.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

@Entity
public class ShortenURL {

    @Id
    @NotBlank
    @Column(name = "Id")
    @Size(min = 5, max = 20, message = "{com.example.linkshorten.NameLengthConstraint.message}")
    private String urlId;


    @PasswordMinTenCharacter
    @PasswordAtLeastOneLowercase
    @PasswordAtLeastTwoUppercase
    @PasswordAtLeastThreeNumber
    @PasswordAtLeastFourSpecialCharacter
    @Column(name = "Password")

    private String password;

    @Column(name = "Name")
    private String name;

    //make sure url is unique and is an actual url
    @URL
    @UrlIsCorrect
    @UrlIsHTTPS
    @Column(name = "TargetUrl", unique = true)

    private String targetUrl;
    @URL
    @Column(name = "RedirectUrl", unique = true)

    private String redirectUrl;

    @Column(name = "Visits")
    private Integer visits;

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
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

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }
}
