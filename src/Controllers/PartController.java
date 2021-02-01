package Controllers;

import Models.InhousePart;
import Models.Outsourced;
import Models.Part;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    private boolean inHouse = true;
    private Part newPart;

    public void updateToInHouse(javafx.scene.input.MouseEvent event) throws Exception {
        inHouse = true;
        additional_part_label.setText("Machine ID");
    }
    public void updateToOutsourced(javafx.scene.input.MouseEvent event) throws Exception {
        inHouse = false;
        additional_part_label.setText("Company Name");
    }

    public void addPart() {
        if (emptyFormFields()) { throwAlert("Form Error", "Fill out all required fields"); return; }
        try {
            int id = Inventory.getID(true);
            String name = name_field.getText();
            double price = Double.parseDouble(price_field.getText());
            int stock = Integer.parseInt(inv_field.getText());
            int min = Integer.parseInt(min_field.getText());
            int max = Integer.parseInt(max_field.getText());
            String additionalField = additional_part_field.getText();
            String inStockErrors = validStockNumber(stock, min, max);
            if (!inStockErrors.equals("")) {throwAlert("In Stock Error", inStockErrors); return; }
            if (inHouse) {
                int machineId = Integer.parseInt(additionalField);
                newPart = new InhousePart(id, name, price, stock, min, max, machineId);
            } else {
                newPart = new Outsourced(id, name, price, stock, min, max, additionalField);
            }
            Inventory.addPart(newPart);
        }
        catch (NumberFormatException e){
            throwAlert("Error adding part", "Entered invalid number");
        } catch (Exception e){
            throwAlert("Error adding part", e.getLocalizedMessage());
        }
    }

    public static void throwAlert(String mainText, String detail) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(mainText);
        alert.setHeaderText("Error");
        alert.setContentText(detail);
        alert.showAndWait();
    }

    public static String validStockNumber(int stock, int min, int max) {
        if (min > max) {
            return "Min cannot be greater than max";
        } else if (stock > max) {
            return "Inventory cannot be greater than max";
        } else if (stock < min){
            return "Inventory cannot be less than min";
        }   return "";
    }

    private boolean emptyFormFields(){
        String name = name_field.getText();
        String price = price_field.getText();
        String stock = inv_field.getText();
        String min = min_field.getText();
        String max = max_field.getText();
        return (name.equals("") && price.equals("") && stock.equals("") && min.equals("") && max.equals(""));
    }
}
