package fruitsAndBox;

public class MainForBox {

    public static void main(String[] args) {

        Box<Apple> appleBox1 = new Box<>();
        Box<Apple> appleBox2 = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        appleBox1.addFruit(new Apple());
        orangeBox.addFruit(new Orange());

        //Сравнили коробку с 1 яблоком и коробку с 1 апельсином
        System.out.println(appleBox1.compare(orangeBox));

        appleBox1.addFruit(new Apple());
        appleBox1.addFruit(new Apple());
        orangeBox.addFruit(new Orange());

        //Сравнили коробку с 3 яблоками и коробку с 2 апельсинами
        System.out.println(appleBox1.compare(orangeBox));

        appleBox2.addFruit(new Apple());
        appleBox2.addFruit(new Apple());

        //Пересыпали 2 яблока из второй коробки в первую
        appleBox1.pour(appleBox2);

        System.out.println(appleBox1.getWeight());
        System.out.println(appleBox2.getWeight());
    }

}
