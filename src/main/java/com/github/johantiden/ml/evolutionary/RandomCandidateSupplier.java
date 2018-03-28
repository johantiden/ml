package com.github.johantiden.ml.evolutionary;

import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface RandomCandidateSupplier extends Supplier<List<Double>> {
}
