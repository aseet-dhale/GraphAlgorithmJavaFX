package org.graphalgorithm.algorithm;

import java.util.Objects;

public class Tuple<X, Y> {
    public final X x;
    public final Y y;

    Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tuple)) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(getX(), tuple.getX()) && Objects.equals(getY(), tuple.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
