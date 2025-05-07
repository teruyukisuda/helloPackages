package com.github.teruyukisuda;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorldUtil {

  public static String returnHello() {
    log.debug("Returning hello message");
    return "Hello";
  }

  public static String returnBye() {
    log.debug("Returning bye message");
    return "Bye";
  }

  public static String returnYeah() {
    log.debug("Returning yeah message");
    return "Yeah";
  }

}
