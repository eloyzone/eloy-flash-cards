package com.github.eloyzone.eloyflashcards.util;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.scene.Node;
import javafx.util.Duration;

public class Transitioner
{


    public static FadeTransition getFade(int duration, Node node)
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setInterpolator(Interpolator.EASE_IN);

        return fadeTransition;
    }

    public static FadeTransition getFade(int duration, Node node, Interpolator value)
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setInterpolator(value);

        return fadeTransition;
    }

}
