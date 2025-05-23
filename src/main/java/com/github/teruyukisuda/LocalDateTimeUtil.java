package com.github.teruyukisuda;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtil {

  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public static String formatLocalTime(LocalTime time) {
    if (time == null) {
      throw new IllegalArgumentException("LocalTimeはnullにできません。");
    }
    return time.format(TIME_FORMATTER);
  }

  public static String formatLocalDateTime(LocalDateTime dateTime) {
    if (dateTime == null) {
      throw new IllegalArgumentException("LocalDateTimeはnullにできません。");
    }
    return dateTime.format(DATE_TIME_FORMATTER);
  }
}
