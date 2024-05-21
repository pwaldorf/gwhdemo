package com.pw.gwhparser.loader.appconfig.sample;

public interface SampleReader {

    public <T> T readSample(String sample) throws RuntimeException;

}
