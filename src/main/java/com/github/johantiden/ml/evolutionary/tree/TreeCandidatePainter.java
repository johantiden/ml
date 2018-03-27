package com.github.johantiden.ml.evolutionary.tree;

import com.github.johantiden.ml.awt.Painter;
import com.github.johantiden.ml.datastructures.BreadthFirst;
import com.github.johantiden.ml.jimage.CircleWithColorImpl;
import com.github.johantiden.ml.jimage.JTColor;
import com.github.johantiden.ml.jimage.JTColorImpl;
import com.github.johantiden.ml.jimage.JTGraphics;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TreeCandidatePainter implements Painter<TreeData> {

    private static final JTColor treeColor = new JTColorImpl(171, 110, 30, 250);
    private static final JTColor leafColor = new JTColorImpl(30, 140, 0, 250);

    @Override
    public void paint(JTGraphics g, TreeData tree) {
        BreadthFirst.traverse(tree, paintBranch(g), (a) -> {});
        BreadthFirst.traverse(tree, (a,b)->{}, paintLeaf(g));
    }

    private Consumer<TreeData> paintLeaf(JTGraphics g) {
        return leaf -> paintLeaf(g, leaf);
    }

    private BiConsumer<TreeData, TreeData> paintBranch(JTGraphics g) {
        return (parent, child) -> paintBranch(g, parent, child);
    }

    private void paintLeaf(JTGraphics g, TreeData leaf) {
//        if (leaf.paintLeaf) {
            g.fillCircle(new CircleWithColorImpl(leaf.x, leaf.y, getLeafColor(leaf), leaf.size));
//        }
    }

    private void paintBranch(JTGraphics g, TreeData p, TreeData c) {
//        g.drawLine(new LineWithColorImpl(p.x, p.y, c.x, c.y, getTreeColor(c)), p.size);
//        g.fillRectangle(
//                Maths.round(p.x),
//                Maths.round(p.y),
//                Maths.round(p.size),
//                Maths.round(p.size),
//                getTreeColor(p));

    }

    private JTColor getTreeColor(TreeData tree) {
//        return treeColor;
//        return new JTColorImpl(treeColor, tree.color.getA());
        return tree.color;
    }


    private JTColor getLeafColor(TreeData tree) {
//        return leafColor;
//        return new JTColorImpl(leafColor, tree.color.getA());
        return tree.color;
    }
}
