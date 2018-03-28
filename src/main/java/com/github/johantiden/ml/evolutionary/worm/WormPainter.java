package com.github.johantiden.ml.evolutionary.worm;

import com.github.johantiden.ml.jimage.Painter;
import com.github.johantiden.ml.jimage.JTGraphics;
import com.github.johantiden.ml.jimage.color.JTColor;
import com.github.johantiden.ml.jimage.shape.EllipseWithColorImpl;
import com.github.johantiden.ml.jimage.shape.RectangleWithColor;
import com.github.johantiden.ml.util.Maths;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

public class WormPainter implements Painter {

    public int chunkSize;
    private BiConsumer<JTGraphics, Iterator<Double>> consumer;

    public WormPainter() {
//        rectangles();
        ellipses();
    }

    private void rectangles() {
        chunkSize = 8;
        consumer = (g, iterator) -> {
            g.fillShape(
                    new RectangleWithColor(
                            iterator.next(),
                            iterator.next(),
                            iterator.next(),
                            iterator.next(),
                            new JTColor(
                                    Maths.minmax(iterator.next(), 0, 1),
                                    Maths.minmax(iterator.next(), 0, 1),
                                    Maths.minmax(iterator.next(), 0, 1),
                                    Maths.minmax(iterator.next(), 0, 1)
                            )
                    ));
        };
    }

    private void ellipses() {
        chunkSize = 9;
        consumer = (g, iterator) -> {
            g.fillShape(
                    new EllipseWithColorImpl(
                            iterator.next(),
                            iterator.next(),
                            iterator.next(),
                            iterator.next(),
                            iterator.next(),
                            new JTColor(
                                    Maths.minmax(iterator.next(), 0, 1),
                                    Maths.minmax(iterator.next(), 0, 1),
                                    Maths.minmax(iterator.next(), 0, 1),
                                    Maths.minmax(iterator.next(), 0, 1)
                            )
                    ));
        };

    }

    @Override
    public void paint(JTGraphics g, List<Double> values) {
        if (values.size() % chunkSize != 0) {
            throw new IllegalArgumentException("Cannot paint. Array is not divisible into chunks.");
        }


        Iterator<Double> iterator = values.iterator();

        while (iterator.hasNext()) {
            consumer.accept(g, iterator);
        }
    }

    @Override
    public int getChunkSize() {
        return chunkSize;
    }
}
