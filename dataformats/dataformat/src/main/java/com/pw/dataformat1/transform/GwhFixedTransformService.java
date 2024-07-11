package com.pw.dataformat1.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import com.pw.support1.model.ElementConfig;
import com.pw.support1.model.FormatModel;
import com.pw.support1.util.GsonUtils;

@Service
public class GwhFixedTransformService implements GwhTransformService<String, String> {

    public static final String TRANSFORM_ID = "FIXED";

    List<ElementConfig> eleConfigs;
    String template;
    String newline = "\r\n";

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String trimNewLine(String ori) {
        if (StringUtils.isBlank(ori)) {
            return ori;
        }
        return ori.replace("\r\n", "").replace("\n", "").replace("\r", "");
    }


    private String[] getLengths(String str, String delimiter) {
        if (StringUtils.isBlank(str)) {
            return new String[]{};
        }
        return str.split(delimiter);
    }

    public void loadTemplate(String headerCandidate) throws Exception {

        FormatModel formatModel = GsonUtils.getGson().fromJson(template, FormatModel.class);
        String[] header = new String[]{};
        eleConfigs = new ArrayList<>();

        String [] lengths = getLengths(formatModel.getContent(), formatModel.getDelimiter());
        if (StringUtils.isNotBlank(headerCandidate) && StringUtils.equalsIgnoreCase(formatModel.getIsHeaderExists(), "true")) {
            String hc = StringUtils.replace(headerCandidate, newline, "");
            header = hc.split(formatModel.getDelimiter());
            if (header.length != lengths.length) {
                throw new Exception("Transform file error:header length is not equal to data length");
            }
        }

        int totalLen = 0;
        for (int i = 0; i < lengths.length; i++) {
            ElementConfig ele = new ElementConfig();
            if (ArrayUtils.isNotEmpty(header)) {
                ele.setName(trimNewLine(header[i]));
            } else {
                int ofs = i + 1;
                ele.setName("C" + ofs);
            }
            ele.setIndex(i);
            ele.setStart(totalLen);
            totalLen = totalLen + NumberUtils.toInt(lengths[i], 0);
            ele.setLength(NumberUtils.toInt(lengths[i]));
            eleConfigs.add(ele);
        }
    }

    @Override
    public Map<String, String> process(String rawMessage, String template, Boolean isFirstLine) throws Exception {
        Map<String, String> map = new HashMap<>();
        setTemplate(template);
        FormatModel formatModel = GsonUtils.getGson().fromJson(template, FormatModel.class);
        if (isFirstLine && StringUtils.equalsIgnoreCase(formatModel.getIsHeaderExists(), "true")) {
            loadTemplate(rawMessage);
            return new HashMap<>();
        }

        if (eleConfigs == null || eleConfigs.size() == 0) {
            loadTemplate(StringUtils.EMPTY);
        }

        for (ElementConfig ele : eleConfigs) {
            String name = ele.getName();
            String value = trimNewLine(StringUtils.substring(rawMessage, ele.getStart(), ele.getStart() + ele.getLength()));
            map.put(name, value);
        }
        return map;
    }

}
