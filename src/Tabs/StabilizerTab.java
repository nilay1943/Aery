package Tabs;

import Tools.ScrollerAndText;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import sun.applet.Main;

public class StabilizerTab extends Tab
{
    private static final ScrollerAndText span = new ScrollerAndText("Span(cm)", 1, 121.92);
    private static final ScrollerAndText rootChord = new ScrollerAndText("Root Chord (cm)", 1, 10.16);
    private static final ScrollerAndText taperRatio = new ScrollerAndText("Taper Ratio", .4, 1);
    private static final ScrollerAndText leadingEdgleSweepAngle = new ScrollerAndText("Leading Edge Sweep Angle", 0, 30);
    private static final ScrollerAndText scale = new ScrollerAndText("ScaleMultiplier", 0, 10);
    private static Canvas canvas;
    private static double height;

    public StabilizerTab()
    {
        super("Stabilizer Tab");
        canvas = new Canvas(800, 800);
        height = canvas.getHeight();
        scale.setValue(5);

        setListeners();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(50);

        gridPane.add(span, 0, 0);
        gridPane.add(rootChord, 0, 1);
        gridPane.add(taperRatio, 0, 2);
        gridPane.add(leadingEdgleSweepAngle, 0, 3);
        gridPane.add(scale, 0, 4);

        GridPane gridPane1 = new GridPane();
        gridPane1.setHgap(10);
        gridPane1.setVgap(50);
        gridPane1.add(canvas, 1, 0);
        gridPane1.add(gridPane, 0, 0);

        this.setContent(gridPane1);
    }
    private void setListeners()
    {
        span.getSlider().valueProperty()
                .addListener
                        ((ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
                        {
                            StabilizerTab.update();
                        });
        rootChord.getSlider().valueProperty()
                .addListener
                        ((ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
                        {
                            StabilizerTab.update();
                            ScrollerAndText sc = MainTab.getStabilizerLocation();
                            sc.getSlider().setMax(MainTab.getFuselageLength().getSlider().getValue() - (double) new_val);
                        });
        taperRatio.getSlider().valueProperty()
                .addListener
                        ((ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
                        {
                            StabilizerTab.update();
                        });
        leadingEdgleSweepAngle.getSlider().valueProperty()
                .addListener
                        ((ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
                        {
                            StabilizerTab.update();
                        });
        scale.getSlider().valueProperty()
                .addListener
                        ((ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
                        {
                            StabilizerTab.update();
                        });
    }
    private static void update()
    {
        double multiplier = scale.getValue();
        double angleOffSet = (span.getValue() / Math.tan((90-leadingEdgleSweepAngle.getValue()) * Math.PI /180)) * multiplier;
        double taperRatioOffset =(rootChord.getValue() - taperRatio.getValue()* rootChord.getValue()) * multiplier;
        double top = height - span.getValue()* multiplier;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.clearRect(0, 0, height, height);
        gc.strokeLine(200, height, 200+angleOffSet, top);
        gc.strokeLine(200 + rootChord.getValue()*multiplier, height, 200+rootChord.getValue()*multiplier+angleOffSet-taperRatioOffset, top);
        gc.strokeLine(200+angleOffSet, top, 200 + rootChord.getValue()*multiplier+angleOffSet-taperRatioOffset, top);

        MainTab.update();
    }

    public String toString()
    {
        return " Stabilizer Wing: " + getSpanValue() + " " + getRootChordValue() + " " + getTaperRatioValue() + " " + getTaperRatioValue() + " " + getLeadingEdgleSweepAngleValue() + " " + getScaleValue();
    }


    public static double getAngleOffSet()
    {
        return (span.getValue() / Math.tan((90-leadingEdgleSweepAngle.getValue()) * Math.PI /180));
    }

    public static double getTaperRatioOffset()
    {
        return (rootChord.getValue() - taperRatio.getValue()* rootChord.getValue());
    }

    public static double getSpanValue() {
        return span.getValue();
    }

    public static double getRootChordValue() {
        return rootChord.getValue();
    }

    public static double getTaperRatioValue() {
        return taperRatio.getValue();
    }

    public static double getLeadingEdgleSweepAngleValue() {
        return leadingEdgleSweepAngle.getValue();
    }

    public static double getScaleValue() {
        return scale.getValue();
    }

    public static void setEverything(double... nums)
    {
        ScrollerAndText[] scrollerAndTexts = {span, rootChord, taperRatio, leadingEdgleSweepAngle, scale};

        for (int i = 0; i < scrollerAndTexts.length; i++)
            scrollerAndTexts[i].setValue(nums[i]);
    }

}
