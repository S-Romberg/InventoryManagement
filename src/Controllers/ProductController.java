package Controllers;

import Models.Product;
import Models.Part;

public class ProductController {
    String name;
    double price;
    int stock;
    int min;
    int max;
    String additionalField;

    private static Part selectedPart;

    public void initialize() {
      System.out.println("YOOOO");
    };
}
