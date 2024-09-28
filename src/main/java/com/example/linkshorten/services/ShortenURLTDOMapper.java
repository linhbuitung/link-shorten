package com.example.linkshorten.services;

import com.example.linkshorten.models.ShortenURL;
import com.example.linkshorten.models.ShortenURLTDO;
import com.example.linkshorten.repositories.URLRepository;
import org.springframework.stereotype.Service;

@Service
public class ShortenURLTDOMapper {

    private final URLRepository urlRepository;

    public ShortenURLTDOMapper(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }
    public ShortenURLTDO mapTDO(ShortenURL shortenURL) {
        ShortenURLTDO shortenURLTDO = new ShortenURLTDO();
        shortenURLTDO.setId(shortenURL.getUrlId());
        shortenURLTDO.setName(shortenURL.getName());
        shortenURLTDO.setTargetUrl(shortenURL.getTargetUrl());
        shortenURLTDO.setRedirectUrl(shortenURL.getRedirectUrl());
        shortenURLTDO.setVisits(shortenURL.getVisits());

        return shortenURLTDO;
    }

    public ShortenURLTDO mapTDOWithPassword(ShortenURL shortenURL) {
        ShortenURLTDO shortenURLTDO = new ShortenURLTDO();
        shortenURLTDO.setId(shortenURL.getUrlId());
        shortenURLTDO.setPassword(shortenURL.getPassword());
        shortenURLTDO.setName(shortenURL.getName());
        shortenURLTDO.setTargetUrl(shortenURL.getTargetUrl());
        shortenURLTDO.setRedirectUrl(shortenURL.getRedirectUrl());
        shortenURLTDO.setVisits(shortenURL.getVisits());

        return shortenURLTDO;
    }
    public ShortenURL map(ShortenURLTDO shortenURLTDO) {
        ShortenURL shortenURL = new ShortenURL();
        shortenURL.setUrlId(shortenURLTDO.getId());
        shortenURL.setName(shortenURLTDO.getName());
        if(shortenURLTDO.getPassword() != null){
            shortenURL.setPassword(shortenURLTDO.getPassword());
        }
        shortenURL.setTargetUrl(shortenURLTDO.getTargetUrl());
        shortenURL.setRedirectUrl(shortenURLTDO.getRedirectUrl());
        shortenURL.setVisits(shortenURLTDO.getVisits());

        return shortenURL;
    }
}
