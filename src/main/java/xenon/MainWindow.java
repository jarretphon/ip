package xenon;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Serves as the primary GUI for the chatbot application.
 * It enables user interaction and displays chatbot responses.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Xenon xenon;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image xenonImage = new Image(this.getClass().getResourceAsStream("/images/chatbot.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setXenon(Xenon x) {
        this.xenon = x;
        String welcome = "Hello! I'm Xenon\n" + "What can I do for you?\n\n" + Operation.showUsageGuide();
        dialogContainer.getChildren().addAll(DialogBox.getDukeDialog(welcome, xenonImage, ""));
    }

    /**
     * Handles user input and updates the dialog container with user and chatbot responses.
     * Retrieves the user's input, generates a response from the chatbot, and displays both
     * the input and response in the dialog.
     *
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = xenon.getResponse(input);
        String commandType = xenon.getCommandType();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, xenonImage, commandType)
        );
        userInput.clear();

        if (input.equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1.2));
            delay.setOnFinished(e -> Platform.exit());
            delay.play();
        }
    }
}
