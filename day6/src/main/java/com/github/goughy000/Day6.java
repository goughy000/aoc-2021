package com.github.goughy000;

import static com.github.goughy000.Collections2.first;
import static com.github.goughy000.Collections2.parseInts;
import static java.lang.System.arraycopy;

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
      var shifted = new long[9];
      arraycopy(fish, 1, shifted, 0, 8);
      shifted[6] += fish[0];
      shifted[8] = fish[0];
      fish = shifted;
    }
    return LongStream.of(fish).sum();
  }
}
