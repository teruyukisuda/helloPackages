package com.github.teruyukisuda;

public class CalcUtil {

  public static int add(int a, int b) {
    if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) {
      throw new ArithmeticException("加算オーバーフロー: 入力値の一つが大きすぎます。");
    }
    return a + b;
  }

  public static int subtract(int a, int b) {
    if (a == Integer.MIN_VALUE || b == Integer.MAX_VALUE) {
      throw new ArithmeticException("減算アンダーフロー: 入力値の一つが極端すぎます。");
    }
    return a - b;
  }

  public static int multiply(int a, int b) {
    // 最初に特殊なケースを処理する
    if (a == 0 || b == 0) {
      return 0;
    }

    // aとbの符号に基づいてオーバーフローをチェックする
    if (a > 0 && b > 0) {
      // 両方正: a > MAX_VALUE / b かどうかをチェック
      if (a > Integer.MAX_VALUE / b) {
        throw new ArithmeticException("乗算オーバーフロー: 結果が範囲外です。");
      }
    } else if (a < 0 && b < 0) {
      // 両方負: a < MIN_VALUE / b（これは正）かどうかをチェック
      if (a < Integer.MAX_VALUE / b) {
        throw new ArithmeticException("乗算オーバーフロー: 結果が範囲外です。");
      }
    } else if (a < 0) {
      // a負、b正: a < MIN_VALUE / b かどうかをチェック
      if (a < Integer.MIN_VALUE / b) {
        throw new ArithmeticException("乗算オーバーフロー: 結果が範囲外です。");
      }
    } else {
      // a正、b負: b < MIN_VALUE / a かどうかをチェック
      if (b < Integer.MIN_VALUE / a) {
        throw new ArithmeticException("乗算オーバーフロー: 結果が範囲外です。");
      }
    }

    return a * b;
  }

  public static double divide(int a, int b) {
    if (b == 0) {
      throw new IllegalArgumentException(
          "ゼロによる除算は許可されていません。ゼロ以外の除数を指定してください。");
    }
    return (double) a / b;
  }
}
