package com.github.teruyukisuda;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeastOnce;
import java.lang.reflect.Field;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;

public class HelloWorldUtilTest {

  private ListAppender<ILoggingEvent> listAppender;
  private Logger helloWorldLogger;

  @BeforeEach
  public void setup() {
    // Get the logger for HelloWorldUtil class
    helloWorldLogger = (Logger) LoggerFactory.getLogger(HelloWorldUtil.class);

    // Set the logger level to DEBUG
    helloWorldLogger.setLevel(ch.qos.logback.classic.Level.DEBUG);

    // Create and start a ListAppender
    listAppender = new ListAppender<>();
    listAppender.start();

    // Add the appender to the logger
    helloWorldLogger.addAppender(listAppender);
  }

  @AfterEach
  public void teardown() {
    // Remove the appender after the test
    helloWorldLogger.detachAppender(listAppender);
  }

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
  public void testReturnHelloLogging() {
    final String s = HelloWorldUtil.returnHello();
    assertEquals("Hello", s);

    // Verify that the log message was recorded
    boolean found = false;
    for (ILoggingEvent event : listAppender.list) {
      if (event.getMessage().equals("Returning hello message") && 
          event.getLevel().toString().equals("DEBUG")) {
        found = true;
        break;
      }
    }
    assertTrue(found, "Expected debug log message not found");
  }

  @Test
  public void testYeah() {
    final String s = HelloWorldUtil.returnYeah();
    assertEquals("Yeah", s);
  }

}
