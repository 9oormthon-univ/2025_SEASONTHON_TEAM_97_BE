package com.example.ccsketch.DTO;

import java.util.List;

public class AiSearchResponseDto {
    private List<Long> resultCardIds;
    private String modelVersion;
    public List<Long> getResultCardIds() {
        return resultCardIds;
    }

    public void setResultCardIds(List<Long> resultCardIds) {
        this.resultCardIds = resultCardIds;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }
}
