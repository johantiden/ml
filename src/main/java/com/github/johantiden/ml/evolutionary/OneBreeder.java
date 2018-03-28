package com.github.johantiden.ml.evolutionary;

import javafx.util.Pair;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface OneBreeder extends Function<List<Double>, List<Pair<List<Double>, String>>> {
}
