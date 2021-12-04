package com.github.goughy000;

import static com.github.goughy000.Collections2.last;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Collectors2 {

  public static <T> Collector<T, List<List<T>>, Stream<List<T>>> chunked(int size) {
    return Collector.of(
        ArrayList::new,
        (chunks, item) -> chunk(chunks, size).add(item),
        Collectors2::unsupportedParallel,
        Collection::stream);
  }

  public static <T> Collector<T, List<List<T>>, Stream<List<T>>> windowed(int size) {
    return Collector.of(
        ArrayList::new,
        (chunks, item) -> {
          chunks.add(new ArrayList<>());
          for (var chunk : chunks) {
            if (chunk.size() < size) {
              chunk.add(item);
            }
          }
        },
        Collectors2::unsupportedParallel,
        chunks -> {
          chunks.removeIf(l -> l.size() != size);
          return chunks.stream();
        });
  }

  private static <T> T unsupportedParallel(T x, T y) {
    throw new UnsupportedOperationException("parallel not supported");
  }

  private static <T> List<T> chunk(List<List<T>> chunks, int size) {
    var list = last(chunks);
    if (null == list || list.size() >= size) {
      var chunk = new ArrayList<T>(size);
      chunks.add(chunk);
      return chunk;
    }
    return list;
  }
}
