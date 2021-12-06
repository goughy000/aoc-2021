package com.github.goughy000;

import static com.github.goughy000.Collections2.first;
import static com.github.goughy000.Collections2.parseInts;
import static java.util.Arrays.copyOfRange;

import java.util.stream.LongStream;

public class Day6 extends Solution {

  public static void main(String[] args) {
    new Day6().main();
  }

  @Override
  protected Long part1() {
    return calculate(80);
  }

  @Override
  protected Long part2() {
    return calculate(256);
  }

  private long calculate(int days) {
    var fish = new long[9];
    for (int n : parseInts(first(input()))) {
      fish[n]++;
    }

    for (var i = 0; i < days; i++) {
      var n = fish[0];
      fish = copyOfRange(fish, 1, 10);
      fish[6] += fish[8] = n;
    }
    return LongStream.of(fish).sum();
  }
}
