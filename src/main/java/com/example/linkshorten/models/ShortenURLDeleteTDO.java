package com.example.linkshorten.models;

import com.example.linkshorten.contraint.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShortenURLDeleteTDO {
    @Nullable
    private String id;

    @NotBlank
    @PasswordMinTenCharacter
    @PasswordAtLeastOneLowercase
    @PasswordAtLeastTwoUppercase
    @PasswordAtLeastThreeNumber
    @PasswordAtLeastFourSpecialCharacter
    private String password;



    public ShortenURLDeleteTDO(){

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






}
