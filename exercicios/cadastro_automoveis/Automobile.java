package exercicios.cadastro_automoveis;

public class Automobile {
    private String model;
    private String plate;
    private double km;
    private double price;

    public Automobile(String plate, String model, double km, double price) {
        this.plate = plate;
        this.model = model;
        this.price = price;
        this.km = km;
    }

    public String getPlate() {
        return plate;
    }

    @Override
    public String toString() {
        return "Placa: " + plate + ", Modelo: " + model + ", Preço: " + price + ", KM: " + km;
    }
}
