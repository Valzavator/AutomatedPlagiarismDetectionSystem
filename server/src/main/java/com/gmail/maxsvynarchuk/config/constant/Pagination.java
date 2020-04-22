package com.gmail.maxsvynarchuk.config.constant;

import com.gmail.maxsvynarchuk.util.ResourceManager;

public final class Pagination {
    public static final String DEFAULT_PAGE_NUMBER = ResourceManager.PAGINATION.getProperty("default.page.number");
    public static final String DEFAULT_PAGE_SIZE  = System.getenv("default.page.size");
    public static final String MAX_PAGE_SIZE = ResourceManager.PAGINATION.getProperty("max.page.size");

    private Pagination() {
    }
}
