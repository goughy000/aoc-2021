package com.github.goughy000;

import static com.github.goughy000.Collections2.first;
import static com.github.goughy000.Collections2.parseInts;
import static java.lang.Integer.parseInt;

import com.github.goughy000.geometry.Axis;
import com.github.goughy000.geometry.Grid;
import com.github.goughy000.geometry.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class Day13 extends Solution {
  private static final String DOT = "\u2592";

  public static void main(String[] args) {
    new Day13().main();
  }

  @Override
  protected Long part1() {
    return solve(
        (grid, instructions) ->
            fold(grid, first(instructions)).values().filter(DOT::equals).count());
  }

  @Override
  protected String part2() {
    return solve(
        (grid, instructions) -> {
          for (var instruction : instructions) {
            grid = grid.fold(instruction.axis(), instruction.number(), (s, s2) -> DOT);
          }

          return grid.toString();
        });
  }

  private static Grid<String> fold(Grid<String> grid, Instruction instruction) {
    return grid.fold(instruction.axis(), instruction.number(), (s, s2) -> DOT);
  }

  private <T> T solve(BiFunction<Grid<String>, List<Instruction>, T> function) {
    var blanked = false;
    var points = new ArrayList<Point>();
    var instructions = new ArrayList<Instruction>();
    for (var line : input()) {
      if (line.isBlank()) {
        blanked = true;
        continue;
      }
      if (blanked) {
        instructions.add(Instruction.parse(line));
      } else {
        points.add(Point.of(parseInts(line)));
      }
    }
    return function.apply(Grid.ofPoints(points, $ -> DOT), instructions);
  }

  private record Instruction(Axis axis, int number) {
    private static final Pattern PATTERN = Pattern.compile("([xy])=(\\d+)");

    private static Instruction parse(String line) {
      var matcher = PATTERN.matcher(line);
      if (!matcher.find()) throw new IllegalArgumentException("Invalid instruction line " + line);
      return new Instruction(Axis.fromString(matcher.group(1)), parseInt(matcher.group(2)));
    }
  }
}
