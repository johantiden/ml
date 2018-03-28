package com.github.johantiden.ml.evolutionary.doubles;

import com.github.johantiden.ml.evolutionary.OneBreeder;
import com.github.johantiden.ml.evolutionary.RandomCandidateSupplier;
import com.github.johantiden.ml.util.Maths;
import com.google.common.collect.Lists;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DoublesOneBreeder implements OneBreeder {

    private static final Logger log = LoggerFactory.getLogger(DoublesOneBreeder.class);
    private final int chunkSize;
    private final RandomCandidateSupplier randomCandidateSupplier;

    public DoublesOneBreeder(int chunkSize, RandomCandidateSupplier randomCandidateSupplier) {
        this.chunkSize = chunkSize;
        this.randomCandidateSupplier = randomCandidateSupplier;
    }


    @Override
    public List<Pair<List<Double>, String>> apply(List<Double> parent) {

        List<Pair<List<Double>, String>> children = new ArrayList<>();

        mutateValues(children, parent, 1);
        mutateValues(children, parent, 2);
        mutateValues(children, parent, 3);
        mutateValues(children, parent, 4);
//        mutateAddChunk(children, parent, randomCandidateSupplier);
//        mutateAddChunk(children, parent, randomCandidateSupplier);
//        mutateAddChunk(children, parent, randomCandidateSupplier);
        mutateAddChunk(children, parent, randomCandidateSupplier);
//        mutateRemoveRandomChunk(children, parent);
        mutateRemoveEachChunk(children, parent);

        return children;
    }

    private void mutateRemoveEachChunk(List<Pair<List<Double>, String>> children, List<Double> parent) {
        int numChunks = parent.size()/chunkSize;

        if (numChunks <= 1) {
            return;
        }
        for (int i = 0; i < numChunks; i++) {
            removeChunk(children, parent, i);
        }
    }

    private void mutateRemoveRandomChunk(List<Pair<List<Double>, String>> children, List<Double> parent) {
        int numChunks = parent.size()/chunkSize;

        if (numChunks <= 1) {
            return;
        }

        int chunkIndex = Maths.randomInt(numChunks);
        removeChunk(children, parent, chunkIndex);
    }

    private void removeChunk(List<Pair<List<Double>, String>> children, List<Double> parent, int chunkIndex) {
        List<Double> child = Lists.newArrayList(parent);

        int parentChunkStart = chunkIndex*chunkSize;

        for (int i = 0; i < chunkSize; i++) {
            child.remove(parentChunkStart);
        }
        tryAdd(children, child, "Removed chunk");
    }

    private void mutateAddChunk(List<Pair<List<Double>, String>> children, List<Double> parent, RandomCandidateSupplier randomCandidateSupplier) {
        List<Double> chunk = randomCandidateSupplier.get();
        List<Double> child = Lists.newArrayList(parent);
        for (int i = 0; i < chunkSize; i++) {
            child.add(chunk.get(i));
        }

        tryAdd(children, child, "Added chunk");
    }

    private void mutateValues(List<Pair<List<Double>, String>> children, List<Double> parent, int numValues) {
        List<Double> child = Lists.newArrayList(parent);
        for (int i = 0; i < numValues; i++) {
            mutateRandomValue(child);
        }
        tryAdd(children, child, "Mutate "+numValues+" value(s)");
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

    private void tryAdd(List<Pair<List<Double>, String>> children, List<Double> child, String name) {
        if (child.isEmpty()) {
            throw new IllegalArgumentException("Cannot be empty!");
        }

        children.add(new Pair<>(child, name));
    }
}
