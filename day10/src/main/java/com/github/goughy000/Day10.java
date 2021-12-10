package com.github.goughy000;

import static com.github.goughy000.Collections2.transpose;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

public class Day10 extends Solution {
  private static final Map<Character, Character> REVERSE =
      transpose(Map.of('(', ')', '[', ']', '{', '}', '<', '>'));

  public static void main(String[] args) {
    new Day10().main();
  }

  @Override
  protected Long part1() {
    var scores = Map.of(')', 3L, ']', 57L, '}', 1197L, '>', 25137L);

    return score(scores::get, x -> 0L).reduce(0L, Long::sum);
  }

  @Override
  protected Long part2() {
    var scores = Map.of('(', 1, '[', 2, '{', 3, '<', 4);

    var results =
        score(
                x -> 0L,
                stack -> {
                  var score = 0L;
                  while (!stack.isEmpty()) {
                    score *= 5L;
                    score += scores.get(stack.removeLast());
                  }

                  return score;
                })
            .filter(score -> score > 0L)
            .sorted()
            .toList();

    return results.get((results.size() / 2));
  }

  private Stream<Long> score(Scorer<Character> onInvalid, Scorer<Deque<Character>> onIncomplete) {
    return input().stream()
        .map(String::toCharArray)
        .map(
            chars -> {
              var stack = new LinkedList<Character>();
              for (var c : chars) {
                if (REVERSE.containsKey(c)) {
                  if (stack.removeLast() != REVERSE.get(c)) {
                    return onInvalid.applyAsLong(c);
                  }
                } else {
                  stack.addLast(c);
                }
              }

              return onIncomplete.applyAsLong(stack);
            });
  }

  private interface Scorer<T> extends ToLongFunction<T> {}
}
