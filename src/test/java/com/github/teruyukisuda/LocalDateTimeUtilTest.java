package com.github.teruyukisuda;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeUtilTest {

    @Test
    public void testFormatLocalTime() {
        // 特定の時間でテスト
        LocalTime time = LocalTime.of(14, 30, 45);
        assertEquals("14:30:45", LocalDateTimeUtil.formatLocalTime(time));

        // 深夜でテスト
        LocalTime midnight = LocalTime.of(0, 0, 0);
        assertEquals("00:00:00", LocalDateTimeUtil.formatLocalTime(midnight));

        // 23:59:59でテスト
        LocalTime endOfDay = LocalTime.of(23, 59, 59);
        assertEquals("23:59:59", LocalDateTimeUtil.formatLocalTime(endOfDay));
    }

    @Test
    public void testFormatLocalTimeWithNull() {
        // nullでテストするとIllegalArgumentExceptionがスローされるはず
        assertThrows(IllegalArgumentException.class, () -> LocalDateTimeUtil.formatLocalTime(null));
    }

    @Test
    public void testFormatLocalDateTime() {
        // 特定の日付と時間でテスト
        LocalDateTime dateTime = LocalDateTime.of(2023, 5, 15, 14, 30, 45);
        assertEquals("2023-05-15 14:30:45", LocalDateTimeUtil.formatLocalDateTime(dateTime));

        // エポックの開始でテスト
        LocalDateTime startOfEpoch = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        assertEquals("1970-01-01 00:00:00", LocalDateTimeUtil.formatLocalDateTime(startOfEpoch));

        // 未来の日付でテスト
        LocalDateTime future = LocalDateTime.of(2099, 12, 31, 23, 59, 59);
        assertEquals("2099-12-31 23:59:59", LocalDateTimeUtil.formatLocalDateTime(future));
    }

    @Test
    public void testFormatLocalDateTimeWithNull() {
        // nullでテストするとIllegalArgumentExceptionがスローされるはず
        assertThrows(IllegalArgumentException.class, () -> LocalDateTimeUtil.formatLocalDateTime(null));
    }
}
