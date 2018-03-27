package com.github.johantiden.ml.evolutionary;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface SingleBreeder<T> extends Function<T, List<T>> {
}
