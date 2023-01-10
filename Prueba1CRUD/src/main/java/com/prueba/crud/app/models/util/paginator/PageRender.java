package com.prueba.crud.app.models.util.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T> {
    private String url;
    private Page<T> page;
    private int totalPages;
    private int currentPage;
    private int numOfItemPerPage;
    private List<PageItem> pages;


    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.pages= new ArrayList<PageItem>();

        numOfItemPerPage = page.getSize();
        totalPages = page.getTotalPages();
        currentPage = page.getNumber() + 1;

        int from, to;

        if (totalPages <= numOfItemPerPage) {
            from = 1;
            to = totalPages;
        } else {
            if (currentPage <= numOfItemPerPage / 2) {
                from = 1;
                to = numOfItemPerPage;
            } else if (currentPage >= totalPages - numOfItemPerPage / 2) {
                from = totalPages - numOfItemPerPage + 1;
                to = numOfItemPerPage;
            } else {
                from = currentPage - numOfItemPerPage / 2;
                to = numOfItemPerPage;
            }
        }
        for (int i = 0; i < to; i++) {
            pages.add(new PageItem(from + i, currentPage == from + i));
        }


    }

    public String getUrl() {
        return url;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public List<PageItem> getPages() {
        return pages;
    }

    public boolean isFirst() {
        return page.isFirst();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public boolean isHasNext() {
        return page.hasNext();
    }

    public boolean isHasPrevious() {
        return page.hasPrevious();
    }
}
