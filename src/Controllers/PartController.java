package Controllers;

import Models.InHouse;
import Models.Outsourced;
import Models.Part;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PartController {
    @FXML
    private Label main_label;
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
    @FXML
    private Button save_button;
    @FXML
    private RadioButton outsourced;
    @FXML
    private RadioButton inhouse;

    String name;
    double price;
    int stock;
    int min;
    int max;
    String additionalField;

    private boolean inHouse = true;
    private static Part selectedPart;

    public void initialize() {
        if(selectedPart != null) {
            name_field.setText(selectedPart.getName());
            inv_field.setText(Integer.toString(selectedPart.getStock()));
            min_field.setText(Integer.toString(selectedPart.getMin()));
            max_field.setText(Integer.toString(selectedPart.getMax()));
            price_field.setText(Double.toString(selectedPart.getPrice()));
            if (selectedPart instanceof InHouse){
                additional_part_field.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
            } else {
                additional_part_field.setText(((Outsourced) selectedPart).getCompanyName());
                inHouse = false;
                outsourced.setSelected(true);
                updateToOutsourced();
            }
            main_label.setText("Modify Part");
            save_button.setOnMouseClicked(e -> modifyPart());
        } else {
            name_field.clear();
            inv_field.clear();
            min_field.clear();
            max_field.clear();
            price_field.clear();
            additional_part_field.clear();
            main_label.setText("Add Part");
            Inventory.printList();
        }
    };


    public void updateToInHouse() {
        inHouse = true;
        additional_part_label.setText("Machine ID");
    }

    public void updateToOutsourced() {
        inHouse = false;
        additional_part_label.setText("Company Name");
    }

    public void close() {
        Stage stage = (Stage) additional_part_field.getScene().getWindow();
        stage.close();
    }

    /**
     * @param part the selected part in part table
     */
    public static void setSelectedPart(Part part){
        selectedPart = part;
    }

    public void addPart() {
        Part newPart;
        if (emptyFormFields()) { throwAlert("Form Error", "Fill out all required fields"); return; }
        try {
            name = name_field.getText();
            price = Double.parseDouble(price_field.getText());
            stock = Integer.parseInt(inv_field.getText());
            min = Integer.parseInt(min_field.getText());
            max = Integer.parseInt(max_field.getText());
            additionalField = additional_part_field.getText();
            int id = Inventory.getID(true);
            String inStockErrors = validStockNumber(stock, min, max);
            if (!inStockErrors.equals("")) {throwAlert("In Stock Error", inStockErrors); return; }
            if (inHouse) {
                int machineId = Integer.parseInt(additionalField);
                newPart = new InHouse(id, name, price, stock, min, max, machineId);
            } else {
                newPart = new Outsourced(id, name, price, stock, min, max, additionalField);
            }
            Inventory.addPart(newPart);
            close();
        } catch (NumberFormatException e){
            throwAlert("Error adding part", "Entered invalid number");
        } catch (Exception e){
            throwAlert("Error adding part", e.getLocalizedMessage());
        }
    }

    public void modifyPart() {
        name = name_field.getText();
        price = Double.parseDouble(price_field.getText());
        stock = Integer.parseInt(inv_field.getText());
        min = Integer.parseInt(min_field.getText());
        max = Integer.parseInt(max_field.getText());
        additionalField = additional_part_field.getText();
        String inStockErrors = validStockNumber(stock, min, max);
        if (!inStockErrors.equals("")) {throwAlert("In Stock Error", inStockErrors); return; }
        int machineId = Integer.parseInt(additionalField);
        boolean inHouse = selectedPart instanceof InHouse;
        int index = Inventory.getAllParts().indexOf(selectedPart);
        Part modifiedPart;
        if (inHouse) {
            modifiedPart = new InHouse(selectedPart.getId(), name, price, stock, min, max, Integer.parseInt(additionalField));
        } else {
            modifiedPart = new Outsourced(selectedPart.getId(), name, price, stock, min, max, additionalField);
        }
        Inventory.updatePart(index, modifiedPart);
        close();
    }

    /**
     * @param mainText Main text to display in alert
     * @param detail Detail text to display in alert
     */
    public static void throwAlert(String mainText, String detail) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(mainText);
        alert.setHeaderText("Error");
        alert.setContentText(detail);
        alert.showAndWait();
    }

    /**
     * @param stock Stock number from form
     * @param min Min number from form
     * @param max Max number from form
     * @return True if all stock-related numbers from form (inv, min, max) are valid
     */
    public static String validStockNumber(int stock, int min, int max) {
        if (min > max) {
            return "Min cannot be greater than max";
        } else if (stock > max) {
            return "Inventory cannot be greater than max";
        } else if (stock < min){
            return "Inventory cannot be less than min";
        }   return "";
    }

    /**
     * @return True if there are fields left empty, false if all fields have been filled
     */
    private boolean emptyFormFields(){
        String name = name_field.getText();
        String price = price_field.getText();
        String stock = inv_field.getText();
        String min = min_field.getText();
        String max = max_field.getText();
        return (name.equals("") && price.equals("") && stock.equals("") && min.equals("") && max.equals(""));
    }

}
