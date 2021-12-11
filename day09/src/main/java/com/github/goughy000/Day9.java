package com.github.goughy000;

import static com.github.goughy000.Collections2.chars;
import static com.github.goughy000.Collections2.mapList;
import static java.util.Comparator.reverseOrder;
import static java.util.function.Predicate.not;

import com.github.goughy000.geometry.Grid;
import com.github.goughy000.geometry.Grid.GridLocation;
import java.util.*;
import java.util.stream.Stream;

public class Day9 extends Solution {
  public static void main(String[] args) {
    new Day9().main();
  }

  @Override
  protected Integer part1() {
    return lowPoints().map(l -> l.getValue() + 1).reduce(0, Integer::sum);
  }

  @Override
  protected Integer part2() {
    return lowPoints()
        .map(location -> basinSize(location, new HashSet<>()))
        .sorted(reverseOrder())
        .limit(3)
        .reduce(1, (a, b) -> a * b);
  }

  private Stream<GridLocation<Integer>> lowPoints() {
    return Grid.ofLines(input(), r -> mapList(chars(r), Integer::parseInt))
        .locations()
        .filter(l -> l.cardinals().allMatch(a -> a.getValue() > l.getValue()));
  }

  private static int basinSize(GridLocation<Integer> location, Set<GridLocation<Integer>> checked) {
    checked.add(location);

    location
        .cardinals()
        .filter(not(checked::contains))
        .filter(l -> l.getValue() != 9)
        .filter(l -> l.getValue() > location.getValue())
        .forEach(l -> basinSize(l, checked));
    return checked.size();
  }
}
