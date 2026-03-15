package org.example.kinobe.misc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.kinobe.exception.TimeRangeException;

import java.time.LocalTime;

@ToString
@Getter
@Setter
public class TimeRange {
    private LocalTime start;
    private LocalTime end;

    public TimeRange(LocalTime start, LocalTime end){
        this.start = start;
        this.end = end;
    }

    public boolean overlaps(TimeRange other){
        nullCheck(other, "overlaps");

        return start.isBefore(other.getEnd()) && end.isAfter(other.getStart());
    }

    public boolean isWithin(LocalTime timeWindow){
        nullCheck(timeWindow, "isWithin");

        return !timeWindow.isBefore(start) && !timeWindow.isAfter(end);
    }


    public boolean isWithin(TimeRange other){
        nullCheck(other, "isWithin");

        return !other.getStart().isBefore(start) && !other.getEnd().isAfter(end);
    }

    private void nullCheck(Object other, String methodName){
        if(other == null){
            throw new TimeRangeException("TimeRange method: " + methodName + ", method arg is null.");
        }
        if (other instanceof TimeRange otherTimeRange && (otherTimeRange.getStart() == null || otherTimeRange.getEnd() == null)) {
            throw new TimeRangeException("Other TimeRange has null start or end: " + other);
        }
        if (start == null || end == null) {
            throw new TimeRangeException("This TimeRange has null start or end: " + this);
        }

    }
}
