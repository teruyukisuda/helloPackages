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
    // HelloWorldUtilクラスのロガーを取得
    helloWorldLogger = (Logger) LoggerFactory.getLogger(HelloWorldUtil.class);

    // ロガーレベルをDEBUGに設定
    helloWorldLogger.setLevel(ch.qos.logback.classic.Level.DEBUG);

    // ListAppenderを作成して開始
    listAppender = new ListAppender<>();
    listAppender.start();

    // ロガーにアペンダーを追加
    helloWorldLogger.addAppender(listAppender);
  }

  @AfterEach
  public void teardown() {
    // テスト後にアペンダーを削除
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

    // ログメッセージが記録されたことを確認
    boolean found = false;
    for (ILoggingEvent event : listAppender.list) {
      if (event.getMessage().equals("helloメッセージを返します") && 
          event.getLevel().toString().equals("DEBUG")) {
        found = true;
        break;
      }
    }
    assertTrue(found, "期待されるデバッグログメッセージが見つかりませんでした");
  }

  @Test
  public void testYeah() {
    final String s = HelloWorldUtil.returnYeah();
    assertEquals("Yeah", s);
  }

  @Test
  public void testHoge() {
    final String s = HelloWorldUtil.returnHoge();
    assertEquals("hoge", s);
  }
}
