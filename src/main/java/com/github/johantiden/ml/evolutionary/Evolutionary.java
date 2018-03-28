package com.github.johantiden.ml.evolutionary;

import com.github.johantiden.ml.util.Maths;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Evolutionary {

    private static final Logger log = LoggerFactory.getLogger(Evolutionary.class);
    private static final Comparator<Pair<List<Double>, Double>> COMPARATOR =
            (o1, o2) -> {
                int i = o1.getValue().compareTo(o2.getValue());
                if (i != 0) {
                    return -i;
                }

                return Integer.compare(o1.getKey().hashCode(), o2.getKey().hashCode());

            };

    private final OneBreeder oneBreeder;
    private final TwoBreeder twoBreeder;
    private final FitnessFunction fitnessFunction;
    private final SortedSet<Pair<List<Double>, Double>> population = new TreeSet<>(COMPARATOR);

    private double bestFitness = Double.NEGATIVE_INFINITY; // optimization
    private final int LIMIT = 1000;

    public Evolutionary(
            OneBreeder oneBreeder,
            TwoBreeder twoBreeder,
            FitnessFunction fitnessFunction,
            RandomCandidateSupplier randomCandidateSupplier) {
        this.fitnessFunction = fitnessFunction;

        for (int i = 0; i < 10; i++) {
            add(new Pair<>(randomCandidateSupplier.get(), "Random"));
        }

        this.oneBreeder = oneBreeder;
        this.twoBreeder = twoBreeder;
    }

    private void add(Pair<List<Double>, String> candidate) {
        Double fitness = fitnessFunction.apply(candidate.getKey());

        if (fitness > bestFitness) {
            bestFitness = fitness;
            String name = candidate.getValue();
            log.info("New best! " + fitness + "\t" + name);
        }

        population.add(new Pair<>(candidate.getKey(), fitness));

        ensurePopulationLimit();
    }

    private void ensurePopulationLimit() {

        while (population.size() > LIMIT) {
            Pair<List<Double>, Double> random = getRandom();
            if (random != getBest()) {
                population.remove(random);
            }
        }

    }

    public void iterate() {
        breedBest();
        breedNextBest();
        breedRandom();
//        breedBestWithRandom();
    }

    private void breedNextBest() {
        if (population.size() >= 2) {
            addAll(oneBreeder.apply(getNextBest()));
        }
    }

    private List<Double> getNextBest() {
        Iterator<Pair<List<Double>, Double>> iterator = population.iterator();
        iterator.next();
        return iterator.next().getKey();
    }

    private void breedBestWithRandom() {
        addAll(twoBreeder.apply(getRandom().getKey(), getBest()));
    }

    private void addAll(List<Pair<List<Double>, String>> children) {
        children.forEach(this::add);
    }

    private void breedBest() {
        addAll(oneBreeder.apply(getBest()));
    }

    private void breedRandom() {
        addAll(oneBreeder.apply(getRandom().getKey()));
    }

    private Pair<List<Double>, Double> getRandom() {
        int index = Maths.randomInt(population.size());

        return population.stream()
                .skip(index)
                .findFirst()
                .get();
    }

    public List<Double> getBest() {
        return population.first().getKey();
    }
}
