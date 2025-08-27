package exercicios.cadastro_automoveis;

import java.util.ArrayList;

public class Automobiles {
    private ArrayList<Automobile> automobiles = new ArrayList<Automobile>();

    private boolean checkIfExists(String plate) {
        return this.automobiles.stream().anyMatch(automobile -> automobile.getPlate().equalsIgnoreCase(plate));
    }

    public boolean add(Automobile automobile) {
        boolean exists = checkIfExists(automobile.getPlate());
        if (!exists) {
            automobiles.add(automobile);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeByPlate(String plate) {
        boolean exists = checkIfExists(plate);
        if (exists) {
            this.automobiles.removeIf(automobile -> automobile.getPlate().equalsIgnoreCase(plate));
            return true;
        } else {
            return false;
        }
    }

    public Automobile getByPlate(String plate) {
        return this.automobiles.stream()
                .filter(auto -> auto.getPlate().equalsIgnoreCase(plate))
                .findFirst()
                .orElse(null);
    }

    public ArrayList<Automobile> getAutomobiles() {
        ArrayList<Automobile> clone = (ArrayList<Automobile>) automobiles.clone();
        return clone;
    }
}
