1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС). Используйте wait/notify/notifyAll.
2**. На серверной стороне сетевого чата реализовать управление потоками через ExecutorService.
3 Приведённый код перенести в новый проект.
public class racing.MainClass {
    public static final int CARS_COUNT = 4;
    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        racing.Race race = new racing.Race(new racing.Road(60), new racing.Tunnel(), new racing.Road(40));
        racing.Car[] cars = new racing.Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new racing.Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
public class racing.Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private racing.Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public racing.Car(racing.Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
    }
}
public abstract class racing.Stage {
    protected int length;
    protected String description;
    public String getDescription() {
        return description;
    }
    public abstract void go(racing.Car c);
}
public class racing.Road extends racing.Stage {
    public racing.Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public void go(racing.Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
public class racing.Tunnel extends racing.Stage {
    public racing.Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }
    @Override
    public void go(racing.Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
public class racing.Race {
    private ArrayList<racing.Stage> stages;
    public ArrayList<racing.Stage> getStages() { return stages; }
    public racing.Race(racing.Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}
Организуем гонки:
Все участники должны стартовать одновременно, несмотря на то, что на подготовку у каждого из них уходит разное время.
В туннель не может заехать одновременно больше половины участников (условность).
Попробуйте всё это синхронизировать.
Только после того как все завершат гонку, нужно выдать объявление об окончании.
Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent.
Пример выполнения кода до корректировки:

ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!
Участник #2 готовится
ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!
ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!
Участник #1 готовится
Участник #4 готовится
Участник #3 готовится
Участник #3 готов
Участник #3 начал этап: Дорога 60 метров
Участник #2 готов
Участник #2 начал этап: Дорога 60 метров
Участник #1 готов
Участник #1 начал этап: Дорога 60 метров
Участник #4 готов
Участник #4 начал этап: Дорога 60 метров
Участник #3 закончил этап: Дорога 60 метров
Участник #3 готовится к этапу(ждет): Тоннель 80 метров
Участник #3 начал этап: Тоннель 80 метров
Участник #2 закончил этап: Дорога 60 метров
Участник #2 готовится к этапу(ждет): Тоннель 80 метров
Участник #2 начал этап: Тоннель 80 метров
Участник #1 закончил этап: Дорога 60 метров
Участник #1 готовится к этапу(ждет): Тоннель 80 метров
Участник #1 начал этап: Тоннель 80 метров
Участник #4 закончил этап: Дорога 60 метров
Участник #4 готовится к этапу(ждет): Тоннель 80 метров
Участник #4 начал этап: Тоннель 80 метров
Участник #2 закончил этап: Тоннель 80 метров
Участник #2 начал этап: Дорога 40 метров
Участник #3 закончил этап: Тоннель 80 метров
Участник #3 начал этап: Дорога 40 метров
Участник #2 закончил этап: Дорога 40 метров
Участник #1 закончил этап: Тоннель 80 метров
Участник #1 начал этап: Дорога 40 метров
Участник #4 закончил этап: Тоннель 80 метров
Участник #4 начал этап: Дорога 40 метров
Участник #3 закончил этап: Дорога 40 метров
Участник #1 закончил этап: Дорога 40 метров
Участник #4 закончил этап: Дорога 40 метров

Что примерно должно получиться:

ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!
Участник #2 готовится
Участник #1 готовится
Участник #4 готовится
Участник #3 готовится
Участник #2 готов
Участник #4 готов
Участник #1 готов
Участник #3 готов
ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!
Участник #2 начал этап: Дорога 60 метров
Участник #4 начал этап: Дорога 60 метров
Участник #3 начал этап: Дорога 60 метров
Участник #1 начал этап: Дорога 60 метров
Участник #1 закончил этап: Дорога 60 метров
Участник #3 закончил этап: Дорога 60 метров
Участник #3 готовится к этапу(ждет): Тоннель 80 метров
Участник #1 готовится к этапу(ждет): Тоннель 80 метров
Участник #1 начал этап: Тоннель 80 метров
Участник #3 начал этап: Тоннель 80 метров
Участник #4 закончил этап: Дорога 60 метров
Участник #4 готовится к этапу(ждет): Тоннель 80 метров
Участник #2 закончил этап: Дорога 60 метров
Участник #2 готовится к этапу(ждет): Тоннель 80 метров
Участник #3 закончил этап: Тоннель 80 метров
Участник #1 закончил этап: Тоннель 80 метров
Участник #2 начал этап: Тоннель 80 метров
Участник #4 начал этап: Тоннель 80 метров
Участник #3 начал этап: Дорога 40 метров
Участник #1 начал этап: Дорога 40 метров
Участник #3 закончил этап: Дорога 40 метров
Участник #3 - WIN
Участник #1 закончил этап: Дорога 40 метров
Участник #4 закончил этап: Тоннель 80 метров
Участник #4 начал этап: Дорога 40 метров
Участник #2 закончил этап: Тоннель 80 метров
Участник #2 начал этап: Дорога 40 метров
Участник #2 закончил этап: Дорога 40 метров
Участник #4 закончил этап: Дорога 40 метров
ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!