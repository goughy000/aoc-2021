package com.github.goughy000;

import static com.github.goughy000.Collections2.mapList;
import static java.lang.Integer.parseInt;

import java.util.Map;
import java.util.function.BiConsumer;

public class Day2 extends Solution {

  public static void main(String[] args) {
    new Day2().main();
  }

  @Override
  protected Integer part1() {
    return calculate(
        Map.of(
            "forward", (p, i) -> p.horizontal += i,
            "down", (p, i) -> p.depth += i,
            "up", (p, i) -> p.depth -= i));
  }

  @Override
  protected Integer part2() {
    return calculate(
        Map.of(
            "forward",
                (p, i) -> {
                  p.horizontal += i;
                  p.depth += p.aim * i;
                },
            "down", (p, i) -> p.aim += i,
            "up", (p, i) -> p.aim -= i));
  }

  private int calculate(Map<String, BiConsumer<Position, Integer>> ops) {
    var p = new Position();
    mapList(input(), Collections2::parseList)
        .forEach(parts -> ops.get(parts.get(0)).accept(p, parseInt(parts.get(1))));

    return p.horizontal * p.depth;
  }

  private static class Position {
    private int horizontal;
    private int depth;
    private int aim;
  }
}
