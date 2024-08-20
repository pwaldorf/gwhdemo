package com.pw.utils1.keywordresolver;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeywordResolver {
    Map<String, Resolver> keywordResolverList;

    public KeywordResolver(List<Resolver> resolverList) {
        keywordResolverList = resolverList.stream()
                .collect(Collectors.toMap(Resolver::getResolverKeyword, Function.identity()));
    }

    public Map<String, Resolver> getKeywordResolverList(){
        return keywordResolverList;
    }

    public Optional<Resolver> getResolver(String keyword) {
        return Optional.ofNullable(keywordResolverList.get(keyword.toUpperCase()));
    }
}
