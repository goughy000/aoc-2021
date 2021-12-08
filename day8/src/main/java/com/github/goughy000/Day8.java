package com.github.goughy000;

import static com.github.goughy000.Collections2.*;
import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day8 extends Solution {

  public static void main(String[] args) {
    new Day8().main();
  }

  @Override
  protected Long part1() {
    var counts = List.of(2, 3, 4, 7);
    return lines()
        .flatMap(line -> line.output().stream())
        .map(String::length)
        .filter(counts::contains)
        .count();
  }

  @Override
  protected Long part2() {
    return lines()
        .map(
            line -> {
              var digits = digits(line.readings());

              return parseLong(
                  line.output().stream()
                      .map(digits::indexOf)
                      .map(String::valueOf)
                      .collect(joining()));
            })
        .reduce(0L, Long::sum);
  }

  public List<String> digits(List<String> patterns) {
    return permutations(List.of("a", "b", "c", "d", "e", "f", "g")).stream()
        .map(
            perm ->
                List.of(
                    mask(perm, "abcefg"),
                    mask(perm, "cf"),
                    mask(perm, "acdeg"),
                    mask(perm, "acdfg"),
                    mask(perm, "bcdf"),
                    mask(perm, "abdfg"),
                    mask(perm, "abdefg"),
                    mask(perm, "acf"),
                    mask(perm, "abcdefg"),
                    mask(perm, "abcdfg")))
        .filter(patterns::containsAll)
        .findFirst()
        .orElseThrow();
  }

  private String mask(List<String> perm, String pattern) {
    var x = new StringBuilder();
    for (char c : pattern.toCharArray()) {
      x.append(perm.get(c - 'a'));
    }
    return order(x.toString());
  }

  private Stream<Line> lines() {
    return input().stream().map(Line::parse);
  }

  private record Line(List<String> readings, List<String> output) {
    private static Line parse(String input) {
      var split = input.split(" \\| ");
      return new Line(
          mapList(parseList(split[0]), Day8::order), mapList(parseList(split[1]), Day8::order));
    }
  }

  private static String order(String s) {
    return Arrays.stream(s.split("")).sorted().collect(joining());
  }
}
