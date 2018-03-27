package com.github.johantiden.ml.evolutionary.doubles;

public interface Packer<T> {

    double[] pack(T t);
    T unpack(double[] packed) throws UnpackFailedException;

    int chunkSize();
}
