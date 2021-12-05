package com.github.goughy000;

import static com.github.goughy000.Collections2.parseInts;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day5 extends Solution {
  public static void main(String[] args) {
    new Day5().main();
  }

  @Override
  protected Long part1() {
    return count(Line::equalAxis);
  }

  @Override
  protected Long part2() {
    return count(x -> true);
  }

  private long count(Predicate<Line> filter) {
    return input().stream()
        .map(s -> parse(parseInts(s)))
        .filter(filter)
        .flatMap(Line::points)
        .collect(groupingBy(identity()))
        .values()
        .stream()
        .filter(l -> l.size() > 1)
        .count();
  }

  private Line parse(List<Integer> parts) {
    return new Line(new Point(parts.get(0), parts.get(1)), new Point(parts.get(2), parts.get(3)));
  }

  private static record Point(int x, int y) {}

  private static record Line(Point a, Point b) {
    private boolean equalAxis() {
      return a.x == b.x || a.y == b.y;
    }

    private Stream<Point> points() {
      return range(0, max(abs(a.x() - b.x()), abs(a.y() - b.y())) + 1)
          .mapToObj(delta -> new Point(calc(delta, Point::x), calc(delta, Point::y)));
    }

    private int calc(int delta, Function<Point, Integer> which) {
      int a1 = which.apply(a);
      int b1 = which.apply(b);
      return b1 > a1 ? a1 + delta : (b1 < a1 ? a1 - delta : a1);
    }
  }
}
