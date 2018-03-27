package com.github.johantiden.ml.evolutionary.worm;

import com.github.johantiden.ml.jimage.color.JTColor;
import com.github.johantiden.ml.evolutionary.doubles.BaseChunkPacker;
import com.github.johantiden.ml.evolutionary.doubles.Packer;
import com.github.johantiden.ml.util.Maths;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Worm {

    public static final Packer<Worm> PACKER = new BaseChunkPacker<Worm, WormBlob>() {

        @Override
        public int chunkSize() {
            return 9;
        }

        @Override
        protected WormBlob unpackChunk(double[] packed, int startIndex) {
            return new WormBlob(
                    packed[startIndex],
                    packed[startIndex+1],
                    new JTColor(
                            Math.round(Maths.minmax(packed[startIndex+2], 0, 1)),
                            Math.round(Maths.minmax(packed[startIndex + 3], 0, 1)),
                            Math.round(Maths.minmax(packed[startIndex+4], 0, 1)),
                            Math.round(Maths.minmax(packed[startIndex+5], 0, 1))
                    ),
                    packed[startIndex+6],
                    packed[startIndex+7],
                    packed[startIndex+8]
                    );
        }

        @Override
        protected void packChunk(WormBlob blob, double[] target, int startIndex) {
            target[startIndex  ] = blob.x;
            target[startIndex+1] = blob.y;
            target[startIndex+2] = blob.color.getR();
            target[startIndex+3] = blob.color.getG();
            target[startIndex+4] = blob.color.getB();
            target[startIndex+5] = blob.color.getA();
            target[startIndex+6] = blob.width;
            target[startIndex+7] = blob.height;
            target[startIndex+8] = blob.angle;
        }

        @Override
        protected Worm fromChunks(List<WormBlob> chunks) {
            return new Worm(chunks);
        }

        @Override
        protected List<WormBlob> toChunks(Worm worm) {
            return worm.getBlobs();
        }


        @Override
        public int getChunkSize() {
            return 9;
        }
    };


    private final List<WormBlob> blobs;

    public Worm(List<WormBlob> blobs) {
        this.blobs = Objects.requireNonNull(blobs);
    }

    public Worm copy() {
        return new Worm(
                blobs.stream()
                    .map(WormBlob::copy)
                .collect(Collectors.toList())
        );
    }

    public List<WormBlob> getBlobs() {
        return blobs;
    }

    public static Packer<Worm> packer() {
        return PACKER;
    }
}
