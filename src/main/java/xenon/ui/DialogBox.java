package xenon.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of a label for dialog text and an ImageView for displaying a picture.
 * The dialog box can represent user messages or chatbot responses.
 * It can be flipped such that the image and text positions are interchanged.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox object with the specified text and image.
     * The DialogBox is initialized and loaded from its corresponding FXML file.
     *
     * @param text The text content to be displayed in the dialog box.
     * @param img The image to be displayed beside the text in the dialog box.
     */
    public DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    public static DialogBox getDukeDialog(String s, Image i, String commandType) {
        var db = new DialogBox(s, i);
        db.flip();
        db.changeDialogStyle(commandType);
        return db;
    }

    public static DialogBox getUserDialog(String s, Image i) {
        var db = new DialogBox(s, i);
        db.dialog.getStyleClass().add("user-label");
        return db;
    }

    private void changeDialogStyle(String commandType) {
        switch (commandType) {
        case "MarkCommand":
        case "UnmarkCommand":
        case "DeleteCommand":
            dialog.getStyleClass().add("success-label");
            break;
        case "Error":
            dialog.getStyleClass().add("error-label");
            break;
        default:
            // Do nothing
        }
    }
}
