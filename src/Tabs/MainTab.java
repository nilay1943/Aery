package Tabs;
import Tools.ScrollerAndText;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class MainTab extends Tab
{
    private static final ScrollerAndText fuselageLength = new ScrollerAndText("Fuselage Lenght (cm)", 10.16, 100);
    private static final ScrollerAndText wingLocation = new ScrollerAndText("Wing Location (cm)", 0,0);
    private static final ScrollerAndText stabilizerLocation = new ScrollerAndText("Stabilizer Location (cm)", 0, 0);
    private static final ScrollerAndText vertTailLocation = new ScrollerAndText("VertTail Location (cm)", 0, 0);
    private static final ScrollerAndText noseMass = new ScrollerAndText("Nose Mass (g)" , 0, 50);
    private static final ScrollerAndText scale = new ScrollerAndText("ScaleMultiplier", 0, 10);
    private static Canvas canvas;
    

    public MainTab()
    {
        super("Main Tab");

        canvas = new Canvas(900, 900);
        scale.setValue(3);
        setListeners();

        setFuselageLengthControl();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(fuselageLength, 0, 0);
        gridPane.add(wingLocation, 0, 1);
        gridPane.add(stabilizerLocation, 0, 2);
        gridPane.add(vertTailLocation, 0, 3);
        gridPane.add(noseMass, 0, 4);
        gridPane.add(scale, 0, 5);
        GridPane gridPane1 = new GridPane();
        gridPane1.setHgap(10);
        gridPane1.setVgap(50);
        gridPane1.add(canvas, 1, 0);
        gridPane1.add(gridPane, 0, 0);

        this.setContent(gridPane1);
    }

    private void setFuselageLengthControl()
    {
        fuselageLength.getSlider().valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
        {
            wingLocation.getSlider().setMax((double) new_val - WingTab.getRootChordValue());
            stabilizerLocation.getSlider().setMax((double) new_val - StabilizerTab.getRootChordValue());
            vertTailLocation.getSlider().setMax((double) new_val - VerticalTailTab.getRootChordValue());
        });
    }

    private void setListeners()
    {
        fuselageLength.getSlider().valueProperty()
                .addListener
                        ((ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
                        {
                            double newMax = 3 * (double) new_val;
                            MainTab.update();
                            wingLocation.getSlider().setMax(newMax);
                            stabilizerLocation.getSlider().setMax(newMax);
                            vertTailLocation.getSlider().setMax(newMax);
                        });
        wingLocation.getSlider().valueProperty()
                .addListener
                        ((ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
                        {
                            MainTab.update();
                        });
        stabilizerLocation.getSlider().valueProperty()
                .addListener
                        ((ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
                        {
                            MainTab.update();
                        });
        vertTailLocation.getSlider().valueProperty()
                .addListener
                        ((ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
                        {
                            MainTab.update();
                        });
        noseMass.getSlider().valueProperty()
                .addListener
                        ((ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
                        {
                            MainTab.update();
                        });
        scale.getSlider().valueProperty()
                .addListener
                        ((ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
                        {
                            MainTab.update();
                        });

    }

    public static void update()
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double midHeight = canvas.getHeight()/2;
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeLine(200, midHeight, 200+fuselageLength.getValue()*scale.getValue(), midHeight);

        setVerticalTail(gc);

        gc.setLineWidth(2);
        setWing(gc);
        setStabilizer(gc);


    }

    private static void setVerticalTail(GraphicsContext gc)
    {
        gc.setStroke(Color.color(.5, 0, 1));
        
        double vertTailLoc = vertTailLocation.getValue()*scale.getValue();
        double topViewLength = VerticalTailTab.topViewLength()*scale.getValue();

        gc.strokeLine(200+vertTailLoc, canvas.getHeight()/2, 200+topViewLength + vertTailLoc, canvas.getHeight()/2);
        gc.strokeLine(200+vertTailLoc, canvas.getHeight()/2, 200+topViewLength + vertTailLoc, canvas.getHeight()/2);
        gc.strokeLine(200+vertTailLoc, canvas.getHeight()/2, 200+topViewLength + vertTailLoc, canvas.getHeight()/2);

    }

    public static void setWing(GraphicsContext gc)
    {

        gc.setStroke(Color.RED);
        
        double wingLoc = wingLocation.getValue()*scale.getValue();
        double angleOffset = WingTab.getAngleOffset() * scale.getValue();
        double rootChordOffSet = WingTab.getRootChordValue()*scale.getValue();
        double spanValue = WingTab.getSpanValue() * scale.getValue();
        double taperRatioOffset = WingTab.getTaperRatioOffset() * scale.getValue();

        gc.strokeLine(200+wingLoc, canvas.getHeight()/2, 200 + wingLoc + angleOffset, canvas.getHeight()/2 - spanValue);
        gc.strokeLine(200+wingLoc+rootChordOffSet, canvas.getHeight()/2, 200 + wingLoc + rootChordOffSet + angleOffset - taperRatioOffset, canvas.getHeight()/2 - spanValue);
        gc.strokeLine(200+wingLoc+ angleOffset,canvas.getHeight()/2 - spanValue, 200+wingLoc+rootChordOffSet + angleOffset - taperRatioOffset, canvas.getHeight()/2 - spanValue );

        gc.strokeLine(200+wingLoc, canvas.getHeight()/2, 200 + wingLoc + angleOffset, canvas.getHeight()/2 + spanValue);
        gc.strokeLine(200+wingLoc+rootChordOffSet, canvas.getHeight()/2, 200 + wingLoc + rootChordOffSet + angleOffset - taperRatioOffset, canvas.getHeight()/2 + spanValue);
        gc.strokeLine(200+wingLoc + angleOffset,canvas.getHeight()/2 + spanValue, 200+wingLoc+rootChordOffSet + angleOffset - taperRatioOffset, canvas.getHeight()/2 + spanValue );

    }

    public static void setStabilizer(GraphicsContext gc)
    {
        gc.setStroke(Color.BLUE);
        
        double stabilizerLoc = stabilizerLocation.getValue()*scale.getValue();
        double angleOffset = StabilizerTab.getAngleOffSet() * scale.getValue();
        double rootChordOffSet = StabilizerTab.getRootChordValue()*scale.getValue();
        double spanValue = StabilizerTab.getSpanValue() * scale.getValue();
        double taperRatioOffset = StabilizerTab.getTaperRatioOffset() * scale.getValue();

        gc.strokeLine(200+stabilizerLoc, canvas.getHeight()/2, 200 + stabilizerLoc + angleOffset, canvas.getHeight()/2 - spanValue);
        gc.strokeLine(200+stabilizerLoc+rootChordOffSet, canvas.getHeight()/2, 200 + stabilizerLoc + rootChordOffSet + angleOffset - taperRatioOffset, canvas.getHeight()/2 - spanValue);
        gc.strokeLine(200+stabilizerLoc+ angleOffset,canvas.getHeight()/2 - spanValue, 200+stabilizerLoc+rootChordOffSet + angleOffset - taperRatioOffset, canvas.getHeight()/2 - spanValue );

        gc.strokeLine(200+stabilizerLoc, canvas.getHeight()/2, 200 + stabilizerLoc + angleOffset, canvas.getHeight()/2 + spanValue);
        gc.strokeLine(200+stabilizerLoc+rootChordOffSet, canvas.getHeight()/2, 200 + stabilizerLoc + rootChordOffSet + angleOffset - taperRatioOffset, canvas.getHeight()/2 + spanValue);
        gc.strokeLine(200+stabilizerLoc + angleOffset,canvas.getHeight()/2 + spanValue, 200+stabilizerLoc+rootChordOffSet + angleOffset - taperRatioOffset, canvas.getHeight()/2 + spanValue );



    }

    public static ScrollerAndText getWingLocation()
    {
        return wingLocation;
    }

    public static ScrollerAndText getFuselageLength() {
        return fuselageLength;
    }

    public static ScrollerAndText getStabilizerLocation() {
        return stabilizerLocation;
    }

    public String toString()
    {
        return "Main: " + fuselageLength.getValue() + " " + wingLocation.getValue()  + " " + stabilizerLocation.getValue() + " " + vertTailLocation.getValue() + " " + noseMass.getValue() + " " + scale.getValue();
    }

    public static void setEverything(double... nums)
    {
        ScrollerAndText[] scrollerAndTexts = {fuselageLength, wingLocation, stabilizerLocation, vertTailLocation, noseMass, scale};

        for (int i = 0; i < scrollerAndTexts.length; i++)
            scrollerAndTexts[i].setValue(nums[i]);
    }



}
