package com.pw.utils1.keywordresolver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ResolverPatternUtil {
    private static final Pattern PATTERN = Pattern.compile("\\$\\((\\w+)(\\.\\w+)\\)"); //$(rulenamespace.keyword)
    private static final String DOT = ".";

    public List<String> getListOfKeywords(String expression) {
        Matcher matcher = PATTERN.matcher(expression);
        List<String> listOfKeyword = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group();
            listOfKeyword.add(group);
        }
        return listOfKeyword;
    }

    public String extractKeyword(String keyword) {
        return keyword.substring(keyword.indexOf('(') + 1,
                keyword.indexOf(')'));
    }

    public String getKeywordResolver(String keyword){
        ArrayList<String> splittedKeyword = Lists.newArrayList(Splitter.on(DOT).omitEmptyStrings().split(keyword));
        return splittedKeyword.get(0);
    }

    public String getKeywordValue(String keyword){
        ArrayList<String> splittedKeyword = Lists.newArrayList(Splitter.on(DOT).omitEmptyStrings().split(keyword));
        return splittedKeyword.get(1);
    }
}
