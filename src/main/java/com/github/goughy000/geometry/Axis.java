package com.github.goughy000.geometry;

public enum Axis {
  X,
  Y;

  public long pointValue(Point point) {
    return this == X ? point.x() : point.y();
  }

  public Point mirror(Point point, long fold) {
    var v = pointValue(point);
    if (v == fold) return null;
    if (v < fold) return point;
    return new Point(
        this == X ? fold - (point.x() - fold) : point.x(),
        this == Y ? fold - (point.y() - fold) : point.y());
  }

  public Point half(Point point) {
    return new Point(this == X ? point.x() / 2 : point.x(), this == Y ? point.y() / 2 : point.y());
  }

  public static Axis fromString(String value) {
    if ("X".equalsIgnoreCase(value)) return X;
    if ("Y".equalsIgnoreCase(value)) return Y;
    throw new IllegalArgumentException("unrecognised axis " + value);
  }
}
