package Controllers;
import Models.InhousePart;
import Models.Part;
import Models.Product;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Inventory extends Application {


    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partID = 0;
    private static int productID = 0;
    public Inventory() {}

    @Override
    public void start(Stage mainStage) throws Exception{
        Parent root =  FXMLLoader.load(getClass().getResource("../Views/inventory.fxml"));
        new InhousePart(1, "part", 1.0, 1,1,2,12);
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
    //+ lookupPart(partId:int):Parts
    //+ lookupProduct(productId:int):Product
    //+ lookupPart(partName:String):ObservableList<Part>
    //+ lookupProduct(productName:String):ObservableList<Product> + updatePart(index:int, selectedPart:Part):void
    //+ updateProduct(index:int, newProduct:Product):void
    //+ deletePart(selectedPart:Part):boolean
    //+ deleteProduct(selectedProduct:Product):boolean

    public static int getID(boolean isPart) {
        return isPart ? partID++ : productID++;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public  ObservableList<Part> getAllParts(){
        return allParts;
    }
    public ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    public void addPart() throws Exception {
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/add_part.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }

    public void modifyPart() throws Exception {
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/modify_part.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }

    public void deletePart() {
        System.out.println("delete");
    }
    public void addProduct() throws Exception {
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/add_product.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }
    public void modifyProduct() throws Exception {
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/modify_product.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }
    public void deleteProduct() {
        System.out.println("delete");
    }
    public void exitApplication() {
        System.out.println("exit");
    }
}
