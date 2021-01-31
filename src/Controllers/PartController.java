package Controllers;

import Models.Inhouse;
import Models.Outsourced;
import Models.Part;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOError;
import java.io.IOException;

public class PartController {
    @FXML
    private Label additional_part_label;
    @FXML
    private TextField additional_part_field;
    @FXML
    private TextField name_field;
    @FXML
    private TextField inv_field;
    @FXML
    private TextField price_field;
    @FXML
    private TextField max_field;
    @FXML
    private TextField min_field;
    private boolean inHouse;
    private Part newPart;

    public void updateToInHouse(javafx.scene.input.MouseEvent event) throws Exception {
        inHouse = true;
        additional_part_label.setText("Machine ID");
    }
    public void updateToOutsourced(javafx.scene.input.MouseEvent event) throws Exception {
        inHouse = false;
        additional_part_label.setText("Company Name");
    }

    public void addPart() throws Exception {
        // instantiate new classes, grab values from fields first and apply to whichever condition is true
        if (inHouse) {
            newPart = new Inhouse();
        } else {
            newPart = new Outsourced();
        }
    }

}
