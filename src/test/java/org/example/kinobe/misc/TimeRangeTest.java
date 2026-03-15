package org.example.kinobe.misc;

import org.example.kinobe.exception.TimeRangeException;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeRangeTest {

    private static final LocalTime T10 = LocalTime.of(10, 0);
    private static final LocalTime T12 = LocalTime.of(12, 0);
    private static final LocalTime T14 = LocalTime.of(14, 0);
    private static final LocalTime T16 = LocalTime.of(16, 0);

    // --- overlap cases ---

    @Test
    void overlaps_thisStartsInsideOther() {
        // this: [11, 13], other: [10, 12] — this starts inside other
        TimeRange thisRange = new TimeRange(LocalTime.of(11, 0), LocalTime.of(13, 0));
        TimeRange other = new TimeRange(T10, T12);
        assertTrue(thisRange.overlaps(other));
    }

    @Test
    void overlaps_thisEndsInsideOther() {
        // this: [09, 11], other: [10, 12] — this ends inside other
        TimeRange thisRange = new TimeRange(LocalTime.of(9, 0), LocalTime.of(11, 0));
        TimeRange other = new TimeRange(T10, T12);
        assertTrue(thisRange.overlaps(other));
    }

    @Test
    void overlaps_thisIsInsideOther() {
        // this: [11, 11:30], other: [10, 12] — this is fully inside other
        TimeRange thisRange = new TimeRange(LocalTime.of(11, 0), LocalTime.of(11, 30));
        TimeRange other = new TimeRange(T10, T12);
        assertTrue(thisRange.overlaps(other));
    }

    @Test
    void overlaps_thisContainsOther() {
        // this: [10, 14], other: [11, 13] — this fully contains other
        TimeRange thisRange = new TimeRange(T10, T14);
        TimeRange other = new TimeRange(LocalTime.of(11, 0), LocalTime.of(13, 0));
        assertTrue(thisRange.overlaps(other));
    }

    // --- non-overlap cases ---

    @Test
    void noOverlap_thisIsBeforeOther() {
        // this: [10, 12], other: [14, 16] — no overlap
        TimeRange thisRange = new TimeRange(T10, T12);
        TimeRange other = new TimeRange(T14, T16);
        assertFalse(thisRange.overlaps(other));
    }

    @Test
    void noOverlap_thisIsAfterOther() {
        // this: [14, 16], other: [10, 12] — no overlap
        TimeRange thisRange = new TimeRange(T14, T16);
        TimeRange other = new TimeRange(T10, T12);
        assertFalse(thisRange.overlaps(other));
    }

    @Test
    void noOverlap_adjacentRanges() {
        // this: [10, 12], other: [12, 14] — touching but not overlapping
        TimeRange thisRange = new TimeRange(T10, T12);
        TimeRange other = new TimeRange(T12, T14);
        assertFalse(thisRange.overlaps(other));
    }

    // --- isWithin(LocalTime) cases ---

    @Test
    void isWithin_timeInsideRange_ReturnsTrue() {
        TimeRange range = new TimeRange(T10, T14);
        assertTrue(range.isWithin(T12));
    }

    @Test
    void isWithin_timeAtStartBoundary_ReturnsTrue() {
        TimeRange range = new TimeRange(T10, T14);
        assertTrue(range.isWithin(T10));
    }

    @Test
    void isWithin_timeAtEndBoundary_ReturnsTrue() {
        TimeRange range = new TimeRange(T10, T14);
        assertTrue(range.isWithin(T14));
    }

    @Test
    void isWithin_timeBeforeStart_ReturnsFalse() {
        TimeRange range = new TimeRange(T12, T16);
        assertFalse(range.isWithin(T10));
    }

    @Test
    void isWithin_timeAfterEnd_ReturnsFalse() {
        TimeRange range = new TimeRange(T10, T12);
        assertFalse(range.isWithin(T14));
    }

    @Test
    void isWithin_nullTime_ThrowsTimeRangeException() {
        TimeRange range = new TimeRange(T10, T14);
        assertThrows(TimeRangeException.class, () -> range.isWithin((LocalTime) null));
    }

    // --- isWithin(TimeRange) cases ---

    @Test
    void isWithin_rangeFullyInsideThis_ReturnsTrue() {
        TimeRange outer = new TimeRange(T10, T16);
        TimeRange inner = new TimeRange(T12, T14);
        assertTrue(inner.isWithin(outer));
    }

    @Test
    void isWithin_rangeAtExactBoundaries_ReturnsTrue() {
        TimeRange range = new TimeRange(T10, T16);
        assertTrue(range.isWithin(new TimeRange(T10, T16)));
    }

    @Test
    void isWithin_rangeStartsBeforeThis_ReturnsFalse() {
        TimeRange outer = new TimeRange(T12, T16);
        TimeRange inner = new TimeRange(T10, T14);
        assertFalse(outer.isWithin(inner));
    }

    @Test
    void isWithin_rangeEndsAfterThis_ReturnsFalse() {
        TimeRange outer = new TimeRange(T10, T12);
        TimeRange inner = new TimeRange(T10, T16);
        assertFalse(inner.isWithin(outer));
    }

    @Test
    void isWithin_nullRange_ThrowsTimeRangeException() {
        TimeRange range = new TimeRange(T10, T14);
        assertThrows(TimeRangeException.class, () -> range.isWithin((TimeRange) null));
    }

    // --- null guard cases ---

    @Test
    void throws_whenOtherIsNull() {
        TimeRange thisRange = new TimeRange(T10, T12);
        assertThrows(TimeRangeException.class, () -> thisRange.overlaps(null));
    }

    @Test
    void throws_whenOtherStartIsNull() {
        TimeRange thisRange = new TimeRange(T10, T12);
        TimeRange other = new TimeRange(null, T14);
        assertThrows(TimeRangeException.class, () -> thisRange.overlaps(other));
    }

    @Test
    void throws_whenOtherEndIsNull() {
        TimeRange thisRange = new TimeRange(T10, T12);
        TimeRange other = new TimeRange(T14, null);
        assertThrows(TimeRangeException.class, () -> thisRange.overlaps(other));
    }

    @Test
    void throws_whenThisStartIsNull() {
        TimeRange thisRange = new TimeRange(null, T12);
        TimeRange other = new TimeRange(T10, T14);
        assertThrows(TimeRangeException.class, () -> thisRange.overlaps(other));
    }

    @Test
    void throws_whenThisEndIsNull() {
        TimeRange thisRange = new TimeRange(T10, null);
        TimeRange other = new TimeRange(T12, T14);
        assertThrows(TimeRangeException.class, () -> thisRange.overlaps(other));
    }
}
