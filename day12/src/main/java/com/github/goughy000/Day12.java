package com.github.goughy000;

import static com.github.goughy000.Collections2.*;
import static java.util.Collections.*;
import static java.util.function.Function.*;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public class Day12 extends Solution {
  private static final String START = "start";
  private static final String END = "end";

  public static void main(String[] args) {
    new Day12().main();
  }

  @Override
  protected Long part1() {
    return routes((path, next) -> !path.contains(next)).count();
  }

  @Override
  protected Long part2() {
    return routes(
            (path, next) ->
                !START.equals(next)
                    && (!path.contains(next)
                        || !path.stream()
                            .filter(not(this::isBig))
                            .collect(groupingBy(identity(), counting()))
                            .containsValue(2L)))
        .count();
  }

  private Stream<List<String>> routes(BiPredicate<List<String>, String> traverse) {
    var links =
        input().stream()
            .map(Collections2::parseList)
            .flatMap(parts -> Stream.of(parts, List.of(last(parts), first(parts))))
            .collect(groupingBy(Collections2::first, mapping(Collections2::last, toList())));
    return routes(START, links, emptyList(), traverse);
  }

  private Stream<List<String>> routes(
      String start,
      Map<String, List<String>> links,
      List<String> current,
      BiPredicate<List<String>, String> traverse) {
    var path = concat(current, start);
    return links.get(start).stream()
        .flatMap(
            next ->
                END.equals(next)
                    ? Stream.of(concat(path, next))
                    : isBig(next) || traverse.test(path, next)
                        ? routes(next, links, path, traverse)
                        : Stream.empty());
  }

  private boolean isBig(String name) {
    return Character.isUpperCase(name.charAt(0));
  }
}
