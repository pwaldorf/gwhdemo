package com.pw.support1.transform;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.pw.support1.util.GsonUtils;

import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;

@Slf4j
@Service
public class GwhDelimitedTransformService implements GwhTransformService<String, Object> {

    String newline = "\r\n";
    String[] headers;

    @Override
    public Map<String, Object> process(String rawMessage, String template, Boolean isHeader) throws Exception {

        Map<String, Object> delimiterMap = GsonUtils.mapJson(template);
        Boolean isHead = false;
        String delimiter = "";

        if (delimiterMap.size() > 1) {
            isHead = (Boolean) delimiterMap.get("isHeaderExist");
        }
        if (isHead == null) {
            log.error("Transform file template error: isHeaderExist is null");
            throw new Exception("Transform file template error: isHeaderExist is null");
        }

        if (delimiterMap.get("delimiter") == null) {
            log.error("Transform file template error: delimiter is null");
            throw new Exception("Transform file template error: delimiter is null");
        } else {
            delimiter = delimiterMap.get("delimiter").toString();
        }

        String[] rawMessages = rawMessage.split(delimiter);
        Map<String, Object> delimiterValue = new HashMap<>();
        String keyName = "";

        for(int i = 0; i < rawMessages.length; i++) {
            if (headers != null && headers.length > 1) {
                if (headers.length == rawMessages.length) {
                    log.error("Transform file error:header length is not equal to data length,header length is {}, data length is {}",
                        headers.length, rawMessages.length);
                    throw new Exception("Transform file error:header length is not equal to data length");
                }
                keyName = trimNewLine(headers[i]);
            } else {
                int ofs = i + 1;
                keyName = "C" + ofs;
            }
            delimiterValue.put(keyName, trimNewLine(rawMessages[i]));
        }
        return delimiterValue;
    }

    public String trimNewLine(String ori) {
        if (StringUtils.isBlank(ori)) {
            return ori;
        }
        return ori.replace("\r\n", "").replace("\n", "").replace("\r", "");
    }


}
