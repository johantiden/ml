package com.github.johantiden.ml.evolutionary;

import javafx.util.Pair;

import java.util.List;
import java.util.function.BiFunction;

@FunctionalInterface
public interface TwoBreeder extends BiFunction<
        List<Double>,
        List<Double>,
        List<Pair<List<Double>, String>>> {
}
