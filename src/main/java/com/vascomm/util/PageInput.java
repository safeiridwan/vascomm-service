package com.vascomm.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageInput {
    private int take;
    private int skip;
    private int page;
    private int size;
    private String orderBy;
    private String search = "";

    public PageInput(int take, int skip) {
        double parsePage = (double) skip / take;
        this.page = (int) Math.ceil(parsePage);
        this.size = take;
    }

    public PageInput(int take, int skip, String orderBy) {
        double parsePage = (double) skip / take;
        this.page = (int) Math.ceil(parsePage);
        this.size = take;
        this.orderBy = orderBy;
    }

    public PageInput(int take, int skip, String orderBy, String keyword) {
        double parsePage = (double) skip / take;
        this.page = (int) Math.ceil(parsePage);
        this.size = take;
        this.orderBy = orderBy;
        this.search = keyword;
    }
}
