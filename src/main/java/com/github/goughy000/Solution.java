package com.github.goughy000;

import static java.lang.String.join;
import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;
import static java.util.Collections.nCopies;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;

public abstract class Solution {
  private static final String DEFAULT_INPUT = "input.txt";

  protected List<String> input() {
    return input(DEFAULT_INPUT);
  }

  protected List<String> input(String file) {
    try {
      var url = getClass().getResource(file);
      return readAllLines(Path.of(URI.create(url.toString())));
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  protected abstract Object part1();

  protected abstract Object part2();

  public void main() {
    delimiter("=");
    print("Advent Of Code %s", getClass().getSimpleName());
    delimiter("-");
    print("Part 1: %s", part1());
    print("Part 2: %s", part2());
    delimiter("=");
  }

  private void print(String s, Object... args) {
    out.printf(s + "%n", args);
  }

  private void delimiter(String c) {
    print(join("", nCopies(40, c)));
  }
}
