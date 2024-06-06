package com.pw.gwhparser1.loader.appconfig.sample;

public interface SampleReader {

    public <T> T readSample(String sample) throws RuntimeException;

}
