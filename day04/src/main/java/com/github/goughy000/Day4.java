package com.github.goughy000;

import static com.github.goughy000.Collections2.*;
import static com.github.goughy000.Collectors2.chunked;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import com.github.goughy000.geometry.Grid;
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

  private Integer winningScore(List<Grid<Integer>> boards) {
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
    return range(SIZE, numbers.size()).mapToObj(index -> numbers.subList(0, index));
  }

  private List<Grid<Integer>> boards() {
    return input().stream()
        .skip(1)
        .filter(not(String::isBlank))
        .collect(chunked(SIZE))
        .map(chunk -> Grid.ofLines(chunk, Collections2::parseInts))
        .collect(toList());
  }

  private static boolean isWinner(Grid<Integer> board, List<Integer> picked) {
    return board.lines().map(line -> line.values().toList()).anyMatch(picked::containsAll);
  }

  private static int score(Grid<Integer> board, List<Integer> picked) {
    return last(picked) * board.values().filter(not(picked::contains)).reduce(0, Integer::sum);
  }
}
