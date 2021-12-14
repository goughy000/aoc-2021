package com.github.goughy000;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Collections2 {
  private static final Pattern SPLIT_PATTERN = Pattern.compile("[, \\->]+");

  public static <T> T first(Collection<T> collection) {
    if (null == collection || collection.isEmpty()) {
      return null;
    }
    return collection.iterator().next();
  }

  public static <T, R> List<R> mapList(List<T> input, Function<T, R> mapper) {
    return input.stream().map(mapper).toList();
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

  public static List<String> chars(String value) {
    if (null == value) {
      return emptyList();
    }
    return value.chars().mapToObj(i -> String.valueOf((char) i)).toList();
  }

  public static <T> List<T> parseList(String value, Function<String, T> converter) {
    if (null == value || value.isBlank()) {
      return emptyList();
    }

    return stream(SPLIT_PATTERN.split(value.strip())).map(converter).collect(toList());
  }

  public static <E> List<List<E>> permutations(List<E> original) {
    if (original.isEmpty()) {
      return List.of(emptyList());
    }

    var copy = new ArrayList<>(original);

    E firstElement = copy.remove(0);
    List<List<E>> returnValue = new ArrayList<>();
    List<List<E>> permutations = permutations(copy);
    for (List<E> smaller : permutations) {
      for (int index = 0; index <= smaller.size(); index++) {
        List<E> temp = new ArrayList<>(smaller);
        temp.add(index, firstElement);
        returnValue.add(temp);
      }
    }
    return returnValue;
  }

  public static <K, V> Map<K, V> transpose(Map<V, K> map) {
    return map.entrySet().stream().collect(toMap(Entry::getValue, Entry::getKey));
  }

  public static <T> List<T> concat(List<T> a, T b) {
    return concat(a, singletonList(b));
  }

  public static <T> List<T> concat(List<T> a, List<T> b) {
    return Stream.concat(a.stream(), b.stream()).toList();
  }

  public static <T> long count(List<T> list, T value) {
    return list.stream().filter(x -> Objects.equals(x, value)).count();
  }

  public static <T> T only(Collection<T> collection) {
    var size = collection.size();
    if (size != 1) {
      throw new IllegalStateException("collection contains " + size + " items");
    }
    return first(collection);
  }

  public static <T extends Comparable<? super T>> List<T> sort(List<T> list) {
    var sorted = new ArrayList<T>(list);
    Collections.sort(sorted);
    return sorted;
  }

  public static <K, V, R> Function<Entry<K, V>, R> mapEntry(BiFunction<K, V, R> function) {
    return entry -> function.apply(entry.getKey(), entry.getValue());
  }
}
