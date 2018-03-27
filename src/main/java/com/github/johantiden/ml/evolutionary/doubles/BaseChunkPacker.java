package com.github.johantiden.ml.evolutionary.doubles;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseChunkPacker<T, C> implements Packer<T> {

    protected abstract int getChunkSize();

    protected abstract C unpackChunk(double[] packed, int startIndex);
    protected abstract void packChunk(C chunk, double[] target, int startIndex);

    protected abstract T fromChunks(List<C> chunks);
    protected abstract List<C> toChunks(T t);


    @Override
    public T unpack(double[] packed) {
        int chunkSize = getChunkSize();

        if (packed.length % chunkSize != 0) {
            throw new RuntimeException("Not implemented");
        }

        List<C> chunks = new ArrayList<>();
        for (int i = 0; i < packed.length; i+=chunkSize) {
            C chunk = unpackChunk(packed, i);
            chunks.add(chunk);
        }

        return fromChunks(chunks);
    }


    @Override
    public double[] pack(T t) {
        int chunkSize = getChunkSize();

        List<C> chunks = toChunks(t);
        double[] target = new double[chunkSize*chunks.size()];

        for (int i = 0; i < chunks.size(); i++) {
            packChunk(chunks.get(i), target, i*chunkSize);
        }

        return target;
    }
}
