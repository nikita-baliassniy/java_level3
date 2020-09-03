package fruitsAndBox;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {

    private List<T> set = new ArrayList<>();

    // Метод получения веса всей коробки
    public double getWeight() {
        double weight = 0.0;
        for (T t : set) {
            weight += t.getWeight();
        }
        return weight;
    }

    // Метод добавления фрукта в коробку
    public void addFruit(T fruit) {
        set.add(fruit);
    }

    // Метод сравнения веса любых коробок
    public boolean compare(Box box) {
        return box.getWeight() == this.getWeight();
    }

    // Метод пересыпания фруктов из sourceBox в текущую коробку
    public void pour(Box<T> sourceBox) {
        if (sourceBox.getWeight() != 0) {
            this.set.addAll(sourceBox.set);
            sourceBox.set.clear();
        } else {
            System.out.println("This box is empty! Nothing to pour!");
        }

    }

}
