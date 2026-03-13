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
        if(other == null){
            throw new TimeRangeException("TimeRange method, overlaps(TimeRange other): TimeRange given in args cannot be null");
        }
        if (other.getStart() == null || other.getEnd() == null) {
            throw new TimeRangeException("Other TimeRange has null start or end: " +
                    other);
        }
        if (start == null || end == null) {
            throw new TimeRangeException("This TimeRange has null start or end: " + this);
        }

        return start.isBefore(other.getEnd()) && end.isAfter(other.getStart());
    }
}
