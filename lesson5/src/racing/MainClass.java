package racing;

import java.util.ArrayList;
import java.util.List;

public class MainClass {
    public static final int CARS_COUNT = 4;
    private static List<Thread> carsThreads = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(CARS_COUNT, new Road(60), new Tunnel(CARS_COUNT / 2), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (Car car : cars) {
            Thread newThread = new Thread(car);
            carsThreads.add(newThread);
            newThread.start();
        }
        for (int i = 0; i < cars.length; i++) {
            carsThreads.get(i).join();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}








