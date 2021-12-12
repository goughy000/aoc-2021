package com.github.goughy000.geometry;

import static java.lang.String.format;
import static java.util.stream.Stream.iterate;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class Grid<T> {
  private final Map<Point, T> map;
  private final long width;
  private final long height;

  private Grid(Map<Point, T> map, long width, long height) {
    this.map = map;
    this.width = width;
    this.height = height;
  }

  public long getWidth() {
    return width;
  }

  public long getHeight() {
    return height;
  }

  public long size() {
    return width * height;
  }

  public T getValue(int x, int y) {
    return getValue(new Point(x, y));
  }

  public boolean containsPoint(int x, int y) {
    return containsPoint(new Point(x, y));
  }

  public boolean containsPoint(Point point) {
    return map.containsKey(point);
  }

  public boolean containsValue(T value) {
    return map.containsValue(value);
  }

  public T getValue(Point point) {
    checkBounds(point);
    return map.get(point);
  }

  public void setValue(Point point, T value) {
    checkBounds(point);
    map.put(point, value);
  }

  public void forEach(Consumer<? super GridLocation<T>> consumer) {
    locations().forEach(consumer);
  }

  public Stream<GridLine<T>> rows() {
    return iterate(Point.ZERO, map::containsKey, Point::south)
        .map(y -> iterate(y, map::containsKey, Point::east).map(this::location).toList())
        .map(GridLine::new);
  }

  public Stream<GridLine<T>> columns() {
    return iterate(Point.ZERO, map::containsKey, Point::east)
        .map(x -> iterate(x, map::containsKey, Point::south).map(this::location).toList())
        .map(GridLine::new);
  }

  public Stream<GridLine<T>> lines() {
    return Stream.concat(rows(), columns());
  }

  public Stream<T> values() {
    return map.values().stream();
  }

  public Stream<GridLocation<T>> locations() {
    return map.keySet().stream().map(this::location);
  }

  private GridLocation<T> location(Point point) {
    return new GridLocation<>(this, point);
  }

  private void checkBounds(Point point) {
    if (point.y() > height) {
      throw new IndexOutOfBoundsException(
          format("requested y (%s) is out of range 0->%s", point.y(), height));
    }
    if (point.x() > width) {
      throw new IndexOutOfBoundsException(
          format("requested x (%s) is out of range 0->%s", point.x(), width));
    }
  }

  public static <T, U> Grid<T> ofLines(List<U> lines, Function<U, List<T>> rowMapper) {
    var map = new HashMap<Point, T>();
    var width = 0L;
    for (int y = 0; y < lines.size(); y++) {
      var row = rowMapper.apply(lines.get(y));
      for (int x = 0; x < row.size(); x++) {
        map.put(new Point(x, y), row.get(x));
      }
      if (row.size() > width) {
        width = row.size();
      }
    }
    return new Grid<>(map, width, lines.size());
  }

  @Override
  public String toString() {
    var sb = new StringBuilder();

    var pad = 1 + values().map(Object::toString).map(String::length).reduce(0, Math::max);

    var point = Point.ZERO;
    while (map.containsKey(point)) {
      while (map.containsKey(point)) {
        var v = map.get(point);
        sb.append(format("%1$" + pad + "s", v));
        point = point.east();
      }
      sb.append(format("%n"));
      point = new Point(0, point.y() + 1);
    }

    return sb.toString();
  }

  public record GridLocation<T>(Grid<T> grid, Point point) {
    public T getValue() {
      return grid.getValue(point);
    }

    public void setValue(T value) {
      grid.setValue(point, value);
    }

    public void mergeValue(Function<T, T> merge) {
      setValue(merge.apply(getValue()));
    }

    public Stream<GridLocation<T>> cardinals() {
      return others(point.cardinals());
    }

    public Stream<GridLocation<T>> principals() {
      return others(point.principals());
    }

    private Stream<GridLocation<T>> others(Stream<Point> points) {
      return points.filter(grid::containsPoint).map(p -> new GridLocation<>(grid, p));
    }
  }

  public record GridLine<T>(List<GridLocation<T>> locations) {
    public Stream<T> values() {
      return locations.stream().map(GridLocation::getValue);
    }
  }
}
