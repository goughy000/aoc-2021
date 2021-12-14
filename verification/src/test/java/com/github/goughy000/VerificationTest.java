package com.github.goughy000;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class VerificationTest {
  private static final String D13P2 =
      join(
          format("%n"),
          List.of(
              " ▒ ▒ ▒ . . ▒ . . . . ▒ ▒ ▒ . . . ▒ ▒ . . . . ▒ ▒ . . ▒ ▒ . . ▒ . . . . ▒ . . ▒ .",
              " ▒ . . ▒ . ▒ . . . . ▒ . . ▒ . ▒ . . ▒ . . . . ▒ . ▒ . . ▒ . ▒ . . . . ▒ . . ▒ .",
              " ▒ . . ▒ . ▒ . . . . ▒ ▒ ▒ . . ▒ . . . . . . . ▒ . ▒ . . . . ▒ . . . . ▒ . . ▒ .",
              " ▒ ▒ ▒ . . ▒ . . . . ▒ . . ▒ . ▒ . . . . . . . ▒ . ▒ . ▒ ▒ . ▒ . . . . ▒ . . ▒ .",
              " ▒ . ▒ . . ▒ . . . . ▒ . . ▒ . ▒ . . ▒ . ▒ . . ▒ . ▒ . . ▒ . ▒ . . . . ▒ . . ▒ .",
              " ▒ . . ▒ . ▒ ▒ ▒ ▒ . ▒ ▒ ▒ . . . ▒ ▒ . . . ▒ ▒ . . . ▒ ▒ ▒ . ▒ ▒ ▒ ▒ . . ▒ ▒ . ."));

  @TestFactory
  Stream<DynamicTest> tests() {
    return Stream.of(
            args(new Day1(), 1288L, 1311L),
            args(new Day2(), 1989014, 2006917119),
            args(new Day3(), 2972336, 3368358),
            args(new Day4(), 50008, 17408),
            args(new Day5(), 8060L, 21577L),
            args(new Day6(), 380243L, 1708791884591L),
            args(new Day7(), 328187, 91257582),
            args(new Day8(), 237L, 1009098L),
            args(new Day9(), 558, 882942),
            args(new Day10(), 318099L, 2389738699L),
            args(new Day11(), 1585L, 382L),
            args(new Day12(), 5874L, 153592L),
            args(new Day13(), 689L, D13P2),
            args(new Day14(), 3259L, 3459174981021L))
        .flatMap(
            a ->
                Stream.of(
                    test(a.solution(), "Part 1", Solution::part1, a.one()),
                    test(a.solution(), "Part 2", Solution::part2, a.two())));
  }

  private static DynamicTest test(
      Solution solution, String name, Function<Solution, Object> action, Object expected) {
    return dynamicTest(
        format("[%s] %s", solution.getClass().getSimpleName(), name),
        () -> {
          // when
          var answer = action.apply(solution);

          // then
          assertThat(answer).isEqualTo(expected);
        });
  }

  private static Arguments args(Solution solution, Object one, Object two) {
    return new Arguments(solution, one, two);
  }

  private record Arguments(Solution solution, Object one, Object two) {}
}
