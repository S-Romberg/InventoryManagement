package Controllers;

import Models.InHouse;
import Models.Outsourced;
import Models.Product;
import Models.Part;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProductController {
    @FXML
    private Label main_label;
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
    private TableView<Part> all_parts;
    @FXML
    private TableView<Part> associated_parts;
    @FXML
    private Button save_button;

    Product newProduct = new Product();
    static Product modifiedProduct;

    public void initialize() {
        all_parts.setItems(Inventory.getAllParts());
        if(modifiedProduct != null) {
            name_field.setText(modifiedProduct.getName());
            inv_field.setText(Integer.toString(modifiedProduct.getStock()));
            min_field.setText(Integer.toString(modifiedProduct.getMin()));
            max_field.setText(Integer.toString(modifiedProduct.getMax()));
            price_field.setText(Double.toString(modifiedProduct.getPrice()));
            main_label.setText("Modify Product");
            save_button.setOnMouseClicked(e -> modifyProduct());
        } else {
            name_field.clear();
            inv_field.clear();
            min_field.clear();
            max_field.clear();
            price_field.clear();
            main_label.setText("Add Product");
            Inventory.printList();
        }
    };

    public static void setModifiedProduct(Product product){
        modifiedProduct = product;
    }

    public void addAssociatedPart(){
        Part selected_part = all_parts.getSelectionModel().getSelectedItem();
        if (selected_part == null) { PartController.throwAlert("Error: No selected part", "Must select part to add"); return; }
        newProduct.addAssociatedPart(selected_part);
        associated_parts.setItems(newProduct.getAllAssociatedParts());
    }

    public void removeAssociatedPart(){
        Part selected_part = associated_parts.getSelectionModel().getSelectedItem();
        if (selected_part == null) { PartController.throwAlert("Error: No selected part", "Must select part to remove"); return; }
        newProduct.deleteAssociatedPart(selected_part);
    }

    public void addProduct() {
        if (emptyFormFields()) { PartController.throwAlert("Form Error", "Fill out all required fields"); return; }
        try {
            String name = name_field.getText();
            double price = Double.parseDouble(price_field.getText());
            int stock = Integer.parseInt(inv_field.getText());
            int min = Integer.parseInt(min_field.getText());
            int max = Integer.parseInt(max_field.getText());
            int id = Inventory.getID(false);
            String inStockErrors = PartController.validStockNumber(stock, min, max);
            if (!inStockErrors.equals("")) {PartController.throwAlert("In Stock Error", inStockErrors); return; }
            newProduct.setId(id);
            newProduct.setName(name);
            newProduct.setPrice(price);
            newProduct.setStock(stock);
            newProduct.setMin(min);
            newProduct.setMax(max);
            Inventory.addProduct(newProduct);
            close();
        } catch (NumberFormatException e){
            PartController.throwAlert("Error adding product", "Entered invalid number");
        } catch (Exception e){
            PartController.throwAlert("Error adding product", e.getLocalizedMessage());
        }
    }

    public void modifyProduct(){
        if (emptyFormFields()) { PartController.throwAlert("Form Error", "Fill out all required fields"); return; }
        String name = name_field.getText();
        double price = Double.parseDouble(price_field.getText());
        int stock = Integer.parseInt(inv_field.getText());
        int min = Integer.parseInt(min_field.getText());
        int max = Integer.parseInt(max_field.getText());
        Product updatedProduct = new Product(modifiedProduct.getId(), name, price, stock, min, max);
        String inStockErrors = PartController.validStockNumber(stock, min, max);
        if (!inStockErrors.equals("")) { PartController.throwAlert("In Stock Error", inStockErrors); return; }
        int index = Inventory.getAllProducts().indexOf(modifiedProduct);
        Inventory.updateProduct(index, updatedProduct);
        for (Part part : modifiedProduct.getAllAssociatedParts()) {
            updatedProduct.addAssociatedPart(part);
        }
        modifiedProduct = null;
        close();
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


    public void close() {
       Stage stage = (Stage) associated_parts.getScene().getWindow();
       stage.close();
    }

}
