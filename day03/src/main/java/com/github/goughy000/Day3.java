package com.github.goughy000;

import static com.github.goughy000.Collections2.first;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.iterate;

import java.util.*;
import java.util.Map.Entry;

public class Day3 extends Solution {

  public static void main(String[] args) {
    new Day3().main();
  }

  @Override
  protected Integer part1() {
    var stats = new HashMap<Integer, Integer>();

    input()
        .forEach(
            line ->
                range(0, line.length())
                    .forEach(pos -> stats.merge(pos, offset(line, pos), Integer::sum)));

    var value =
        fromBinary(
            stats.entrySet().stream()
                .sorted(comparingInt(Entry::getKey))
                .map(entry -> String.valueOf(pick(entry.getValue(), false)))
                .collect(joining()));

    return value * (value ^ 0xFFF);
  }

  @Override
  protected Integer part2() {
    return calculate(false) * calculate(true);
  }

  private int calculate(boolean inverse) {
    var values = input();

    iterate(0, x -> values.size() > 1, i -> ++i)
        .forEach(i -> values.removeIf(v -> popular(v, values, i, inverse)));

    return fromBinary(first(values));
  }

  private static boolean popular(String value, List<String> values, int index, boolean inverse) {
    return value.charAt(index) == pick(count(values, index), inverse);
  }

  private static int count(List<String> values, int index) {
    return values.stream().map(s -> offset(s, index)).reduce(0, Integer::sum);
  }

  private static char pick(int count, boolean inverse) {
    return (count < 0) ^ inverse ? '1' : '0';
  }

  private static int offset(String s, int index) {
    return s.charAt(index) == '1' ? 1 : -1;
  }

  private static int fromBinary(String bits) {
    return Integer.parseInt(bits, 2);
  }
}
