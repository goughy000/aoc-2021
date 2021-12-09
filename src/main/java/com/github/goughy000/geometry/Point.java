package com.github.goughy000.geometry;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.stream.Stream;

public record Point(int x, int y) {
  public static Point of(List<Integer> coordinates) {
    return new Point(coordinates.get(0), coordinates.get(1));
  }

  public Stream<Point> toPoint(Point b) {
    return range(0, max(abs(x() - b.x()), abs(y() - b.y())) + 1)
        .mapToObj(delta -> new Point(calc(x(), b.x(), delta), calc(y(), b.y(), delta)));
  }

  public List<Point> adjacentPoints() {
    return List.of(north(), east(), south(), west());
  }

  public Point north() {
    return new Point(x, y + 1);
  }

  public Point east() {
    return new Point(x + 1, y);
  }

  public Point south() {
    return new Point(x, y - 1);
  }

  public Point west() {
    return new Point(x - 1, y);
  }

  public Point add(Point other) {
    return new Point(x() + other.x(), y() + other.y());
  }

  public Point subtract(Point other) {
    return new Point(x() - other.x(), y() - other.y());
  }

  private int calc(int a, int b, int delta) {
    return b > a ? a + delta : (b < a ? a - delta : a);
  }
}
