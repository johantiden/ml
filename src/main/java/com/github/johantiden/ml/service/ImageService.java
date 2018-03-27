package com.github.johantiden.ml.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.johantiden.ml.jimage.Painter;
import com.github.johantiden.ml.jimage.FastJTImage;
import com.github.johantiden.ml.jimage.color.JTColor;
import com.github.johantiden.ml.jimage.color.JTColorImpl;
import com.github.johantiden.ml.jimage.JTGraphics;
import com.github.johantiden.ml.jimage.JTImage;
import com.github.johantiden.ml.evolutionary.Evolutionary;
import com.github.johantiden.ml.evolutionary.tree.TreeData;

import javax.annotation.PostConstruct;
import java.util.List;

public class ImageService<T> {

    @Autowired
    private JTImage tree;
    @Autowired
    private Evolutionary<T> treeEvolver;
    @Autowired
    private Painter<T> painter;


    private JTImage buffer;

    @PostConstruct
    public void postConstruct() {
        buffer = new FastJTImage(tree.getWidth(), tree.getHeight()*2);
    }

    public JTImage getLatestImage() {

        JTGraphics g = buffer.getGraphics();
        g.fillRectangle(0, 0, tree.getWidth(), tree.getHeight(), JTColor.WHITE);
//        g.paint(0, 0, tree, 0.1);

        T candidate = getCandidate();
        painter.paint(g, candidate);
        g.paint(0, tree.getHeight(), tree);

        return buffer;
    }

    private T getCandidate() {
        return treeEvolver.findBest();
    }

    private static TreeData tree(List<TreeData> children, double x, double y, double size) {
        return new TreeData(children, x, y, size, new JTColorImpl(128, 128, 128));
    }


}
