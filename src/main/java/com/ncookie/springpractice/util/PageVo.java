package com.ncookie.springpractice.util;

public class PageVo {
    public int totalPage;

    public int startNumber;
    public int endNumber;

    public boolean hasPrev;
    public boolean hasNext;

    public int prevIndex;
    public int nextIndex;

    public PageVo(int totalPage, int startNumber, int endNumber, boolean hasPrev, boolean hasNext, int prevIndex, int nextIndex) {
        this.totalPage = totalPage;
        this.startNumber = startNumber;
        this.endNumber = endNumber;
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;
        this.prevIndex = prevIndex;
        this.nextIndex = nextIndex;
    }
}
