package com.example.linkshorten.controllers;

import com.example.linkshorten.models.ShortenURLTDO;
import com.example.linkshorten.services.URLService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE
        })
public class UrlApiController {
    private final URLService urlService;
    private final ObjectMapper objectMapper;

    public UrlApiController(URLService urlService, ObjectMapper objectMapper) {

        this.urlService = urlService;
        this.objectMapper = objectMapper;
    }

    @Tag(name = "POST", description = "Create new short URL")
    @PostMapping("/api/links")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShortenURLTDO> saveURLWithPassword(@Valid @RequestBody ShortenURLTDO shortenURLTDO) {
        ShortenURLTDO savedURL = urlService.saveURL(urlService.generateShortURL(shortenURLTDO));

        URI savedURLLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedURL.getId())
                .toUri();
        return ResponseEntity.created(savedURLLocation).body(savedURL);
    }


    @Tag(name = "GET", description = "Get information of an URL")
    @GetMapping("/api/links/{id}")
    public ResponseEntity<ShortenURLTDO> getURLInfo( @PathVariable String id) {
        return urlService.getURLById(id, false)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @Tag(name = "GET")
    @GetMapping("/red/{id}")
    public ResponseEntity<Object> redirectURL(@PathVariable String id) {
        if(!urlService.getURLById(id, false).isPresent())
            return ResponseEntity.notFound().build();
        if(urlService.getURLById(id, true).isPresent()){
            urlService.getURLById(id, true).ifPresent(shortenURLTO -> urlService.incrementVisits(shortenURLTO));
        } else {
            urlService.getURLById(id, false).ifPresent(shortenURLTO -> urlService.incrementVisits(shortenURLTO));}
        return urlService.getURLById(id, false)
                .map(shortenURLTDO -> ResponseEntity.status(HttpStatus.FOUND).location(URI.create(shortenURLTDO.getTargetUrl())).build())
                .orElse(ResponseEntity.notFound().build());
    }

    @Tag(name = "PATCH", description = "Update the URL")
    @PatchMapping("/api/links/{id}")
    public ResponseEntity<?> updateURL(@PathVariable String id, @Valid @RequestBody JsonMergePatch patch) {
        try {
            if(!urlService.getURLById(id, true).isPresent())
                return ResponseEntity.notFound().build();
            ShortenURLTDO urlDTO = urlService.getURLById(id, true).orElseThrow();
            ShortenURLTDO patchedUrlDTO = applyPatch(urlDTO, patch);

            if (patchedUrlDTO == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).header("reason", "wrong password").build();
            urlService.updateUrl(patchedUrlDTO);
        }catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.noContent().build();
    }

    private ShortenURLTDO applyPatch(ShortenURLTDO urlDTO, JsonMergePatch patch) throws JsonProcessingException, JsonPatchException {

        JsonNode urlNode = objectMapper.valueToTree(urlDTO);
        JsonNode patchNode = patch.apply(urlNode);
        if(!patchNode.get("password").asText().equals(urlDTO.getPassword()) )
            return null;
        return objectMapper.treeToValue(patchNode, ShortenURLTDO.class);
    }

    @Tag(name = "DELETE", description = "Delete the URL")
    @DeleteMapping("/api/links/{id}")
    public ResponseEntity<?> deleteURL(@PathVariable String id, @RequestHeader(value = "pass", required = true) String password) {

        if(!urlService.getURLById(id, true).isPresent())
            return ResponseEntity.noContent().build();
        ShortenURLTDO urlDTO = urlService.getURLById(id, true).orElseThrow();
        if(!password.equals(urlDTO.getPassword())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).header("reason", "wrong password").build();
        }
        urlService.deleteURL(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, String> handle(MethodArgumentNotValidException ex){
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
