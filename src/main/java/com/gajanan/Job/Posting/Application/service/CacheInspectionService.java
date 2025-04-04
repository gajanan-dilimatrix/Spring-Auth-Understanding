package com.gajanan.Job.Posting.Application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static java.lang.System.*;

@Service
@RequiredArgsConstructor
public class CacheInspectionService {


    private final CacheManager cacheManager;

    public void printCacheContent(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            out.println("Cache Contents");
            out.println(Objects.requireNonNull(cache.getNativeCache()));
        }else{
            out.println("No such cache: "+cacheName);
        }
    }
}
