package Models;

public class Outsourced extends Part{
    private String companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String initCompanyName) {
        super(id, name, price, stock, min, max);
        companyName = initCompanyName;
    }

    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String name){
        companyName = name;
    }
}
