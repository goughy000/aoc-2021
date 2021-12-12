package com.github.goughy000;

import static com.github.goughy000.Collections2.*;
import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.joining;

import java.util.*;
import java.util.function.Predicate;
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
        .map(List::size)
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
                  line.output().stream().map(digits::get).map(String::valueOf).collect(joining()));
            })
        .reduce(0L, Long::sum);
  }

  public Map<List<String>, Integer> digits(List<List<String>> patterns) {
    var one = find(patterns, 2, $ -> true);
    var four = find(patterns, 4, $ -> true);
    var diff = four.stream().filter(x -> !one.contains(x)).toList();

    return transpose(
        Map.of(
            0, find(patterns, 6, x -> contains(x, one) && !contains(x, diff)),
            1, one,
            2, find(patterns, 5, x -> !contains(x, diff) && !contains(x, one)),
            3, find(patterns, 5, x -> contains(x, one)),
            4, four,
            5, find(patterns, 5, x -> contains(x, diff)),
            6, find(patterns, 6, x -> contains(x, diff) && !contains(x, one)),
            7, find(patterns, 3, $ -> true),
            8, find(patterns, 7, $ -> true),
            9, find(patterns, 6, x -> contains(x, one) && contains(x, diff))));
  }

  private Stream<Line> lines() {
    return input().stream().map(Line::parse);
  }

  private static List<String> find(
      List<List<String>> patterns, int size, Predicate<List<String>> filter) {
    return only(patterns.stream().filter(s -> s.size() == size).filter(filter).toList());
  }

  private static boolean contains(List<String> it, List<String> target) {
    return it.containsAll(target);
  }

  private record Line(List<List<String>> readings, List<List<String>> output) {
    private static Line parse(String input) {
      var split = input.split(" \\| ");
      return new Line(
          mapList(parseList(split[0]), s -> sort(chars(s))),
          mapList(parseList(split[1]), s -> sort(chars(s))));
    }
  }
}
