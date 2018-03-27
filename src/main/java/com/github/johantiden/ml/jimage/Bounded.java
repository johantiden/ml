package com.github.johantiden.ml.jimage;

import com.github.johantiden.ml.util.Maths;

public interface Bounded {
    double left();
    double right();
    double top();
    double bottom();

    default int leftInt() {
        return Maths.roundI(left());
    }
    default int rightInt() {
        return Maths.roundI(right());
    }
    default int topInt() {
        return Maths.roundI(top());
    }
    default int bottomInt() {
        return Maths.roundI(bottom());
    }

    default double getWidth() {
        return right() - left();
    }

    default double getHeight() {
        return bottom() - top();
    }

    default int getWidthInt() {
        return rightInt() - leftInt();
    }

    default int getHeightInt() {
        return bottomInt() - topInt();
    }
}
