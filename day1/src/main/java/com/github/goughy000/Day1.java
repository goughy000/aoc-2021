package com.github.goughy000;

import static com.github.goughy000.Collections2.mapList;
import static com.github.goughy000.Collectors2.windowed;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.List;

public class Day1 extends Solution {

  public static void main(String[] args) {
    new Day1().main();
  }

  private List<Integer> numbers() {
    return mapList(input(), Integer::valueOf);
  }

  @Override
  protected Long part1() {
    return countIncrease(numbers());
  }

  @Override
  protected Long part2() {
    return countIncrease(
        numbers().stream()
            .collect(windowed(3))
            .map(window -> window.stream().reduce(0, Integer::sum))
            .collect(toList()));
  }

  private long countIncrease(List<Integer> numbers) {
    return range(1, numbers.size()).filter(i -> numbers.get(i) > numbers.get(--i)).count();
  }
}
