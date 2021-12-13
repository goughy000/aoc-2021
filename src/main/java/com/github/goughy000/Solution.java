package com.github.goughy000;

import static java.lang.String.format;
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
    printSolution(1, part1());
    printSolution(2, part2());
    delimiter("=");
  }

  private void printSolution(int part, Object value) {
    if (null == value) return;
    var lines = String.valueOf(value).lines().toList();
    if (lines.isEmpty()) return;

    var sb = new StringBuilder("Part ").append(part).append(":");
    if (lines.size() > 1) {
      sb.append(format("%n"));
    } else if (lines.size() == 1) {
      sb.append(" ");
    }
    for (var line : lines) {
      sb.append(format("%s%n", line));
    }
    out.print(sb);
  }

  private void print(String s, Object... args) {
    out.printf(s + "%n", args);
  }

  private void delimiter(String c) {
    print(join("", nCopies(40, c)));
  }
}
