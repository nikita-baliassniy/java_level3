public class CharacterThreads {

    private static volatile char[] chars = new char[]{'A', 'B', 'C'};

    public static void main(String[] args) {

        new Thread(new CharThread('A')).start();
        new Thread(new CharThread('B')).start();
        new Thread(new CharThread('C')).start();

    }

    public static class CharThread implements Runnable {

        private char letter;
        public final static Object o = new Object();
        private static volatile int letterToPrintNumber = 0;

        public CharThread(char letter) {
            this.letter = letter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                synchronized (o) {
                    try {
                        while (letter != chars[letterToPrintNumber]) {
                            o.wait();
                        }
                        System.out.print(letter);
                        letterToPrintNumber = (letterToPrintNumber + 1) % chars.length;
                        o.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
