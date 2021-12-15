package com.github.goughy000.geometry;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.String.format;
import static java.util.stream.LongStream.range;

import java.util.List;
import java.util.stream.Stream;

public record Point(long x, long y) {
  public static final Point ZERO = new Point(0L, 0L);

  public static Point of(List<Integer> coordinates) {
    return new Point(coordinates.get(0), coordinates.get(1));
  }

  public Stream<Point> toPoint(Point b) {
    return range(0L, max(abs(x() - b.x()), abs(y() - b.y())) + 1L)
        .mapToObj(delta -> new Point(calc(x(), b.x(), delta), calc(y(), b.y(), delta)));
  }

  public double distanceTo(Point b) {
    return abs(x() - b.x()) + abs(y() - b.y());
  }

  public Stream<Point> cardinals() {
    return Stream.of(north(), east(), south(), west());
  }

  public Stream<Point> principals() {
    return Stream.of(
        north(), northEast(), east(), southEast(), south(), southWest(), west(), northWest());
  }

  public Point north() {
    return new Point(x, y - 1L);
  }

  public Point northEast() {
    return new Point(x + 1L, y - 1L);
  }

  public Point east() {
    return new Point(x + 1L, y);
  }

  public Point southEast() {
    return new Point(x + 1L, y + 1L);
  }

  public Point south() {
    return new Point(x, y + 1L);
  }

  public Point southWest() {
    return new Point(x - 1L, y + 1L);
  }

  public Point west() {
    return new Point(x - 1L, y);
  }

  public Point northWest() {
    return new Point(x - 1L, y - 1L);
  }

  public Point add(Point other) {
    return new Point(x() + other.x(), y() + other.y());
  }

  public Point subtract(Point other) {
    return new Point(x() - other.x(), y() - other.y());
  }

  private long calc(long a, long b, long delta) {
    return b > a ? a + delta : (b < a ? a - delta : a);
  }

  @Override
  public String toString() {
    return format("(%s, %s)", x, y);
  }
}
