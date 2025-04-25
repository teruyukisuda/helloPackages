package jp.co.suda.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorldUtil {
  public static void sayHello() {
    log.info("Hello World!");
  }

  public static void sayHello(String name) {
    log.info("Hello {}", name);
  }
}
