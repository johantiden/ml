package com.github.johantiden.ml.datastructures;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface Tree<T extends Tree<T>> {

    List<T> getChildren();
    T getThis();
    T deepCopy();

    default boolean isLeaf() {
        return getChildren().isEmpty();
    }

    default int getDepth() {
        if (isLeaf()) {
            return 1;
        }

        return 1 + getChildren().stream()
                .mapToInt(Tree::getDepth)
                .max()
                .getAsInt();
    }

    default int getSize() {
        if (isLeaf()) {
            return 1;
        }

        return 1 + getChildren().stream()
                .mapToInt(Tree::getSize)
                .max()
                .getAsInt();
    }


    default List<T> flattenAsList() {
        ArrayList<T> list = Lists.newArrayList();
        list.add(getThis());

        List<T> collect = getChildren().stream()
                .flatMap(c -> c.flattenAsList().stream())
                .collect(Collectors.toList());

        list.addAll(collect);
        return list;
    }
}
