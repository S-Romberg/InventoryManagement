package Models;

public class InHouse extends Part {
    private int machineId;
    public InHouse(int id, String name, double price, int stock, int min, int max, int initMachineId ){
        super(id, name, price, stock, min, max);
        machineId = initMachineId;
    }

    /**
     * @return Machine ID integer
     */
    public int getMachineId(){
        return machineId;
    }

    /**
     * @param id the machine id to set
     */
    public void setMachineId(int id){
        machineId = id;
    }
}
