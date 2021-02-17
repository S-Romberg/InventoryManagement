package Controllers;
import Models.Part;
import Models.Product;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.function.Predicate;

public class Inventory extends Application {

    @FXML
    private TableView<Part> part_table;
    @FXML
    private TableView<Product> product_table;
    @FXML
    private TextField search_part;
    @FXML
    private TextField search_product;

    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static FilteredList<Part> filteredParts = new FilteredList<>(allParts);
    public static FilteredList<Product> filteredProducts = new FilteredList<>(allProducts);
    private static int partID = 0;
    private static int productID = 0;

    public Inventory() {}

    /**
     * initializes search fields for products and parts
    */
    public void initialize () {
        initializeSearch();
    }


    /**
     * starts main page for application
     */
    @Override
    public void start(Stage mainStage) throws Exception{
        Parent root =  FXMLLoader.load(getClass().getResource("../Views/inventory.fxml"));
        mainStage.setTitle("Inventory Management System");
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }

    /**
     *
     * RUNTIME ERROR: During development on the search functionality I had introduced a bug where it would only filter
     * off of the first letter in the search field. I fixed this by moving the Predicate declarations inside of the
     * addListener method on the search fields.
     *
     * FUTURE ENHANCEMENT: In the future I would add a `type` property to the Parts class so that the user could filter
     * off of that property. I would also add a database so the data would persist.
     * off of that property. I would also add a database so the data would persist.
     *
     * JAVADOC: located in /InventoryManagement/javadoc
     *
     * @param args main args
     */

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * initializes search fields for products and parts
     */
    public void initializeSearch(){
        part_table.setItems(filteredParts);
        product_table.setItems(filteredProducts);

        search_part.textProperty().addListener(obs-> {
            Predicate<Part> partSearch = e -> String.valueOf(e.getId()).contains(search_part.getText()) || e.getName().contains(search_part.getText());
            String filter = search_part.getText();
            if(filter == null || filter.length() == 0) {
                filteredParts.setPredicate(s -> true);
            } else {
                filteredParts.setPredicate(partSearch);
            }
        });
        search_product.textProperty().addListener(obs-> {
            Predicate<Product> productSearch = e -> String.valueOf(e.getId()).contains(search_product.getText()) || e.getName().contains(search_product.getText());
            String filter = search_product.getText();
            if(filter == null || filter.length() == 0) {
                filteredProducts.setPredicate(s -> true);
            } else {
                filteredProducts.setPredicate(productSearch);
            }
        });
    }

    /**
     * @param isPart True if id is for part, false if id is for product
     * @return ID for new part or product
     */
    public static int getID(boolean isPart) {
        return isPart ? partID++ : productID++;
    }

    /**
     * @return ObservableList of parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * @return ObservableList of products
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     * @param partName Name of part
     * @return ObservableList of parts that contain the partName
     */
    public ObservableList<Part> lookupPart(String partName) {
        return allParts.filtered(e -> e.getName().contains(partName));
    }

    /**
     * @param partId Id of part
     * @return Part that contains the matching partId
     */
    public Part lookupPart(int partId) {
        return allParts.filtered(e -> e.getId() == partId).get(0);
    }

    /**
     * @param productName Name of part
     * @return ObservableList of products that contain the productName
     */
    public  ObservableList<Product> lookupProduct(String productName) {
        return allProducts.filtered(e -> e.getName().contains(productName));
    }

    /**
     * @param productId Id of part
     * @return Product that contains the matching productId
     */
    public Product lookupProduct(int productId) {
        return allProducts.filtered(e -> e.getId() == productId).get(0);
    }


    /**
     * @param newPart Part that is added to allProducts observable list
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * creates add part scene
     */
    public void addPart() throws Exception {
        PartController.setSelectedPart(null);
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/part_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }

    /**
     * creates modify part scene, passes selected part to partController
     */
    public void modifyPart() throws Exception {
        Part part = part_table.getSelectionModel().getSelectedItem();
        PartController.setSelectedPart(part);
        if (part == null) { PartController.throwAlert("Error: No selected part", "Must select part to modify"); return; }
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/part_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }


    /**
     * @param index Index of Part in allParts observableArray
     * @param selectedPart Part to updated
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * @return True if part is deleted, false if not
     */
    public boolean deletePart() {
        try {
            if(confirmationAlert("Are you sure you want to delete this part?")) {
                Part part = part_table.getSelectionModel().getSelectedItem();
                if (part == null) { PartController.throwAlert("Error: No selected part", "Must select part to delete"); return false; }
                return deletePart(part);
            } return false;
        } catch (Exception e){
            PartController.throwAlert("Error deleting part", e.getLocalizedMessage());
            return false;
        }
    }

    /**
     * @param selectedPart part to be deleted
     * @return True if part is deleted, false if not
     */
    public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * @param newProduct Product that is added to allProducts observable list
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * creates add product scene
     */
    public void addProduct() throws Exception {
        ProductController.setModifiedProduct(null);
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/product_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }

    /**
     * creates modify product scene, passes selected product to productController
     */
    public void modifyProduct() throws Exception {
        Product product = product_table.getSelectionModel().getSelectedItem();
        if (product == null) { PartController.throwAlert("Error: No selected product", "Must select product to modify"); return; }
        ProductController.setModifiedProduct(product);
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/product_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }

    /**
     * @param index index of product to be updated
     * @param newProduct new product that will replace old product existing at index
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * @return True if product is deleted, false if not
     */
    public boolean deleteProduct() {
        try {
            if(confirmationAlert("Are you sure you want to delete this product?")) {
                Product product = product_table.getSelectionModel().getSelectedItem();
                if (product == null) { PartController.throwAlert("Error: No selected product", "Must select product to delete"); return false; }
                if (product.getAllAssociatedParts().size() >= 1){  PartController.throwAlert("Error deleting product", "Cannot delete product with attached parts"); return false;  }
                return deleteProduct(product);
            } return false;
        } catch (Exception e){
            PartController.throwAlert("Error deleting product", e.getLocalizedMessage());
            return false;
        }
    }

    /**
     * @param selectedProduct product to be deleted
     * @return True if product is deleted, false if not
     */
    public boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * @param mainText Text to display in confirmation alert body
     * @return boolean True if user confirmed
     */
    public static boolean confirmationAlert(String mainText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(mainText);
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    /**
     * closes scene
     */
    public void exitApplication() {
        Stage stage = (Stage) part_table.getScene().getWindow();
        stage.close();
    }
}
