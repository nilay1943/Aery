package Tabs;

import Tools.OpenWindow;
import Tools.SaveWindow;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

/**
 * Created by 1250493 on 5/16/2017.
 */
public class FileTab extends Tab {
    private Button open;
    private Button save;
    private TextField txtFld;

    public FileTab() {
        super("File Save/Load");

        save = new Button("Save");
        save.setMinWidth(200);
        save.setMinHeight(60);
        save.setOnAction((ActionEvent) ->
        {
            new SaveWindow();
        });

        open = new Button("Open");
        open.setMinWidth(200);
        open.setMinHeight(60);
        open.setOnAction((ActionEvent) -> new OpenWindow());
        GridPane gridPane = new GridPane();
        gridPane.setHgap(100);
        gridPane.setVgap(100);

        gridPane.add(save, 3 , 3);
        gridPane.add(open, 4, 3);

        this.setContent(gridPane);
    }
}