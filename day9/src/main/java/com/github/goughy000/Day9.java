package com.github.goughy000;

import static com.github.goughy000.Collections2.chars;
import static com.github.goughy000.Collections2.mapList;
import static java.util.Comparator.reverseOrder;
import static java.util.Map.entry;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;

import com.github.goughy000.geometry.Point;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class Day9 extends Solution {
  public static void main(String[] args) {
    new Day9().main();
  }

  @Override
  protected Integer part1() {
    var heights = heights();
    return lowPoints(heights).map(heights::get).map(i -> ++i).reduce(0, Integer::sum);
  }

  @Override
  protected Integer part2() {
    var heights = heights();
    return lowPoints(heights)
        .map(point -> count(heights, point))
        .sorted(reverseOrder())
        .limit(3)
        .reduce(1, (a, b) -> a * b);
  }

  private Map<Point, Integer> heights() {
    var lines = input();

    return range(0, lines.size())
        .boxed()
        .flatMap(
            y -> {
              var row = mapList(chars(lines.get(y)), Integer::parseInt);
              return range(0, row.size()).mapToObj(x -> entry(new Point(x, y), row.get(x)));
            })
        .collect(toMap(Entry::getKey, Entry::getValue));
  }

  private static Stream<Point> lowPoints(Map<Point, Integer> heights) {
    return heights.keySet().stream()
        .filter(
            point -> adjacent(point, heights).allMatch(p -> heights.get(p) > heights.get(point)));
  }

  private static int count(Map<Point, Integer> heights, Point point) {
    var checked = new ArrayList<Point>();
    check(heights, point, checked);
    return checked.size();
  }

  private static void check(Map<Point, Integer> heights, Point point, List<Point> checked) {
    checked.add(point);
    adjacent(point, heights)
        .filter(not(checked::contains))
        .filter(p -> heights.get(p) != 9)
        .filter(p -> heights.get(p) > heights.get(point))
        .forEach(p -> check(heights, p, checked));
  }

  private static Stream<Point> adjacent(Point point, Map<Point, ?> points) {
    return point.adjacentPoints().filter(points::containsKey);
  }
}
