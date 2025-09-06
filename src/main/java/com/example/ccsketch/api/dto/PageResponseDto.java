package com.example.ccsketch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class PageResponseDto<T> {

    @Schema(description = "실제 데이터 목록")
    private List<T> data;

    @Schema(description = "페이지 정보")
    private PageInfo pageInfo;

    public PageResponseDto(List<T> data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }

    public List<T> getData() {
        return data;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    // Inner static class for PageInfo
    public static class PageInfo {
        @Schema(description = "현재 페이지 번호", example = "0")
        private int page;
        @Schema(description = "페이지 당 항목 수", example = "10")
        private int size;
        @Schema(description = "전체 항목 수", example = "100")
        private long totalElements;
        @Schema(description = "전체 페이지 수", example = "10")
        private int totalPages;

        public PageInfo(int page, int size, long totalElements, int totalPages) {
            this.page = page;
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
        }

        public int getPage() {
            return page;
        }

        public int getSize() {
            return size;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }
    }
}