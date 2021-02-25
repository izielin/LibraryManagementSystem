package assistant.Utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlertMaker {
    public static void showSimpleAlert(String type, String title, String header, String content) {
        // choose alerts type
        Alert alert;
        if (type.equals("information"))
            alert = new Alert(Alert.AlertType.INFORMATION);
        else if (type.equals("error"))
            alert = new Alert(Alert.AlertType.ERROR);
        else
            alert = new Alert(Alert.AlertType.CONFIRMATION);

        // set alerts elements
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public static Optional<ButtonType> alertConfirm(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    public static void showJFXButton(StackPane rootPane, Node node, List<JFXButton> controls, String header, String body) {
        BoxBlur blur = new BoxBlur(3, 3, 3);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);

        if (controls.isEmpty()) {
            controls.add(new JFXButton("Okay"));
        }

        controls.forEach(controlButton -> {
            controlButton.getStyleClass().add("dialog-button");
            controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
        });

        dialogLayout.setHeading(new Label(header));
        dialogLayout.setBody(new Label(body));
        dialogLayout.setActions(controls);
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1) -> node.setEffect(null));
        node.setEffect(blur);
    }

    public static void showExitDialog(StackPane rootPane, Node node) {
        BoxBlur blur = new BoxBlur(3, 3, 3);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);

        JFXButton exitButton = new JFXButton("Exit");
        JFXButton cancelButton = new JFXButton("Back");
        exitButton.getStyleClass().add("dialog-button");
        cancelButton.getStyleClass().add("dialog-button");

        exitButton.setOnAction(e -> {
            dialog.close();
            Platform.exit();
            System.exit(0);
        });

        cancelButton.setOnAction(e -> {
            dialog.close();
            e.consume();
        });

        JFXDialogLayout content = new JFXDialogLayout();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        Separator separator = new Separator();
        Label label = new Label("Are you sure you want to close app?");
        Label contentLabel = new Label("Make sure you save all changes, otherwise you will lose them");
        vBox.getChildren().addAll(label, separator, contentLabel);


        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().addAll(exitButton, cancelButton);
        vBox.getChildren().addAll(hBox);
        content.setBody(vBox);
        dialog.setContent(content);


        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1) -> node.setEffect(null));
        node.setEffect(blur);
    }


    public static void showTableDialog(StackPane rootPane, Node node, List<JFXButton> controls, String header, String body, TableView<?> tableView) {
        BoxBlur blur = new BoxBlur(3, 3, 3);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);

        controls.forEach(controlButton -> {
            controlButton.getStyleClass().add("dialog-button");
            controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
        });

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        Label labelBody = new Label(body);
        vBox.getChildren().addAll(labelBody, tableView);


        dialogLayout.setHeading(new Label(header));
        dialogLayout.setBody(vBox);
        dialogLayout.setActions(controls);
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1) -> node.setEffect(null));
        node.setEffect(blur);
    }

}
