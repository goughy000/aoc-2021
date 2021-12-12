package com.github.goughy000;

import static com.github.goughy000.Collections2.*;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toMap;

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
    return routes(links(), (path, next) -> isBig(next) || !path.contains(next)).count();
  }

  @Override
  protected Long part2() {
    var links = links();

    return links.keySet().stream()
        .filter(not(this::isTerminal))
        .filter(not(this::isBig))
        .flatMap(
            cave ->
                routes(
                    links,
                    (path, next) ->
                        isBig(next)
                            || !path.contains(next)
                            || (cave.equals(next) && count(path, next) == 1L)))
        .distinct()
        .count();
  }

  private boolean isBig(String name) {
    return name.toUpperCase().equals(name);
  }

  private boolean isTerminal(String name) {
    return START.equals(name) || END.equals(name);
  }

  private static Stream<List<String>> routes(
      Map<String, List<String>> links, BiPredicate<List<String>, String> traverse) {
    return routes(START, END, links, emptyList(), traverse);
  }

  private static Stream<List<String>> routes(
      String start,
      String end,
      Map<String, List<String>> links,
      List<String> current,
      BiPredicate<List<String>, String> traverse) {
    var path = concat(current, start);

    return links.get(start).stream()
        .flatMap(
            next -> {
              if (end.equals(next)) {
                return Stream.of(concat(path, next));
              } else if (traverse.test(current, next)) {
                return routes(next, end, links, path, traverse);
              }
              return Stream.empty();
            });
  }

  private Map<String, List<String>> links() {
    return input().stream()
        .map(Collections2::parseList)
        .flatMap(parts -> Stream.of(parts, List.of(last(parts), first(parts))))
        .collect(
            toMap(Collections2::first, parts -> singletonList(last(parts)), Collections2::concat));
  }
}
