package com.github.goughy000;

import static com.github.goughy000.Collections2.mapList;
import static com.github.goughy000.Collections2.parseList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Day8 extends Solution {

  public static void main(String[] args) {
    new Day8().main();
  }

  @Override
  protected Long part1() {

    return input().stream()
        .map(s -> s.split(" \\| ")[1])
        .map(Collections2::parseList)
        .flatMap(Collection::stream)
        .filter(s -> List.of(2, 3, 4, 7).contains(s.length()))
        .count();
  }

  public <E> List<List<E>> generatePerms(List<E> original) {
    if (original.isEmpty()) {
      List<List<E>> result = new ArrayList<>();
      result.add(new ArrayList<>());
      return result;
    }
    E firstElement = original.remove(0);
    List<List<E>> returnValue = new ArrayList<>();
    List<List<E>> permutations = generatePerms(original);
    for (List<E> smallerPermutated : permutations) {
      for (int index = 0; index <= smallerPermutated.size(); index++) {
        List<E> temp = new ArrayList<>(smallerPermutated);
        temp.add(index, firstElement);
        returnValue.add(temp);
      }
    }
    return returnValue;
  }

  public List<String> digits(List<String> patterns) {
    for (var perm : generatePerms(new ArrayList<>(List.of("a", "b", "c", "d", "e", "f", "g")))) {
      var zero = mask(perm, "abcefg");
      var one = mask(perm, "cf");
      var two = mask(perm, "acdeg");
      var three = mask(perm, "acdfg");
      var four = mask(perm, "bcdf");
      var five = mask(perm, "abdfg");
      var six = mask(perm, "abdefg");
      var seven = mask(perm, "acf");
      var eight = mask(perm, "abcdefg");
      var nine = mask(perm, "abcdfg");
      var maybe = List.of(zero, one, two, three, four, five, six, seven, eight, nine);
      if (patterns.containsAll(maybe)) {
        return maybe;
      }
    }
    throw new RuntimeException("oh dear");
  }

  private String mask(List<String> perm, String pattern) {
    StringBuilder x = new StringBuilder();
    for (char c : pattern.toCharArray()) {
      x.append(perm.get(c - 'a'));
    }
    return order(x.toString());
  }

  @Override
  protected Long part2() {
    long total = 0;
    for (var line : input()) {

      var split = line.split(" \\| ");

      var examples = mapList(parseList(split[0]), this::order);
      var digits = digits(examples);

      var right =
          Integer.valueOf(
              parseList(split[1]).stream()
                  .map(this::order)
                  .map(digits::indexOf)
                  .map(String::valueOf)
                  .collect(Collectors.joining()));

      total += right;
    }

    return total;

    //    input().stream()
    //        .map(s -> s.split(" \\| ")[0])
    //        .map(Collections2::parseList)
    //        .map(l -> mapList(l, this::order))
    //        .map(this::digits)
    //        .toList();
    //
    //    return input().stream()
    //        .map(s -> s.split(" \\| ")[1])
    //        .map(Collections2::parseList)
    //        .map( s -> Integer.valueOf(s.stream()
    //            .map(this::order)
    //                .peek(System.out::println)
    //            .map(digits::indexOf)
    //                .peek(System.out::println)
    //            .map(String::valueOf).collect(Collectors.joining())) )
    //        .peek(System.out::println)
    //        .reduce(0, Integer::sum);

    //    return null;

    //    var patterns = mapList(List.of("cagedb", "ab", "gcdfa", "fbcad", "eafb", "cdfbe",
    // "cdfgeb", "dab", "acedgfb", "cefabd"), this::order);
    //
    //    return input().stream()
    //        .map(s -> s.split(" \\| ")[1])
    //        .map(Collections2::parseList)
    //        .map( s -> Integer.valueOf(s.stream()
    //            .map(this::order)
    //                .peek(System.out::println)
    //            .map(patterns::indexOf)
    //                .peek(System.out::println)
    //            .map(String::valueOf).collect(Collectors.joining())) )
    //        .peek(System.out::println)
    //        .reduce(0, Integer::sum);
  }

  private String order(String s) {
    return Arrays.stream(s.split("")).sorted().collect(Collectors.joining());
  }
}
