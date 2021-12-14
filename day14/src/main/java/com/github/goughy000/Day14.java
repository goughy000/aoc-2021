package com.github.goughy000;

import static com.github.goughy000.Collections2.*;
import static java.util.Collections.*;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class Day14 extends Solution {
  public static void main(String[] args) {
    new Day14().main();
  }

  @Override
  protected Object part1() {
    return solve(10);
  }

  @Override
  protected Object part2() {
    return solve(40);
  }

  private Long solve(int iterations) {
    var input = input();
    var polymer = chars(first(input));

    var map =
        range(1, polymer.size())
            .boxed()
            .collect(toMap(i -> new Pair(polymer.get(i - 1), polymer.get(i)), $ -> 1L));

    var instructions =
        input.subList(2, input.size()).stream()
            .map(s -> parseList(s, Collections2::chars))
            .collect(
                toMap(
                    split -> new Pair(first(first(split)), last(first(split))),
                    split -> only(last(split))));

    for (int i = 0; i < iterations; i++) {
      map =
          map.entrySet().stream()
              .flatMap(
                  entry -> {
                    var pair = entry.getKey();
                    return pair.splitWith(instructions.get(pair)).stream()
                        .map(p -> entry(p, entry.getValue()));
                  })
              .collect(toMap(Entry::getKey, Entry::getValue, Long::sum));
    }

    var counts =
        Stream.concat(
                Stream.of(entry(first(polymer), 1L)),
                map.entrySet().stream()
                    .map(entry -> entry(entry.getKey().right(), entry.getValue())))
            .collect(toMap(Entry::getKey, Entry::getValue, Long::sum))
            .values();

    return max(counts) - min(counts);
  }

  private record Pair(String left, String right) {
    private List<Pair> splitWith(String insert) {
      return List.of(new Pair(left, insert), new Pair(insert, right));
    }
  }
}
