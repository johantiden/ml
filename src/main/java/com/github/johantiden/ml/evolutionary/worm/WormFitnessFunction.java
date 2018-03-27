package com.github.johantiden.ml.evolutionary.worm;


import com.github.johantiden.ml.awt.Painter;
import com.github.johantiden.ml.jimage.JTImage;
import com.github.johantiden.ml.evolutionary.image.ImageFitnessFunction;

public class WormFitnessFunction extends ImageFitnessFunction<Worm> {
    public WormFitnessFunction(JTImage targetImage, Painter<Worm> painter, int windowSize) {
        super(targetImage, painter, windowSize);
    }

    @Override
    protected double getTopologyLoss(Worm worm) {
        return worm.getBlobs().size();
    }
}
