package Controllers;
import Models.InHouse;
import Models.Outsourced;
import Models.Part;
import Models.Product;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Inventory extends Application {


    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partID = 0;
    private static int productID = 0;
    @FXML
    private TableView<Part> part_table;
    public Inventory() {}

    public void initialize () {
        part_table.setItems(allParts);
    }

    @Override
    public void start(Stage mainStage) throws Exception{
        Parent root =  FXMLLoader.load(getClass().getResource("../Views/inventory.fxml"));
        new InHouse(1, "part", 1.0, 1,1,2,12);
        mainStage.setTitle("Inventory Management System");
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }

    public static void printList(){
        allParts.forEach((part) -> System.out.println("Name:  " + part.getName()));
    }

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public static int getID(boolean isPart) {
        return isPart ? partID++ : productID++;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }


    public static Part lookupPart(int partId) {
        for (Part part : getAllParts()) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    //+ lookupProduct(productId:int):Product
    //+ lookupPart(partName:String):ObservableList<Part>
    //+ lookupProduct(productName:String):ObservableList<Product> + updatePart(index:int, selectedPart:Part):void
    //+ updateProduct(index:int, newProduct:Product):void
    //+ deletePart(selectedPart:Part):boolean
    //+ deleteProduct(selectedProduct:Product):boolean

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
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/part_form.fxml"));
        Stage stage = new Stage();
        System.out.println("Modify Part");
        System.out.println(part);
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }

    public static void updatePart(Part modifiedPart, int max, int min, double price, int stock, String name, String additionalField) {
        modifiedPart.setStock(stock);
        modifiedPart.setPrice(price);
        modifiedPart.setMax(max);
        modifiedPart.setMin(min);
        modifiedPart.setName(name);
        if (modifiedPart instanceof InHouse){
            ((InHouse) modifiedPart).setMachineId(Integer.parseInt(additionalField));
        }
        assert modifiedPart instanceof Outsourced;
        ((Outsourced) modifiedPart).setCompanyName(additionalField);
    }

    public boolean deletePart() {
        System.out.println("delete");
        return true;
    }
    public void addProduct() throws Exception {
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/product_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }
    public void modifyProduct() throws Exception {
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/product_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }
    public boolean deleteProduct() {
        System.out.println("delete");
        return true;
    }

    public void exitApplication() {
        System.out.println("exit");
    }
}
