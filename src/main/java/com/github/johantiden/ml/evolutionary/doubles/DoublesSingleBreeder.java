package com.github.johantiden.ml.evolutionary.doubles;

import com.github.johantiden.ml.evolutionary.SingleBreeder;
import com.github.johantiden.ml.util.Maths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DoublesSingleBreeder<T> implements SingleBreeder<T> {

    private static final Logger log = LoggerFactory.getLogger(DoublesSingleBreeder.class);
    private final Packer<T> packer;

    public DoublesSingleBreeder(Packer<T> packer) {
        this.packer = packer;
    }

    @Override
    public List<T> apply(T t) {

        double[] parent = packer.pack(t);

        List<T> children = new ArrayList<>();

        mutateValues(children, parent, 1);
        mutateValues(children, parent, 2);
        mutateValues(children, parent, 3);
        mutateValues(children, parent, 4);
        mutateValues(children, parent, parent.length);
        mutateAddChunk(children, parent);

        return children;
    }

    private void mutateAddChunk(List<T> children, double[] parent) {
        int chunkSize = packer.chunkSize();
        double[] child = new double[parent.length + chunkSize];

        int numChunks = parent.length/chunkSize;
        int parentChunk = Maths.randomInt(numChunks);
        int parentChunkStart = parentChunk*chunkSize;

        for (int i = 0; i < parent.length; i++) {
            child[i] = parent[i];
        }
        for (int i = 0; i < chunkSize; i++) {
            child[i+parent.length] = mutateValue(parent[i+parentChunkStart]);
        }

        tryAdd(children, child);
    }

    private void mutateValues(List<T> children, double[] parent, int numValues) {
        double[] child = parent.clone();
        for (int i = 0; i < numValues; i++) {
            mutateRandomValue(child);
        }
        tryAdd(children, child);
    }

    private static void mutateRandomValue(double[] child) {
        int i = Maths.randomInt(child.length);
        child[i] = mutateValue(child[i]);
    }

    private static double mutateValue(double v) {
        return Maths.randomize(v, v);
    }

    private void tryAdd(List<T> children, double[] child) {
        T unpack = null;
        try {
            unpack = packer.unpack(child);
        } catch (UnpackFailedException ignored) {
            // Failed unpack. Probably because random value went out of limit.
            // This might be a big CPU loss.
            boolean putBreakpointHere = false;
            log.error("Unpack failed", ignored);
        }
        if (unpack != null) {
            children.add(unpack);
        }

    }
}
