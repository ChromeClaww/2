package Game;

import java.awt.*;

/**
 * пуля
 */
public class Bullet {

    private double x;
    private double y;
    private int r;
    private Color color;
    private int speed;

    private double bulletDX;//смещение по х
    private double bulletDY;//
    private double distX;// разница по х от мышки до пули
    private double distY;//
    private double dist;// расстояние от мышки до пули

    // Constructor

    public Bullet() {

// присвиваем нач положение пули = коодинатам героя
        x = GamePanel.player.getX();
        y = GamePanel.player.getY();
        r = 2;// радиус пули
        speed = 20; // скорость

        distX = GamePanel.mouseX - x;// разница по х от мышки до пули
        distY = y - GamePanel.mouseY;
        dist = (Math.sqrt(distX * distX + distY * distY));//  расстояние от мышки до пули
        bulletDX = distX / dist * speed;// смещение по х
        bulletDY = distY / dist * speed;

        color = Color.WHITE;// цвет */
    }

    // геттеры
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getR() {//для получения радиуса
        return r;
    }

    // смещение пули
    public void update() {


        //y = y - speed;
        y = y - speed * distY / (Math.sqrt(distX * distX + distY * distY));
        x = x + speed * distX / (Math.sqrt(distX * distX + distY * distY));
    }

    // отрисовка
    public void draw(Graphics2D g) {
        g.setColor(color);//передаём цвет
        g.fillOval((int) x, (int) y, r, 2 * r);//рисуем пулю

    }

    // проверка где пуля
    public boolean remove_f() {
        if (y < 0 || y > GamePanel.HEIGHT || x < 0 || x > GamePanel.WIDTH) { //если вылетела
            return true;
        }
        return false;

    }
}


