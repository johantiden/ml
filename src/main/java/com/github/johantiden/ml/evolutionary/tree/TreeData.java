package com.github.johantiden.ml.evolutionary.tree;

import com.github.johantiden.ml.datastructures.Tree;
import com.github.johantiden.ml.jimage.color.JTColor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TreeData implements Tree<TreeData> {

    public final List<TreeData> children;

    public double x;
    public double y;
    public double size;

    public JTColor color;


    public TreeData(List<TreeData> children, double x, double y, double size, JTColor color) {
        this.children = Objects.requireNonNull(children);
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = Objects.requireNonNull(color);
    }

    @Override
    public List<TreeData> getChildren() {
        return children;
    }

    @Override
    public TreeData getThis() {
        return this;
    }

    @Override
    public TreeData deepCopy() {
        List<TreeData> newChildren = children.stream()
                .map(TreeData::deepCopy)
                .collect(Collectors.toList());

        return new TreeData(
                newChildren,
                x,
                y,
                size,
                color
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        TreeData treeData = (TreeData) o;

        if (Double.compare(treeData.x, x) != 0) { return false; }
        if (Double.compare(treeData.y, y) != 0) { return false; }
        if (Double.compare(treeData.size, size) != 0) { return false; }
        if (!children.equals(treeData.children)) { return false; }
        return color.equals(treeData.color);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = children.hashCode();
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(size);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + color.hashCode();
        return result;
    }
}
