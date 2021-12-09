package com.github.goughy000;

import static java.util.Arrays.stream;
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
    return lowPoints(heights).map(heights::get).map(i -> i + 1).reduce(0, Integer::sum);
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

  private int count(Map<Point, Integer> heights, Point point) {
    return countRecursive(heights, point, new ArrayList<>());
  }

  private int countRecursive(Map<Point, Integer> heights, Point point, List<Point> checked) {
    checked.add(point);
    adjacent(point, heights)
        .filter(p -> heights.get(p) != 9)
        .filter(not(checked::contains))
        .filter(p -> heights.get(p) > heights.get(point))
        .forEach(p -> countRecursive(heights, p, checked));
    return checked.size();
  }

  private Stream<Point> lowPoints(Map<Point, Integer> heights) {
    return heights.keySet().stream()
        .filter(
            point -> adjacent(point, heights).allMatch(p -> heights.get(p) > heights.get(point)));
  }

  private Stream<Point> adjacent(Point point, Map<Point, ?> points) {
    return point.adjacentPoints().stream().filter(points::containsKey);
  }

  private Map<Point, Integer> heights() {
    var lines = input();

    return range(0, input().size())
        .boxed()
        .flatMap(
            y -> {
              var row = stream(lines.get(y).split("")).map(Integer::parseInt).toList();
              return range(0, row.size()).mapToObj(x -> entry(new Point(x, y), row.get(x)));
            })
        .collect(toMap(Entry::getKey, Entry::getValue));
  }
}
