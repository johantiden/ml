package com.github.johantiden.ml.datastructures;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BreadthFirst{


    public static <T extends Tree<T>> void traverse(T tree, BiConsumer<T, T> parentChildConsumer, Consumer<T> leafConsumer) {

        if (tree.isLeaf()) {
            leafConsumer.accept(tree);
        } else {
            for (T child : tree.getChildren()) {
                parentChildConsumer.accept(tree, child);
            }
            for (T child : tree.getChildren()) {
                traverse(child, parentChildConsumer, leafConsumer);
            }
        }
    }

    public static <T extends Tree<T>> void traverse(T tree, TriConsumer<Integer, T, T> parentChildConsumer, BiConsumer<Integer, T> leafConsumer) {
        traverse(tree, parentChildConsumer, leafConsumer, 0);
    }

    public static <T extends Tree<T>> void traverse(T tree, TriConsumer<Integer, T, T> parentChildConsumer, BiConsumer<Integer, T> leafConsumer, int depth) {

        if (tree.isLeaf()) {
            leafConsumer.accept(depth, tree);
        } else {
            for (T child : tree.getChildren()) {
                parentChildConsumer.accept(depth, tree, child);
            }
            for (T child : tree.getChildren()) {
                traverse(child, parentChildConsumer, leafConsumer, depth+1);
            }
        }
    }


}
