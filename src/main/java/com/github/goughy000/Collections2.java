package com.github.goughy000;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Collections2 {
  private static final Pattern SPLIT_PATTERN = Pattern.compile("[, ]+");

  public static <T> T first(Collection<T> collection) {
    if (null == collection || collection.isEmpty()) {
      return null;
    }
    return collection.iterator().next();
  }

  public static <T, R> List<R> mapList(List<T> input, Function<T, R> mapper) {
    return input.stream().map(mapper).collect(toList());
  }

  public static <T> T last(List<T> list) {
    if (null == list || list.isEmpty()) {
      return null;
    }
    return list.get(list.size() - 1);
  }

  public static List<String> parseList(String value) {
    return parseList(value, s -> s);
  }

  public static List<Integer> parseInts(String value) {
    return parseList(value, Integer::parseInt);
  }

  public static <T> List<T> parseList(String value, Function<String, T> converter) {
    if (null == value || value.isBlank()) {
      return Collections.emptyList();
    }

    return stream(SPLIT_PATTERN.split(value.strip())).map(converter).collect(toList());
  }
}
