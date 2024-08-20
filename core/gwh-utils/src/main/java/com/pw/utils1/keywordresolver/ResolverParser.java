package com.pw.utils1.keywordresolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ResolverParser {

    private final KeywordResolver keywordResolver;
    private final ResolverPatternUtil resolverPatternUtil;

    public ResolverParser(KeywordResolver keywordResolver, ResolverPatternUtil resolverPatternUtil) {
        this.keywordResolver = keywordResolver;
        this.resolverPatternUtil = resolverPatternUtil;
    }

    public String resolveKeywords(String expression){
        Map<String, Object> keywordToResolverValueMap = executeResolver(expression);
        return replaceKeywordsWithValue(expression, keywordToResolverValueMap);
    }

    private Map<String, Object> executeResolver(String expression) {
        List<String> listOfKeyword = resolverPatternUtil.getListOfKeywords(expression);
        Map<String, Object> keywordToResolverValueMap = new HashMap<>();
        listOfKeyword.stream()
                .forEach(
                        keyword -> {
                            String extractedKeyword = resolverPatternUtil.extractKeyword(keyword);
                            String keyResolver = resolverPatternUtil.getKeywordResolver(extractedKeyword);
                            String keywordValue = resolverPatternUtil.getKeywordValue(extractedKeyword);
                            Resolver resolver = keywordResolver.getResolver(keyResolver).get();
                            Object resolveValue = resolver.resolveValue(keywordValue);
                            keywordToResolverValueMap.put(keyword, resolveValue);
                        }
                );
        return keywordToResolverValueMap;
    }

    private String replaceKeywordsWithValue(String expression, Map<String, Object> keywordToResolverValueMap){
        List<String> keyList = keywordToResolverValueMap.keySet().stream().collect(Collectors.toList());
        for (int index = 0; index < keyList.size(); index++){
            String key = keyList.get(index);
            String resolveValue = keywordToResolverValueMap.get(key).toString();
            expression = expression.replace(key, resolveValue);
        }
        return expression;
    }
}
