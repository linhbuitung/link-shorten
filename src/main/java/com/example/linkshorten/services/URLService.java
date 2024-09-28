package com.example.linkshorten.services;

import com.example.linkshorten.models.ShortenURL;
import com.example.linkshorten.models.ShortenURLTDO;
import com.example.linkshorten.repositories.URLRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class URLService {
    private final URLRepository urlRepository;
    private final ShortenURLTDOMapper shortenURLTDOMapper;
    private final Validator validator;

    public URLService(URLRepository urlRepository,Validator validator) {
        this.urlRepository = urlRepository;
        this.shortenURLTDOMapper = new ShortenURLTDOMapper(urlRepository);
        this.validator = validator;
    }
    public ShortenURLTDO saveURL(ShortenURLTDO shortenURLTDO) {
        ShortenURL shortenURL = shortenURLTDOMapper.map(generateShortURL(shortenURLTDO));
        Set<ConstraintViolation<ShortenURL>> errors = validator.validate(shortenURL);

        if (errors.isEmpty()){
            if(shortenURL.getPassword() == ""){
                shortenURL.setPassword(null);
            }
            shortenURL = urlRepository.save(shortenURL);

            return shortenURLTDOMapper.mapTDO(shortenURL);
        }else{
            return null;
        }


    }

    public ShortenURLTDO generateShortURL(ShortenURLTDO shortenURLTDO) {
        String shortLink;
        while (true){
            shortLink = "";
            for (int i =0; i <10; i++){
                int randomInt = (int)(Math.random()*(122-97+1)+97);
                char appendChar = (char)randomInt;
                randomInt = (int)(Math.random()*(1-0+1)+0);
                if (randomInt == 0)
                    appendChar = Character.toUpperCase(appendChar);
                shortLink += appendChar;

            }
            if (!urlRepository.existsById(shortLink))
                break;
        }

        shortenURLTDO.setRedirectUrl("http://localhost:8080/red/"+shortLink);
        shortenURLTDO.setId(shortLink);
        shortenURLTDO.setVisits(0);

        return shortenURLTDO;

    }

    public Optional<ShortenURLTDO> getURLById(String id, boolean withPassword) {
        if (withPassword)
            return urlRepository.findById(id)
                    .map(shortenURLTDOMapper::mapTDOWithPassword);
        return urlRepository.findById(id)
                .map(shortenURLTDOMapper::mapTDO);
    }


    public Optional<ShortenURL> updateUrl(ShortenURLTDO shortenURLTDO){
        ShortenURL shortenURL = shortenURLTDOMapper.map(shortenURLTDO);
        Set<ConstraintViolation<ShortenURL>> errors = validator.validate(shortenURL);
        if (errors.isEmpty()){
            return Optional.of(urlRepository.save(shortenURL));
        }else{
            return Optional.empty();
        }

    }

    public void incrementVisits(ShortenURLTDO shortenURLTDO){
        shortenURLTDO.setVisits(shortenURLTDO.getVisits()+1);
        updateUrl(shortenURLTDO);
    }

    public void deleteURL(String id){
        urlRepository.deleteById(id);
    }

    public boolean isUrlUnique(String url, String id){
        Optional<ShortenURL> shortenURLTemp = urlRepository.findByTargetURLOrRedirectURL(url);
        if (shortenURLTemp.isPresent()){
            ShortenURL shortenURL = shortenURLTemp.get();
            return shortenURL.getUrlId().equals(id);
        }
         return true;
    }

}
