package com.github.teruyukisuda;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorldUtil {

  public static String returnHello() {
    log.debug("helloメッセージを返します");
    return "Hello";
  }

  public static String returnBye() {
    log.debug("byeメッセージを返します");
    return "Bye";
  }

  public static String returnYeah() {
    log.debug("yeahメッセージを返します");
    return "Yeah";
  }

  public static String returnHoge() {
    log.debug("hogeメッセージを返します");
    return "hoge";
  }
}
