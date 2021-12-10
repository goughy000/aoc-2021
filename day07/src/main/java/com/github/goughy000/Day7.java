package com.github.goughy000;

import static com.github.goughy000.Collections2.first;
import static com.github.goughy000.Collections2.parseInts;
import static java.lang.Math.abs;
import static java.util.stream.IntStream.range;

import java.util.function.IntUnaryOperator;

public class Day7 extends Solution {

  public static void main(String[] args) {
    new Day7().main();
  }

  @Override
  protected Integer part1() {
    return calculate(d -> d);
  }

  @Override
  protected Integer part2() {
    return calculate(this::triangular);
  }

  private int calculate(IntUnaryOperator cost) {
    var inputs = parseInts(first(input()));
    int max = inputs.stream().reduce(0, Math::max);

    return range(0, max)
        .map(
            h ->
                inputs.stream()
                    .map(input -> cost.applyAsInt(abs(input - h)))
                    .reduce(0, Integer::sum))
        .min()
        .orElseThrow();
  }

  private int triangular(int i) {
    return i * (i + 1) / 2;
  }
}
