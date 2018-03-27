package com.github.johantiden.ml.evolutionary.worm;

import com.github.johantiden.ml.jimage.color.JTColor;
import com.github.johantiden.ml.util.Maths;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class WormBreeder implements Function<Worm, List<Worm>> {
    public static final double CHANCE = 0.95;
    private final int imageWidth;
    private final int imageHeight;

    public WormBreeder(int imageWidth, int imageHeight) {

        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    @Override
    public List<Worm> apply(Worm worm) {
        List<Worm> children = new ArrayList<>();

//        mutateRandomBlobs(children, worm, 1);
//        mutateRandomBlobs(children, worm, 2);
//        mutateRandomBlobs(children, worm, 3);
//        mutateAllBlobs(children, worm);
        mutateWithNewDecendantBlob(children, worm);
        mutateWithNewRandomBlob(children, worm);
        mutateRemoveBlob(children, worm);

        return children;
    }

    private void mutateWithNewRandomBlob(List<Worm> children, Worm worm) {
        Worm copy = worm.copy();
        int i = Maths.randomInt(copy.getBlobs().size());
        copy.getBlobs().add(new WormBlob(
                Maths.randomize(imageWidth, imageWidth/2, 0, 7),
                Maths.randomize(imageHeight, imageHeight/2, 0, 7),
                Maths.randomizeColor(1, 1, JTColor.GRAY),
                Maths.randomize(imageWidth, imageWidth/2, 0, imageWidth),
                Maths.randomize(imageHeight, imageHeight/2, 0, imageHeight),
                Maths.randomize(Math.PI*2, 0)));
    }

    private void mutateAllBlobs(List<Worm> children, Worm worm) {
        Worm copy = worm.copy();

        for (int i = 0; i < copy.getBlobs().size(); i++) {
            mutateBasicValues(children, copy, i);
        }
    }

    private void mutateRandomBlobs(List<Worm> children, Worm worm, int numberToMutate) {
        if (!worm.getBlobs().isEmpty()) {
            Worm copy = worm.copy();

            for (int j = 0; j < numberToMutate; j++) {
                int i = Maths.randomInt(copy.getBlobs().size());
                mutateBasicValues(children, copy, i);
                children.add(copy);
            }

        }
    }

    private void mutateRemoveBlob(List<Worm> children, Worm worm) {
        if (worm.getBlobs().size() > 1) {
            Worm copy = worm.copy();
            int index = Maths.randomInt(copy.getBlobs().size());
            copy.getBlobs().remove(index);
            children.add(copy);
        }
    }

    private void mutateWithNewDecendantBlob(List<Worm> children, Worm worm) {
        Worm copy = worm.copy();
        int index = Maths.randomInt(copy.getBlobs().size());
        WormBlob parent = copy.getBlobs().get(index);
        WormBlob newBlob = parent.copy();
        copy.getBlobs().add(index+1,newBlob);
        mutateBasicValues(children, copy, copy.getBlobs().size() - 1);
    }

    private void mutateBasicValues(List<Worm> children, Worm worm, int index) {
        Worm copy = worm.copy();
        WormBlob wormBlob = copy.getBlobs().get(index);
        wormBlob.width = Maths.randomize(wormBlob.width, wormBlob.width, 1, 7);
        wormBlob.height = Maths.randomize(wormBlob.height, wormBlob.height, 1, 7);
        wormBlob.angle = Maths.randomize(3, wormBlob.angle, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        wormBlob.color = Maths.randomizeColor(15, 0, wormBlob.color);
        mutatePosition(copy, wormBlob);
        children.add(copy);
    }

    private void mutatePosition(Worm worm, WormBlob wormBlob) {
//        if (worm.getBlobs().size() == 1) {
//            wormBlob.x = Maths.randomize(wormBlob.width, wormBlob.x, 0, imageWidth);
//            wormBlob.y = Maths.randomize(wormBlob.height, wormBlob.y, 0, imageHeight);
//        } else if (isLast(worm, wormBlob)) {
//
//            WormBlob blobBefore = worm.getBlobs().get(worm.getBlobs().size() - 2);
//            wormBlob.x = Maths.randomize(wormBlob.width, wormBlob.x,
//                    blobBefore.left(),
//                    blobBefore.right());
//            wormBlob.y = Maths.randomize(wormBlob.height, wormBlob.y,
//                    blobBefore.top(),
//                    blobBefore.bottom());
//        } else if(isFirst(worm, wormBlob)) {
//            WormBlob blobAfter = worm.getBlobs().get(1);
//
//            wormBlob.x = Maths.randomize(wormBlob.width, wormBlob.x,
//                    blobAfter.left(),
//                    blobAfter.right());
//            wormBlob.y = Maths.randomize(wormBlob.height, wormBlob.y,
//                    blobAfter.top(),
//                    blobAfter.bottom());
//        } else {
//            WormBlob blobBefore = worm.getBlobs().get(indexOf(worm, wormBlob) - 1);
////            WormBlob blobAfter = worm.getBlobs().get(indexOf(worm, wormBlob) + 1);
//            wormBlob.x = Maths.randomize(wormBlob.width, wormBlob.x,
//                    blobBefore.left(),
//                    blobBefore.right());
//            wormBlob.y = Maths.randomize(wormBlob.height, wormBlob.y,
//                    blobBefore.top(),
//                    blobBefore.bottom());
//
//
//
//
//
//        }

        wormBlob.x = Maths.randomize(wormBlob.width, wormBlob.x);
        wormBlob.y = Maths.randomize(wormBlob.height, wormBlob.y);
    }

    private static int indexOf(Worm worm, WormBlob wormBlob) {
        int i = worm.getBlobs().indexOf(wormBlob);
        if (i == -1) {
            throw new IllegalArgumentException("Not found.");
        }
        return i;
    }

    private static boolean isFirst(Worm worm, WormBlob wormBlob) {
        int i = indexOf(worm, wormBlob);
        return i == 0;
    }

    private static boolean isLast(Worm worm, WormBlob wormBlob) {
        int i = indexOf(worm, wormBlob);
        return i == worm.getBlobs().size()-1;
    }
}
