package Models;

public class InhousePart extends Part {
    private int machineId;
    public InhousePart(int id, String name, double price, int stock, int min, int max, int machineId ){
        super(id, name, price, stock, min, max);
        machineId = machineId;
    }
}
