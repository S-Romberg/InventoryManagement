package Controllers;
import Models.Part;
import Models.Product;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Inventory extends Application {


    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;
    public Inventory(ObservableList<Part> allParts, ObservableList<Product> allProducts) {
        this.allParts = allParts;
        this.allProducts = allProducts;
    }
    public Inventory() {
    }


    @Override
    public void start(Stage mainStage) throws Exception{
        Parent root =  FXMLLoader.load(getClass().getResource("../Views/inventory.fxml"));
        mainStage.setTitle("Inventory Management System");
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }

    public void addPart(Part newPart){
        System.out.println(newPart);
    };

    public void addProduct(Product newProduct){
        System.out.println(newProduct);
    };
    //+ addProduct(newProduct:Product):void
    //+ lookupPart(partId:int):Part
    //+ lookupProduct(productId:int):Product
    //+ lookupPart(partName:String):ObservableList<Part>
    //+ lookupProduct(productName:String):ObservableList<Product> + updatePart(index:int, selectedPart:Part):void
    //+ updateProduct(index:int, newProduct:Product):void
    //+ deletePart(selectedPart:Part):boolean
    //+ deleteProduct(selectedProduct:Product):boolean
    public ObservableList<Part> getAllParts(){
        return this.allParts;
    }
     public ObservableList<Product> getAllProducts(){
         System.out.println(this.allProducts);
         return this.allProducts;
     }

    public static void main(String[] args) {
        launch(args);
    }

    public void addPart(MouseEvent mouseEvent) throws Exception {

        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/add_part.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }
    public void modifyPart(MouseEvent mouseEvent) throws Exception {
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/modify_part.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }
    public void deletePart(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
    }
    public void addProduct(MouseEvent mouseEvent) throws Exception {
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/add_product.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }
    public void modifyProduct(MouseEvent mouseEvent) throws Exception {
        Parent addPartPage = FXMLLoader.load(getClass().getResource("../Views/modify_product.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(addPartPage));
        stage.show();
    }
    public void deleteProduct(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
    }
    public void exitApplication(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
    }
}
