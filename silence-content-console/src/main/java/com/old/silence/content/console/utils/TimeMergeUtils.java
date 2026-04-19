package com.old.silence.content.console.utils;

import com.google.common.collect.Range;

import java.time.Instant;
import java.util.List;

/**
 * @author EX-ZHANGMENGWEI001
 */
public final class TimeMergeUtils {

    private TimeMergeUtils() {
        throw new AssertionError();
    }

    // 判断是否交叉
    public static boolean isOverlapForInstant(Instant startTime, Instant endTime, List<Range<Instant>> timeRanges){
        var wholeRange = Range.closed(startTime, endTime);
        for (Range<Instant> timeRange : timeRanges) {
            var encloses = timeRange.isConnected(wholeRange);
            if(encloses) {
                return false;
            }
        }
        return true;
    }

    // 判断是否超出指定时间范围, encloses方法 一个range里面的值都在另一个range里面
    public static boolean isOverFlowForInstant(Instant startTime, Instant endTime, List<Range<Instant>> checkRanges){
        var wholeRange = Range.closed(startTime, endTime);
        return checkRanges.stream().allMatch(wholeRange::encloses);

    }


}
