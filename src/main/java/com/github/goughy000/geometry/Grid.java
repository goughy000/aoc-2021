package com.github.goughy000.geometry;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class Grid<T> {
  private final Map<Point, T> map;
  private final int width;
  private final int height;

  private Grid(Map<Point, T> map, int width, int height) {
    this.map = map;
    this.width = width;
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int size() {
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

  public void forEach(Consumer<GridLocation<T>> consumer) {
    map.forEach((p, v) -> consumer.accept(new GridLocation<>(this, p)));
  }

  public Set<GridLocation<T>> locations() {
    return map.keySet().stream().map(e -> new GridLocation<>(this, e)).collect(toSet());
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
    var width = 0;
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

    var point = new Point(0, 0);
    while (map.containsKey(point)) {
      while (map.containsKey(point)) {
        var v = map.get(point);
        sb.append(v);
        point = point.add(new Point(1, 0));
      }
      sb.append(String.format("%n"));
      point = new Point(0, point.y() + 1);
    }

    return sb.toString();
  }

  public record GridLocation<T>(Grid<T> grid, Point point) {
    public T getValue() {
      return grid.getValue(this.point);
    }

    public void setValue(T value) {
      grid.setValue(this.point, value);
    }

    public Stream<GridLocation<T>> cardinals() {
      return point.cardinals().filter(grid::containsPoint).map(p -> new GridLocation<>(grid, p));
    }

    public Stream<GridLocation<T>> principals() {
      return point.principals().filter(grid::containsPoint).map(p -> new GridLocation<>(grid, p));
    }
  }
}
