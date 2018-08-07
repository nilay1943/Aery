package Tools;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.text.DecimalFormat;

/**
 * Created by Nilay on 3/13/2017.
 */
public class ScrollerAndText extends GridPane
{
    private final Slider slider = new Slider();
    private TextField textField;
    private Label name;

    public ScrollerAndText(String name, double min, double max)
    {
        this.setHgap(10);
        this.setVgap(10);

        slider.setMin(min);
        slider.setMax(max);
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);

        textField = new TextField();
        textField.setPromptText("0.0");

        setActionControls();

        this.name = new Label(name);

        this.add(this.name, 0, 0);
        this.add(slider, 0, 1);
        this.add(textField, 0, 2);
    }

    private void setActionControls()
    {
        slider.valueProperty()
                .addListener
                        ((ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
        {
            DecimalFormat dF = new DecimalFormat("###.##");

            textField.setText(dF.format(new_val));
        });

        textField.setOnAction(e -> {
            double d = 0;
            try
            {
                d = Double.parseDouble(textField.getText().toString());
                if(d > slider.getMax())
                    d = slider.getMax();
                else if( d < slider.getMin())
                    d = slider.getMin();

            }
            catch(Exception ex){};

            textField.setText(d + "");
            slider.setValue(d);
        });
    }

    public Slider getSlider()
    {
        return slider;
    }
    public double getValue()
    {
        return slider.getValue();
    }
    public void setValue(double d)
    {
        slider.setValue(d);
        textField.setText(d + "");
    }
}
