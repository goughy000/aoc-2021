package com.github.goughy000;

import static com.github.goughy000.Collections2.parseInts;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;

import com.github.goughy000.geometry.Point;
import java.util.List;
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
    return new Line(Point.of(parts), Point.of(parts.subList(2, 4)));
  }

  private record Line(Point a, Point b) {
    private boolean equalAxis() {
      return a.x() == b.x() || a.y() == b.y();
    }

    private Stream<Point> points() {
      return a.toPoint(b);
    }
  }
}
