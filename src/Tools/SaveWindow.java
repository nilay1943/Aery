package Tools;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by 1250493 on 5/19/2017.
 */
public class SaveWindow extends Stage {

    private Label prompt, prompt2;
    private Button picked, save, close;
    private TextField tf;
    public SaveWindow()
    {

        System.out.println("this should work please work");
        setWidth(400);
        setHeight(400);

        tf = new TextField();
        prompt = new Label("Pick a file to save to: ");
        prompt2 = new Label("Or name a new file: ");
        save = new Button("Save");
        close = new Button("Close");
        setListeners();


        File folder = new File(Paths.get("").toAbsolutePath()+ "\\Database");
        File[] listOfFiles = folder.listFiles();

        GridPane innerGridPane = new GridPane();

        for(int i = 0; i< listOfFiles.length; i++)
        {
            Button file = new Button(listOfFiles[i].getName());
            file.setOnAction((ActionEvent) -> picked = file);

            innerGridPane.add(file, 0, i);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(innerGridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setMinHeight(200);

        GridPane outerGridPane = new GridPane();
        outerGridPane.add(prompt,0 ,0);
        outerGridPane.add(scrollPane, 0, 1);
        outerGridPane.add(prompt2, 0, 2);
        outerGridPane.add(tf, 0,3);
        outerGridPane.add(save, 0, 4);
        outerGridPane.add(close, 1, 4);

        Scene scene = new Scene(outerGridPane, 400, 400);

        setTitle("Save");
        setScene(scene);
        show();
    }

    private void setListeners() {

        save.setOnAction((ActionEvent) -> {
            String input = "";
            if(tf.textProperty().getValue().trim() == "")
            {
                input= tf.textProperty().getName();
            }
            else if(picked != null)
            {
                input = picked.getText();
            }

            try {writeToFile(input);}catch (IOException e){}
        });

        close.setOnAction((ActionEvent) -> this.close());


    }

    public static void writeToFile(String str) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("str"));
        for (Student x : list) {
            writer.write(x.getFirstName() + " " + x.getLastName() + " " + x.getTickets());
            writer.write(x.getFirstName() + " " + x.getLastName() + " " + x.getTickets());
            writer.newLine();
        }
        writer.close();
    }

}
