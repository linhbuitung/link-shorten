package com.example.linkshorten.repositories;

import com.example.linkshorten.models.ShortenURL;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLRepository extends CrudRepository<ShortenURL, String> {
    // Find a ShortenURL by target URL or redirect URL
    @Query("SELECT u FROM ShortenURL u WHERE u.targetUrl = :url OR u.redirectUrl = :url")
    Optional<ShortenURL> findByTargetURLOrRedirectURL(@Param("url") String url);
}
