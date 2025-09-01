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
import xenon.exception.XenonException;

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
        dialogContainer.getChildren().addAll(DialogBox.getDukeDialog(welcome, xenonImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws XenonException {
        String input = userInput.getText();
        String response = xenon.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, xenonImage)
        );
        userInput.clear();

        if (input.equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1.2));
            delay.setOnFinished(e -> Platform.exit());
            delay.play();
        }
    }
}
