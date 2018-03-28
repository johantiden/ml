package com.github.johantiden.ml.evolutionary.doubles;

import com.github.johantiden.ml.evolutionary.TwoBreeder;
import com.github.johantiden.ml.util.Maths;
import com.google.common.collect.Lists;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DoublesTwoBreeder implements TwoBreeder {
    private final int chunkSize;

    public DoublesTwoBreeder(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Override
    public List<Pair<List<Double>, String>> apply(List<Double> a, List<Double> b) {

        List<Double> child = new ArrayList<>();

        addRandomChunks(child, a, Maths.randomInt(a.size()));
        addRandomChunks(child, b, Maths.randomInt(a.size()));

        mutateValues(child, child.size());

        return ret(child);
    }

    private void mutateValues(List<Double> values, int numValues) {
        for (int i = 0; i < numValues; i++) {
            mutateRandomValue(values);
        }
    }

    private static void mutateRandomValue(List<Double> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Cannot be empty!");
        }
        int i = Maths.randomInt(list.size());
        list.set(i, mutateValue(list.get(i)));
    }

    private static double mutateValue(double v) {
        return Maths.randomize(v/2, v);
    }

    private void addRandomChunks(List<Double> child, List<Double> parent, int numChunksToAdd) {

        for (int i = 0; i < numChunksToAdd; i++) {
            addRandomChunk(child, parent);
        }
    }

    private void addRandomChunk(List<Double> child, List<Double> parent) {
        int numChunks = parent.size()/chunkSize;

        int index = Maths.randomInt(numChunks);

        int chunkIndex = index*chunkSize;

        for (int i = 0; i < chunkSize; i++) {
            child.add(parent.get(i+chunkIndex));
        }
    }

    private List<Pair<List<Double>, String>> ret(List<Double> child) {
        Pair<List<Double>, String> p = new Pair<>(child, "Combined");
        return Lists.newArrayList(p);
    }
}
