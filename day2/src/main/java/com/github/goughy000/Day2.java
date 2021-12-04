package com.github.goughy000;

import static com.github.goughy000.Collections2.mapList;
import static java.lang.Integer.parseInt;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class Day2 extends Solution {

  public static void main(String[] args) {
    new Day2().main();
  }

  @Override
  protected Integer part1() {
    return calculate(
        Map.of(
            "forward", i -> p -> p.horizontal += i,
            "down", i -> p -> p.depth += i,
            "up", i -> p -> p.depth -= i));
  }

  @Override
  protected Integer part2() {
    return calculate(
        Map.of(
            "forward",
                i ->
                    p -> {
                      p.horizontal += i;
                      p.depth += p.aim * i;
                    },
            "down", i -> p -> p.aim += i,
            "up", i -> p -> p.aim -= i));
  }

  private int calculate(Map<String, Function<Integer, Consumer<Position>>> ops) {
    var p = new Position();
    mapList(input(), Collections2::parseList)
        .forEach(parts -> ops.get(parts.get(0)).apply(parseInt(parts.get(1))).accept(p));

    return p.horizontal * p.depth;
  }

  private static class Position {
    private int horizontal;
    private int depth;
    private int aim;
  }
}
