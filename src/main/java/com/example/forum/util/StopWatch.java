package com.example.forum.util;

import java.util.Date;

public final class StopWatch {
    public long startTime;

    private StopWatch(long startTime) {
        this.startTime = startTime;
    }

    public static StopWatch start() {
        return new StopWatch(new Date().getTime());
    }

    public long end() {
        final long endTime = new Date().getTime();
        return endTime - startTime;
    }
}