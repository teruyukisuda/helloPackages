package com.github.teruyukisuda;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldUtilTest {

  @Test
  public void testHello() {
    final String s = HelloWorldUtil.returnHello();
    assertEquals("Hello", s);
  }

  @Test
  public void testBye() {
    final String s = HelloWorldUtil.returnBye();
    assertEquals("Bye", s);
  }

  @Test
  public void testYeah() {
    final String s = HelloWorldUtil.returnYeah();
    assertEquals("Yeah", s);
  }

  @Test
  public void testHowOld() {
    final String s = HelloWorldUtil.returnHowOld();
    assertEquals("how old are you", s);
  }
}
