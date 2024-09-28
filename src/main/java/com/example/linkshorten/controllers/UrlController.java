package com.example.linkshorten.controllers;

import com.example.linkshorten.models.ShortenURL;
import com.example.linkshorten.models.ShortenURLDeleteTDO;
import com.example.linkshorten.models.ShortenURLTDO;
import com.example.linkshorten.models.ShortenURLUpdateTDO;
import com.example.linkshorten.services.URLService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class UrlController {
    private URLService urlService;

    public UrlController(URLService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/create-menu")
    public String create(Model model) {
        model.addAttribute("shortenURLTDO", new ShortenURLTDO());
        return "link-creation";
    }

    @PostMapping("/create-link")
    public  String createUrl(@Valid @ModelAttribute("shortenURLTDO") ShortenURLTDO shortenURLTDO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors())
            return "link-creation";

        ShortenURLTDO savedUrlTDO = urlService.saveURL(urlService.generateShortURL(shortenURLTDO));
        redirectAttributes.addAttribute("urlId", savedUrlTDO.getId());
        return "redirect:link-operation";
    }

    @GetMapping("/link-operation")
    public String linkOperation(@RequestParam String urlId, Model model){
        Optional<ShortenURLTDO> optionalShortenURLTDO = urlService.getURLById(urlId,true);
        ShortenURLTDO shortenURLTDO = optionalShortenURLTDO.orElse(null);

        if (shortenURLTDO == null){
            return "link-not-found";
        }
        ShortenURLUpdateTDO shortenURLUpdateTDO = new ShortenURLUpdateTDO();
        shortenURLUpdateTDO.setId(shortenURLTDO.getId());
        shortenURLUpdateTDO.setName(shortenURLTDO.getName());
        shortenURLUpdateTDO.setTargetUrl(shortenURLTDO.getTargetUrl());
        shortenURLUpdateTDO.setPassword(shortenURLTDO.getPassword());

        ShortenURLDeleteTDO shortenURLDeleteTDO = new ShortenURLDeleteTDO();
        shortenURLDeleteTDO.setId(shortenURLTDO.getId());


        model.addAttribute("shortenURLTDO",shortenURLTDO);
        model.addAttribute("shortenURLUpdateTDO",shortenURLUpdateTDO);
        model.addAttribute("shortenURLDeleteTDO",shortenURLDeleteTDO);
        return "link-operation";
    }

    @PostMapping("/delete-link")
    public String deleteLink(@Valid @ModelAttribute("shortenURLDeleteTDO") ShortenURLDeleteTDO shortenURLDeleteTDO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            return "link-delete";
        }
        Optional<ShortenURLTDO> optionalShortenURLTDO = urlService.getURLById(shortenURLDeleteTDO.getId(),true);
        ShortenURLTDO tempShortenURLTDO = optionalShortenURLTDO.orElse(null);
         if(!tempShortenURLTDO.getPassword().equals(shortenURLDeleteTDO.getPassword())) {
            redirectAttributes.addAttribute("urlId", tempShortenURLTDO.getId());
            return "redirect:delete-failed";
        }
        urlService.deleteURL(tempShortenURLTDO.getId());
        return "redirect:delete-success";
    }

    @GetMapping("/delete-success")
    public String deleteSuccess(){
        return "delete-success";
    }

    @GetMapping("/delete-failed")
    public String deleteFailed(){
        return "delete-failed";
    }
    @PostMapping("/update-link")
    public String updateLink(@Valid @ModelAttribute("shortenURLUpdateTDO") ShortenURLUpdateTDO shortenURLUpdateTDO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            return "link-update";
        }
        if(!urlService.isUrlUnique(shortenURLUpdateTDO.getTargetUrl(), shortenURLUpdateTDO.getId())){
            return "update-failed-dupe";
        }
        Optional<ShortenURLTDO> optionalShortenURLTDO = urlService.getURLById(shortenURLUpdateTDO.getId(),true);
        if (optionalShortenURLTDO.isPresent()) {
            ShortenURLTDO tempShortenURLTDO = optionalShortenURLTDO.get();
            if(tempShortenURLTDO.getPassword()==null ) {
                return "link-update";
            }
            if( !tempShortenURLTDO.getPassword().equals(shortenURLUpdateTDO.getPassword())){
                return "update-failed";
            }
            tempShortenURLTDO.setName(shortenURLUpdateTDO.getName());
            tempShortenURLTDO.setTargetUrl(shortenURLUpdateTDO.getTargetUrl());
            urlService.updateUrl(tempShortenURLTDO);

            redirectAttributes.addAttribute("urlId", tempShortenURLTDO.getId());
            return "redirect:link-operation";
        }

        return "link-update";
    }


    @GetMapping("/update-failed")
    public String updateFailed(){
        return "update-failed";
    }
//    @GetMapping("/redirect")
//    public String redirect(@ModelAttribute ShortenURLTDO shortenURLTDO){
//        ShortenURL shortenURL = urlService.getURL(shortenURLTDO.getShortURL());
//        return "redirect:" + shortenURL.getOriginalURL();
//    }
    @GetMapping("/update-menu")
    public String update(Model model, @Nullable @RequestParam String urlId){
        ShortenURLUpdateTDO shortenURLUpdateTDO = new ShortenURLUpdateTDO();
        if(urlId != null){
            Optional<ShortenURLTDO> optionalShortenURLTDO = urlService.getURLById(urlId,true);
            ShortenURLTDO tempShortenURLTDO = optionalShortenURLTDO.orElse(null);
            shortenURLUpdateTDO.setId(urlId);
            shortenURLUpdateTDO.setName(tempShortenURLTDO.getName());
            shortenURLUpdateTDO.setTargetUrl(tempShortenURLTDO.getTargetUrl());
        }
        model.addAttribute("shortenURLUpdateTDO", shortenURLUpdateTDO );

        return "link-update";
    }

    @GetMapping("/delete-menu")
    public String delete(Model model, @Nullable @RequestParam String urlId){
        ShortenURLDeleteTDO shortenURLDeleteTDO = new ShortenURLDeleteTDO();
        if(urlId != null){
            shortenURLDeleteTDO.setId(urlId);
        }
        model.addAttribute("shortenURLDeleteTDO", shortenURLDeleteTDO );

        return "link-delete";
    }


}
