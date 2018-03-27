package com.github.johantiden.ml.evolutionary.worm;

import com.github.johantiden.ml.jimage.Painter;
import com.github.johantiden.ml.jimage.JTGraphics;

import java.util.List;

public class WormPainter implements Painter<Worm> {
    @Override
    public void paint(JTGraphics g, Worm worm) {

        List<WormBlob> blobs = worm.getBlobs();
//        for (int i = blobs.size()-1; i >= 0; i--) {
//            WormBlob wormBlob = blobs.get(i);
//            g.fillEllipse(wormBlob);
//        }

        blobs.forEach(g::fillEllipseRadial);

//        blobs.forEach(b -> g.fillCircle(new CircleWithColorImpl(b.x, b.y, b.color, b.width)));
//        blobs.forEach(b -> g.fillRectangle(
//                Maths.roundI(b.x),
//                Maths.roundI(b.y),
//                Maths.roundI(b.width),
//                Maths.roundI(b.height),
//                b.color));
    }
}
