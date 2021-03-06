package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmBox {
    private boolean answer;

    /*
                   Another user issued command encapsulation, ensures that users don't accidentally close the program
                   and forces a confirm box to make sure it does not occur.
     */

    public boolean displayGetAnswer(String title, String message) {


        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(230);


        //Set message on the label
        Label label = new Label();
        label.setText(message);

        //Set the buttons up
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");


        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        BorderPane layout = new BorderPane();
        layout.setTop(label);
        layout.setLeft(yesButton);
        layout.setRight(noButton);


        layout.setPadding(new Insets(10, 30, 10, 30));

        Scene scene = new Scene(layout);
        window.setAlwaysOnTop(true);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }

    /**
     * Handel close method
     * @param window any window passed to this object
     * @param Title Title of the confirm box
     * @param Message   message displayed to the user
     */
    public static void handleClose(Stage window, String Title, String Message) {
        ConfirmBox box = new ConfirmBox();
        boolean close = box.displayGetAnswer(Title, Message);
        if (close) {
            window.close();

        }

    }


}