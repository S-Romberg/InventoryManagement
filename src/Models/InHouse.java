package Models;

import javafx.collections.ObservableList;

public class InHouse extends Part {
    private int machineId;
    public InHouse(int id, String name, double price, int stock, int min, int max, int initMachineId ){
        super(id, name, price, stock, min, max);
        machineId = initMachineId;
    }
    public int getMachineId(){
        return machineId;
    }
    public void setMachineId(int id){
        machineId = id;
    }
}
