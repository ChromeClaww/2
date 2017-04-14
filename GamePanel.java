package Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;


public class GamePanel extends JPanel implements Runnable {
    // задаём размер панели
    public static int WIDTH = 600;
    public static int HEIGHT = 600;
    private Thread thread; // Создаем поток- ссылка на обьект класса Thread
    private BufferedImage image; // ссылка на обьект класса
    private Graphics2D g; // ссылка на обьект класса
    public static GameBack background;// ссылка на обьект класса
    public static Player player;// ссылка на обьект класса
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    public static Wave wave;
    public static Menue menue;

    public static int mouseX;// координаты мышки
    public static int mouseY;
    public static boolean leftMouse;

    private int FPS;//
    private double millisToFPS;// fps в миллсек
    private long timerFPS;// таймер fps
    private int sleepTime; //сколько он будет спать

    public static enum STATES {MENUE, PLAY} //обьявляем перечсления

    public static STATES state = STATES.MENUE;// переменная меню

    // конструктор
    public GamePanel() {
        super(); // активируем консруктор родителя
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // размер передаем в обьект класса Измерения

        setFocusable(true); //передаем фокус
        requestFocus(); // акивируем

        addMouseListener(new Listeners());// добавляем обработчик событий клик мышь
        addKeyListener(new Listeners());// добавляем обработчик событий клаава
        addMouseMotionListener(new Listeners());//добавляем обработчик событий перем мышь
    }

    // запуск потока
    public void start() {
        thread = new Thread((Runnable) this);
        thread.start();// запускаем поток
    }

    //метод от интерфейса Runnable (потока)
    @Override
    public void run() {
        FPS = 30; // задаем желаемый FPS
        millisToFPS = 1000 / FPS; //пересчет в миллисек
        sleepTime = 0;

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//сглаживание соседних пиксеей

        leftMouse = false;
        background = new GameBack();
        player = new Player();
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        wave = new Wave();
        menue = new Menue();//

        Toolkit kit = Toolkit.getDefaultToolkit();// переназначение
        BufferedImage buffered = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);// панель для рисования 16х16
        Graphics2D g3 = (Graphics2D) buffered.getGraphics();//
        g3.setColor(new Color(255, 255, 255));//
        g3.drawOval(0, 0, 16, 16);//
        g3.drawLine(8, 0, 8, 16);//
        g3.drawLine(0, 8, 16, 8);//
        Cursor myCursor = kit.createCustomCursor(buffered, new Point(16, 16), "myCursor");
        g3.dispose();

        while (true) { // игровой цикл
            this.setCursor(myCursor);

            if (state.equals(STATES.MENUE)) {// если пер state == MENUE
                background.update();// фон обновляем
                background.draw(g);// рисуем фон
                menue.update();//меню обновляем
                menue.draw(g);// рисуем меню
                gameDraw();// перерсовываем на панель
            }
            if (state.equals(STATES.PLAY)) {// ира
                gameUpdate(); //обновление
                gameRender(); //перерисовка
                gameDraw(); ////перенос изображения на панель

            }
            timerFPS = System.nanoTime();// присвоим текущ время


            timerFPS = (System.nanoTime() - timerFPS) / 1000000;//сколько прошло миллсек на операции выше
            if (millisToFPS > timerFPS) {
                sleepTime = (int) (millisToFPS - timerFPS);
            } else sleepTime = 1;

            try {
                thread.sleep(sleepTime); //засыпаем на ... мс
            } catch (InterruptedException ex) { //если не удается заснуть- исключение
                ex.printStackTrace();
            }
            timerFPS = 0;// обнуляем таймер
            sleepTime = 1;// обновляем время сна
        }
    }

    //обновление
    public void gameUpdate() { //обновление

        background.update();// вызов метода для заднего плана
        player.update();// обновление

        if (player.health <= 0) { // если правдиво
            JOptionPane.showMessageDialog(null, "BOOM!!!! BOOM!!!");
            System.exit(1); //выход в систему ;// удалить элемент из списка врагов
        }


        //bullets.update
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update();// обновлям текущую пулю
            boolean remove_p = bullets.get(i).remove_f();//текущую пулю проверяем где она
            if (remove_p) { // если правдиво(улетела)
                bullets.remove(i);//удаляем пулю которая вылетела
                i--;//
            }
        }
        //enemies.update
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();// обновлям текущего врага
        }
        //столкновение пули с врагом
        for (int i = 0; i < enemies.size(); i++) {// каждого врвгв из списка
            Enemy e = enemies.get(i); // выделяем элемент списка
            double ex = e.getX();// получаем коорд элемента
            double ey = e.getY();
            //по списку пуль
            for (int j = 0; j < bullets.size(); j++) {
                Bullet b = bullets.get(j);// выделяем элемент списка
                double bx = b.getX();// получаем коорд элемента
                double by = b.getY();
                double dx = ex - bx;// вычисляем разницу коорд элементов
                double dy = ey - by;
                double dist = Math.sqrt(dx * dx + dy * dy); // расстояние от друг друга
                if ((int) dist <= e.getR() + b.getR()) { // если расстояние от друг друга меньше двух радиусов
                    e.hit();// метод уменьшения здоровья врага
                    bullets.remove(j); // удаляем пулю из списка
                    //Проверка здоровья врага
                    boolean remove_p = e.remove_f(); // пер присваив значение метода пров здоров врага
                    if (remove_p) { // если правдиво
                        enemies.remove(i);// удалить элемент из списка врагов
                        i--;
                        //
                        break;
                    }
                }
            }
        }
        // Столкновение героя с врагом
        for (int i = 0; i < enemies.size(); i++) {// каждого врвгв из списка
            Enemy e = enemies.get(i); // выделяем элемент списка
            double ex = e.getX();// получаем коорд элемента
            double ey = e.getY();

            double px = player.getX();// получаем коорд элемента
            double py = player.getY();
            double er = player.getR();
            double dx = ex - px;// вычисляем разницу коорд элементов
            double dy = ey - py;
            double dist = Math.sqrt(dx * dx + dy * dy); // расстояние от друг друга
            if ((int) dist < e.getR() + player.getR()) { // если расстояние от друг друга меньше двух рад
                e.hit();
                player.hit();
                //Проверка здоровья врага
                boolean remove_p = e.remove_f(); // пер присваив значение метода пров здоров врага
                if (remove_p) { // если правдиво
                    enemies.remove(i);// удалить элемент из списка врагов
                    i--;
                }

            }
        }
        // Вонна обновить Wave update
        wave.update();
    }


    //рисуем в виртуальном окне
    public void gameRender() { //перерисовка
        background.draw(g);// вызов метода для заднего плана
        player.draw(g);
        //перерисовка - вызов метода для bullet
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }
        //перерисовка - вызов метода для enemies
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);// рисуем текущего врага
        }
        //перерисовка - вызов метода для волны
        //wave.draw(g);// вызов метода перерисовки для волны
        if (wave.showWave()) {
            wave.draw(g);// вызов метода перерисовки для волны
        }

    }

    //перенос изображения на панель
    private void gameDraw() {
        Graphics g2 = this.getGraphics();// переоппред Graphics2d на Graphics
        g2.drawImage(image, 0, 0, null);// рисуем
        g2.dispose();// команда для уборщщика мусора

    }
}


