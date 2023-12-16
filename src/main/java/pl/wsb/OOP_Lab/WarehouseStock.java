package pl.wsb.OOP_Lab;

public class WarehouseStock {
    private SupportedMetalType metalType;
    private double mass;

    public WarehouseStock(SupportedMetalType metalType, double mass) {
        this.metalType = metalType;
        this.mass = mass;
    }

    public SupportedMetalType getMetalType() {
        return metalType;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getMass() {
        return mass;
    }
}
