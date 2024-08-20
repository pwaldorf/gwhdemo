package com.pw.utils1.keywordresolver;

public interface Resolver {
    String getResolverKeyword();
    <T> T resolveValue(String keyword);
}
