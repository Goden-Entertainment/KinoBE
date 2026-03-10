# LocalDate Cheatsheet

`LocalDate` represents a date only (year, month, day) — no time, no timezone.

---

## Creating Dates

```java
LocalDate today  = LocalDate.now();
LocalDate fixed  = LocalDate.of(2026, 6, 15);
LocalDate parsed = LocalDate.parse("2026-06-15");
```

---

## Adding / Subtracting

```java
today.plusDays(1)
today.plusWeeks(1)
today.plusMonths(3)
today.plusYears(1)

today.minusDays(1)
today.minusWeeks(1)
today.minusMonths(3)
today.minusYears(1)
```

---

## Comparing Dates

```java
date.isBefore(other)   // date < other
date.isAfter(other)    // date > other
date.isEqual(other)    // date == other
date.compareTo(other)  // returns -1, 0, or 1
```

---

## Extracting Info

```java
today.getYear()         // 2026
today.getMonthValue()   // 3  (int)
today.getMonth()        // MARCH  (enum)
today.getDayOfWeek()    // TUESDAY  (enum)
today.getDayOfYear()    // 69
today.lengthOfMonth()   // days in the current month
today.isLeapYear()      // true / false
```

---

## Period — Difference Between Two Dates

```java
Period gap = Period.between(startDate, endDate);
gap.getYears()         // years part
gap.getMonths()        // months part
gap.getDays()          // days part
gap.toTotalMonths()    // total months as a single number
```