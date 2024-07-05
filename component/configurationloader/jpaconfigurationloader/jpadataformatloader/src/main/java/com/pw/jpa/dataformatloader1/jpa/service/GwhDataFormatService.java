package com.pw.jpa.dataformatloader1.jpa.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.pw.jpa.dataformatloader1.jpa.model.GwhDataFormatEntity;
import com.pw.jpa.dataformatloader1.jpa.repository.GwhDataFormatRepository;

@Service
public class GwhDataFormatService {

    private final GwhDataFormatRepository dataformatRepository;

    public GwhDataFormatService(GwhDataFormatRepository dataformatRepository) {
        this.dataformatRepository = dataformatRepository;
    }

    public List<GwhDataFormatEntity> getAllDataFormats() {
        List<GwhDataFormatEntity> dataFormats = dataformatRepository.findAll();

        if(CollectionUtils.isNotEmpty(dataFormats)) {
            return dataFormats;
        } else {
            return List.of();
        }
    }

    public List<GwhDataFormatEntity> getByProfile(String profile) {
        List<GwhDataFormatEntity> dataFormats = dataformatRepository.findByProfile(profile);

        if(CollectionUtils.isNotEmpty(dataFormats)) {
            return dataFormats;
        } else {
            return List.of();
        }
    }

    public List<GwhDataFormatEntity> getByProfileAndRegionAndVersion(String profile, String region, String version) {
        List<GwhDataFormatEntity> dataFormats = dataformatRepository.findByProfileAndRegionAndVersion(profile, region, version);

        if(CollectionUtils.isNotEmpty(dataFormats)) {
            return dataFormats;
        } else {
            return List.of();
        }
    }

}
