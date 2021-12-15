package com.github.goughy000;

import static com.github.goughy000.Collections2.chars;
import static com.github.goughy000.Collections2.mapList;

import com.github.goughy000.geometry.Grid;
import com.github.goughy000.geometry.Point;

public class Day15 extends Solution {
  public static void main(String[] args) {
    new Day15().main();
  }

  @Override
  protected Long part1() {
    return solve(grid());
  }

  @Override
  protected Long part2() {
    var grid = grid();
    long width = grid.getWidth();
    long height = grid.getHeight();

    var resized =
        grid.resize(
            width * 5,
            height * 5,
            p -> {
              var x = p.x() % width;
              var y = p.y() % height;
              var i = (p.x() / width) + (p.y() / height);

              var v = grid.getValue(new Point(x, y)) + i;

              return v > 9 ? v - 9 : v;
            });

    return solve(resized);
  }

  private long solve(Grid<Long> grid) {
    return grid.aStar(
        Point.ZERO,
        new Point(grid.getWidth() - 1, grid.getHeight() - 1),
        ($, p) -> grid.getValue(p));
  }

  private Grid<Long> grid() {
    return Grid.ofLines(input(), line -> mapList(chars(line), Long::parseLong));
  }
}
