package com.github.goughy000;

import static com.github.goughy000.Collections2.*;
import static com.github.goughy000.Collectors2.chunked;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.stream.Stream;

public class Day4 extends Solution {
  private static final int SIZE = 5;

  public static void main(String[] args) {
    new Day4().main();
  }

  @Override
  protected Integer part1() {
    return winningScore(boards());
  }

  @Override
  protected Integer part2() {
    var boards = boards();

    numbers()
        .takeWhile(x -> boards.size() > 1)
        .forEach(picked -> boards.removeIf(board -> isWinner(board, picked)));

    return winningScore(boards);
  }

  private Integer winningScore(List<List<List<Integer>>> boards) {
    return numbers()
        .flatMap(
            picked ->
                boards.stream()
                    .filter(board -> isWinner(board, picked))
                    .map(board -> score(board, picked)))
        .findFirst()
        .orElseThrow();
  }

  private Stream<List<Integer>> numbers() {
    var numbers = parseInts(first(input()));
    return range(0, numbers.size()).mapToObj(index -> numbers.subList(0, index));
  }

  private List<List<List<Integer>>> boards() {
    return input().stream()
        .skip(1)
        .filter(not(String::isBlank))
        .collect(chunked(SIZE))
        .map(chunk -> mapList(chunk, Collections2::parseInts))
        .collect(toList());
  }

  private static boolean isWinner(List<List<Integer>> board, List<Integer> picked) {
    return range(0, SIZE)
        .anyMatch(
            x ->
                picked.containsAll(mapList(board, r -> r.get(x)))
                    || picked.containsAll(board.get(x)));
  }

  private static int score(List<List<Integer>> board, List<Integer> picked) {
    return last(picked)
        * board.stream()
            .flatMap(r -> r.stream().filter(not(picked::contains)))
            .reduce(0, Integer::sum);
  }
}
