package com.github.goughy000;

import static java.lang.String.join;
import static java.lang.System.out;
import static java.util.Collections.nCopies;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Solution {
  protected List<String> input() {
    return input(getClass().getSimpleName() + ".txt");
  }

  protected List<String> input(String file) {
    try (var is = getResource(file);
        var isr = new InputStreamReader(is);
        var br = new BufferedReader(isr)) {

      var lines = new ArrayList<String>();
      while (br.ready()) {
        lines.add(br.readLine());
      }

      return lines;
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  private InputStream getResource(String resource) {
    var is = getClass().getResourceAsStream(resource);
    if (null == is) {
      throw new RuntimeException("Unable to find resource " + resource);
    }
    return is;
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
