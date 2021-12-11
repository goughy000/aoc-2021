package com.github.goughy000;

import static com.github.goughy000.Collections2.chars;
import static com.github.goughy000.Collections2.mapList;
import static java.util.stream.IntStream.iterate;
import static java.util.stream.IntStream.range;

import com.github.goughy000.geometry.Grid;

public class Day11 extends Solution {
  private static final int FLASH = 10;

  public static void main(String[] args) {
    new Day11().main();
  }

  @Override
  protected Integer part1() {
    var grid = grid();

    return range(0, 100).reduce(0, (x, $) -> x + step(grid));
  }

  @Override
  protected Integer part2() {
    var grid = grid();

    return iterate(1, i -> ++i).filter($ -> step(grid) == grid.size()).findFirst().orElseThrow();
  }

  private static int step(Grid<Integer> grid) {
    grid.forEach(location -> location.setValue(location.getValue() + 1));

    var flashes = 0;

    while (grid.containsValue(FLASH)) {
      for (var location : grid.locations()) {
        if (location.getValue() == FLASH) {

          location.setValue(0);
          flashes++;

          location
              .principals()
              .forEach(
                  adjacent -> {
                    var value = adjacent.getValue();
                    adjacent.setValue(value < FLASH && value != 0 ? value + 1 : value);
                  });
        }
      }
    }
    return flashes;
  }

  private Grid<Integer> grid() {
    return Grid.ofLines(input(), line -> mapList(chars(line), Integer::parseInt));
  }
}
