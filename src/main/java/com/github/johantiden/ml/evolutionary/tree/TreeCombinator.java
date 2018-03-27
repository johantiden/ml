package com.github.johantiden.ml.evolutionary.tree;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.johantiden.ml.util.Maths;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class TreeCombinator implements BiFunction<TreeData, TreeData, TreeData> {

    private static final Logger log = LoggerFactory.getLogger(TreeCombinator.class);

    @Override
    public TreeData apply(TreeData l, TreeData r) {

        return new TreeData(
                Lists.newArrayList(l,r),
                Maths.avg(l.x, r.x),
                Maths.avg(l.y, r.y),
                Maths.avg(l.size, r.size),
                l.color
        );
    }

    private List<TreeData> crossSplice(TreeData l, TreeData r) {

//        if (Math.random() < 1/3.0) {
        List<TreeData> merge = merge(l.children, r.children);

        return merge;
//        }
//
//        if (Math.random() < 1/3.0) {
//            return l;
//        }
//
//        return r;
    }

    private List<TreeData> merge(List<TreeData> l, List<TreeData> r) {
        ArrayList<TreeData> treeDatas = Lists.newArrayList(l);
        treeDatas.addAll(r);
        return treeDatas;
    }
}
