package com.github.johantiden.ml.evolutionary.tree;

import com.github.johantiden.ml.awt.Painter;
import com.github.johantiden.ml.jimage.JTImage;
import com.github.johantiden.ml.evolutionary.image.ImageFitnessFunction;

public class TreeFitnessFunction extends ImageFitnessFunction<TreeData> {

    public TreeFitnessFunction(JTImage targetImage, Painter<TreeData> treeCandidatePainter, int windowSize) {
        super(targetImage, treeCandidatePainter, windowSize);
    }

    @Override
    protected double getTopologyLoss(TreeData tree) {


        int numNodes = tree.flattenAsList().size();
        int depth = tree.getDepth();
        double topologyLoss =  Math.sqrt(depth) + Math.sqrt(numNodes);

        return topologyLoss;
    }

}
