package Controllers;
import Models.InHouse;
import Models.Outsourced;
import Models.Part;
import Models.Product;
import com.sun.jdi.event.ExceptionEvent;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public void initialize () {
        part_table.setItems(filteredParts);
        product_table.setItems(filteredProducts);

        Predicate<Part> partSearch = e -> String.valueOf(e.getId()).contains(search_part.getText()) || e.getName().contains(search_part.getText());
        Predicate<Product> productSearch = e -> String.valueOf(e.getId()).contains(search_product.getText()) || e.getName().contains(search_product.getText());

        search_part.textProperty().addListener(obs-> {
            String filter = search_part.getText();
            if(filter == null || filter.length() == 0) {
                filteredParts.setPredicate(s -> true);
            } else {
                filteredParts.setPredicate(partSearch);
            }
        });
        search_product.textProperty().addListener(obs-> {
            String filter = search_part.getText();
            if(filter == null || filter.length() == 0) {
                filteredProducts.setPredicate(s -> true);
            }else {
                filteredProducts.setPredicate(productSearch);
            }
        });
    }


    @Override
    public void start(Stage mainStage) throws Exception{
        Parent root =  FXMLLoader.load(getClass().getResource("../Views/inventory.fxml"));
        new InHouse(1, "part", 1.0, 1,1,2,12);
        mainStage.setTitle("Inventory Management System");
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void printList(){
        allParts.forEach((part) -> System.out.println("Name:  " + part.getName()));
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

    public void addPart() throws Exception {
        PartController.setSelectedPart(null);
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/part_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }

    public void modifyPart() throws Exception {
        Part part = part_table.getSelectionModel().getSelectedItem();
        PartController.setSelectedPart(part);
        if (part == null) { PartController.throwAlert("Error: No selected part", "Must select part to modify"); return; }
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/part_form.fxml"));
        Stage stage = new Stage();
        System.out.println("Modify Part");
        System.out.println(part);
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }


    /**
     * @param index Index of Part in allParts observableArray
     * @param inHouse True if class is Inhouse, false if class is Outsourced
     * @param id Part ID from modified part
     * @param max Part max from form
     * @param min Part min from form
     * @param price Part price from form
     * @param stock Part stock from form
     * @param name Part name from form
     * @param additionalField Either InHouse MachineId from form or Outsourced CompanyName from form
     */

     // + updatePart(index:int, selectedPart:Part):void
    public static void updatePart(int index, boolean inHouse, int id, int max, int min, double price, int stock, String name, String additionalField) {
        Part modifiedPart;
        if (inHouse) {
            modifiedPart = new InHouse(id, name, price, stock, min, max, Integer.parseInt(additionalField));
        } else {
            modifiedPart = new Outsourced(id, name, price, stock, min, max, additionalField);
        }
        allParts.set(index, modifiedPart);
    }

    /**
     * @return True if part is deleted, false if not
     */
    public boolean deletePart() {
        System.out.println("delete");
        return true;
    }

    /**
     * @param newProduct Product that is added to allProducts observable list
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }


    public void addProduct() throws Exception {
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/product_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }

    public void modifyProduct() throws Exception {
        Product product = product_table.getSelectionModel().getSelectedItem();
        if (product == null) { PartController.throwAlert("Error: No selected product", "Must select product to modify"); return; }
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/product_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }

    //+ updateProduct(index:int, newProduct:Product):void

    /**
     * @return True if product is deleted, false if not
     */
    public boolean deleteProduct() {
        System.out.println("delete");
        return true;
    }

    public void exitApplication() {
        Stage stage = (Stage) part_table.getScene().getWindow();
        stage.close();
    }
}
