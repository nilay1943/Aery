import Tabs.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Material;
import javafx.stage.Stage;

public class Aery extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Group root = new Group();
        TabPane tabPane= new TabPane(new MainTab(), new WingTab(), new StabilizerTab(), new VerticalTailTab(), new FileTab());
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Scene scene = new Scene(root,1100, 800);

        BorderPane borderPane = new BorderPane();
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);

        primaryStage.setTitle("Aery");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {launch(args);}
}
