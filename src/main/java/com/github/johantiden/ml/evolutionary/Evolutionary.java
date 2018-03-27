package com.github.johantiden.ml.evolutionary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Evolutionary<T> {

    private static final Logger log = LoggerFactory.getLogger(Evolutionary.class);
    public final Function<T, List<T>> mutator;
    private final Function<T, Double> fitnessFunction;
    private final Supplier<T> randomCandidateSupplier;

    private T best; // optimization
    private double bestFitness = Double.NEGATIVE_INFINITY; // optimization

    public Evolutionary(Function<T, List<T>> mutator, Function<T, Double> fitnessFunction, Supplier<T> randomCandidateSupplier) {
        this.fitnessFunction = fitnessFunction;
        this.randomCandidateSupplier = randomCandidateSupplier;

        for (int i = 0; i < 10; i++) {
            add(randomCandidateSupplier.get());
        }

        this.mutator = mutator;
    }

    private void add(T t) {
//        candidates.computeIfAbsent(t, fitnessFunction);

        Double fitness = fitnessFunction.apply(t);
        //debug
//        Double fitness = candidates.get(t);
        if (fitness > bestFitness) {
            bestFitness = fitness;
            best = t;
            log.info("New best! " + fitness);
        }
    }

    public void iterate() {
        recursiveBreed(best, 0);
    }

    private void recursiveBreed(T t, int depth) {
        List<T> children = breed(t);
        children.forEach(this::add);

        if (depth > 0) {
            children.forEach(c -> recursiveBreed(c, depth-1));
        }
    }

    private List<T> breed(T t) {
        return mutator.apply(t);
    }

    public T findBest() {
        return best;
    }
}
