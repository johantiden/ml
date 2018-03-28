package com.github.johantiden.ml.evolutionary.worm;


import com.github.johantiden.ml.jimage.Painter;
import com.github.johantiden.ml.jimage.JTImage;
import com.github.johantiden.ml.evolutionary.image.ImageFitnessFunction;


public class WormFitnessFunction extends ImageFitnessFunction {

    public WormFitnessFunction(JTImage targetImage, Painter painter) {
        super(targetImage, painter);
    }

}
