package com.github.teruyukisuda;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalcUtilTest {

    @Test
    public void testAdd() {
        assertEquals(5, CalcUtil.add(2, 3));
        assertEquals(0, CalcUtil.add(0, 0));
        assertEquals(-1, CalcUtil.add(2, -3));
    }

    @Test
    public void testAddWithOverflow() {
        assertThrows(ArithmeticException.class, () -> CalcUtil.add(Integer.MAX_VALUE, 1));
        assertThrows(ArithmeticException.class, () -> CalcUtil.add(1, Integer.MAX_VALUE));
    }

    @Test
    public void testSubtract() {
        assertEquals(-1, CalcUtil.subtract(2, 3));
        assertEquals(0, CalcUtil.subtract(0, 0));
        assertEquals(5, CalcUtil.subtract(2, -3));
    }

    @Test
    public void testSubtractWithUnderflow() {
        assertThrows(ArithmeticException.class, () -> CalcUtil.subtract(Integer.MIN_VALUE, 1));
        assertThrows(ArithmeticException.class, () -> CalcUtil.subtract(0, Integer.MAX_VALUE));
    }

    @Test
    public void testMultiply() {
        assertEquals(6, CalcUtil.multiply(2, 3));
        assertEquals(0, CalcUtil.multiply(0, 5));
        assertEquals(-6, CalcUtil.multiply(2, -3));
    }

    @Test
    public void testMultiplyWithOverflow() {
        // これらのテストは、メソッド内のオーバーフローチェックにより例外が発生しない可能性があります
        // しかし、境界条件をテストするためにこれらを含めています
        assertThrows(ArithmeticException.class, 
            () -> CalcUtil.multiply(Integer.MAX_VALUE, 2));
        assertThrows(ArithmeticException.class, 
            () -> CalcUtil.multiply(Integer.MIN_VALUE, 2));
    }

    @Test
    public void testDivide() {
        assertEquals(2.0, CalcUtil.divide(6, 3));
        assertEquals(0.5, CalcUtil.divide(1, 2));
        assertEquals(-2.0, CalcUtil.divide(6, -3));
    }

    @Test
    public void testDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> CalcUtil.divide(5, 0));
    }
}
