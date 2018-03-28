package com.github.johantiden.ml.evolutionary;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface FitnessFunction extends Function<List<Double>, Double> {
}
