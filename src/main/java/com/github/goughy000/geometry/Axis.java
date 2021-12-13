package com.github.goughy000.geometry;

public enum Axis {
  X,
  Y;

  public static Axis fromString(String value) {
    if ("X".equalsIgnoreCase(value)) return X;
    if ("Y".equalsIgnoreCase(value)) return Y;
    throw new IllegalArgumentException("unrecognised axis " + value);
  }
}
