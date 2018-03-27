package com.github.johantiden.ml.evolutionary.tree;

import com.google.common.collect.Lists;
import com.github.johantiden.ml.util.Maths;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TreeMutator implements Function<TreeData, TreeData> {

    private final int imageWidth;
    private final int imageHeight;

    public TreeMutator(int imageWidth, int imageHeight) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    @Override
    public TreeData apply(TreeData tree) {

        TreeData copy = tree.deepCopy();

        mutateTreeBasicValues(copy);

        mutateBasicValues(copy);
        mutateTopology(copy);

        return copy;
    }

    private void mutateTreeBasicValues(TreeData tree) {
        if (Math.random() > 0.90) {
            List<TreeData> list = tree.flattenAsList();
            int i = Maths.randomInt(list.size());

            TreeData node = list.get(i);

            mutateBasicValues(node);
        }
    }

    private void mutateBasicValues(TreeData tree) {
        if (Math.random() > 0.9) {
            tree.x = Maths.randomize(imageWidth / 10, tree.x, 0, imageWidth);
            tree.y = Maths.randomize(imageHeight / 10, tree.y, 0, imageHeight);
        }

        if (Math.random() > 0.9) {
            if (tree.isLeaf()) {
                tree.size = Maths.randomize(0.9, tree.size, 0, Double.POSITIVE_INFINITY);
            } else {
                tree.size = Maths.randomize(0.9, tree.size, 3, Double.POSITIVE_INFINITY);
            }
        }
        if (Math.random() > 0.9) {
            tree.color = Maths.randomizeColor(3, 3, tree.color);
        }
    }

    private static boolean randomizeBoolean(boolean b) {
        return Math.random() > 0.90 != b;
    }

    private void mutateTopology(TreeData tree) {

        if (Math.random() > 0.80) {
            addNewLeafRandomly(tree);
        }

        if (Math.random() > 0.95) {
            chopOffRandom(tree);
        }

        if (Math.random() > 0.90) {
            pruneBranchRandomly(tree);
        }
    }

    private void chopOffRandom(TreeData tree) {
        List<TreeData> list = tree.flattenAsList();
        int treeSize = list.size();
        int addNodeAddIndex = Maths.randomInt(treeSize);

        TreeData parent = list.get(addNodeAddIndex);
        if (!parent.isLeaf()) {
            int childrenSize = parent.children.size();
            int childIndex = Maths.randomInt(childrenSize);
            TreeData child = parent.children.get(childIndex);
            parent.children.remove(childIndex);
            TreeData middle = new TreeData(Lists.newArrayList(child),
                    Maths.avg(parent.x, child.x),
                    Maths.avg(parent.y, child.y),
                    Maths.avg(parent.size, child.size),
                    parent.color);
            mutateBasicValues(parent);
            mutateBasicValues(middle);
            mutateBasicValues(child);
            parent.children.add(childIndex, middle);
        }
    }

    private void pruneBranchRandomly(TreeData tree) {
        int treeSize = tree.getSize();
        int removeChildrenAtNodeIndex = Maths.randomInt(treeSize);

        TreeData treeData = tree.flattenAsList().get(removeChildrenAtNodeIndex);
        treeData.children.clear();
    }

    private void addNewLeafRandomly(TreeData tree) {
        int treeSize = tree.getSize();
        int addNodeAddIndex = Maths.randomInt(treeSize);

        TreeData nodeToAddChildAt = tree.flattenAsList().get(addNodeAddIndex);
        int i = nodeToAddChildAt.isLeaf() ? 0 : Maths.randomInt(nodeToAddChildAt.children.size());
        TreeData newChild = new TreeData(new ArrayList<>(),
                nodeToAddChildAt.x,
                nodeToAddChildAt.y,
                nodeToAddChildAt.size,
                nodeToAddChildAt.color);
        mutateBasicValues(newChild);

        newChild.x = Maths.randomize(imageWidth / 10.0, newChild.x, 0, imageWidth);
        newChild.y = Maths.randomize(imageHeight / 10.0, newChild.y, 0, imageHeight);
        newChild.size = Maths.randomize(20, newChild.size, 0, Double.POSITIVE_INFINITY);
        tree.color = Maths.randomizeColor(20, 20, tree.color);

        nodeToAddChildAt.children.add(i, newChild);
    }

}
