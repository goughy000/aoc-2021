package com.github.goughy000;

import static com.github.goughy000.Collections2.chars;
import static com.github.goughy000.Collections2.mapList;
import static java.util.stream.LongStream.iterate;
import static java.util.stream.LongStream.range;

import com.github.goughy000.geometry.Grid;
import com.github.goughy000.geometry.Grid.GridLocation;

public class Day11 extends Solution {
  private static final int FLASH = 10;

  public static void main(String[] args) {
    new Day11().main();
  }

  @Override
  protected Long part1() {
    var grid = grid();

    return range(0, 100).reduce(0, (x, $) -> x + step(grid));
  }

  @Override
  protected Long part2() {
    var grid = grid();

    return iterate(1, i -> ++i).filter($ -> step(grid) == grid.size()).findFirst().orElseThrow();
  }

  private static long flash(Grid<Integer> grid) {
    if (!grid.containsValue(FLASH)) return 0;
    var charged = grid.locations().filter(o -> o.getValue() == FLASH).toList();
    charged.forEach(o -> o.setValue(0));
    charged.stream()
        .flatMap(GridLocation::principals)
        .filter(p -> p.getValue() != 0)
        .filter(p -> p.getValue() < FLASH)
        .forEach(p -> p.mergeValue(x -> ++x));

    flash(grid);
    return grid.values().filter(v -> v == 0).count();
  }

  private static long step(Grid<Integer> grid) {
    grid.forEach(o -> o.mergeValue(x -> ++x));
    return flash(grid);
  }

  private Grid<Integer> grid() {
    return Grid.ofLines(input(), line -> mapList(chars(line), Integer::parseInt));
  }
}
