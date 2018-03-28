package com.github.johantiden.ml.service;

import com.github.johantiden.ml.Main;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.johantiden.ml.jimage.Painter;
import com.github.johantiden.ml.jimage.color.JTColor;
import com.github.johantiden.ml.jimage.JTGraphics;
import com.github.johantiden.ml.jimage.JTImage;
import com.github.johantiden.ml.evolutionary.Evolutionary;

import javax.annotation.PostConstruct;
import java.util.List;

public class ImageService {

    private static final JTImage image = Main.getTargetImage(Main.DOWNSCALE);
    @Autowired
    private Evolutionary treeEvolver;
    @Autowired
    private Painter painter;

    private JTImage buffer;

    @PostConstruct
    public void postConstruct() {
        buffer = new JTImage(image.getWidth(), image.getHeight()*2);
    }

    public JTImage getLatestImage() {

        JTGraphics g = buffer.getGraphics();
        g.fillAll(JTColor.WHITE);
//        g.paint(0, 0, image, 0.1);

        List<Double> candidate = getCandidate();

//        g.setScale(Main.DOWNSCALE);
        painter.paint(g, candidate);
//        g.setScale(1);

        g.paint(0, image.getHeight(), image);

        return buffer;
    }

    private List<Double> getCandidate() {
        return treeEvolver.getBest();
    }
}
