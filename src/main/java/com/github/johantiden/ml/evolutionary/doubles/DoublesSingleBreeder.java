package com.github.johantiden.ml.evolutionary.doubles;

import com.github.johantiden.ml.evolutionary.SingleBreeder;
import com.github.johantiden.ml.util.Maths;

import java.util.ArrayList;
import java.util.List;

public class DoublesSingleBreeder<T> implements SingleBreeder<T> {

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

        for (int i = 0; i < parent.length; i++) {
            child[i] = parent[i];
        }
        for (int i = parent.length; i < child.length; i++) {
            child[i] = mutateValue(parent[i-chunkSize]);
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
        }
        if (unpack != null) {
            children.add(unpack);
        }

    }
}
