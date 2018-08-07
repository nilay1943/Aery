package Tools;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by 1250493 on 5/19/2017.
 */
public class OpenWindow extends Stage {
    private Label files;
    private Button picked, open, close;
    public OpenWindow()
    {
        setWidth(400);
        setHeight(400);

        files = new Label("Pick a file to open: ");
        open = new Button("Open");
        close = new Button("Close");

        setActionControls();

        File folder = new File(Paths.get("").toAbsolutePath()+ "\\Database");
        File[] listOfFiles = folder.listFiles();

        GridPane innerGridPane = new GridPane();

        for(int i = 0; i< listOfFiles.length; i++)
        {
            Button file = new Button(listOfFiles[i].getName());
            file.setOnAction((ActionEvent) -> {picked = file;
                System.out.println("this happened" + file.getText());});

            innerGridPane.add(file, 0, i);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(innerGridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setMinHeight(200);

        GridPane outerGridPane = new GridPane();
        outerGridPane.add(files, 0, 0);
        outerGridPane.add(scrollPane, 0, 1);
        outerGridPane.add(open, 0, 2);
        outerGridPane.add(close, 1, 2);

        Scene scene = new Scene(outerGridPane, 400, 400);

        setTitle("Open");
        setScene(scene);
        show();
    }

    private void setActionControls()
    {
        open.setOnAction((ActionEvent) ->
        {
            if(picked != null)
            {

            }

        });

        close.setOnAction((ActionEvent) ->
        {
            this.close();
        });
    }


}
