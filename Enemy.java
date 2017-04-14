package Game;

import java.awt.*;

/**
 враги
 */
public class Enemy {

    private double x;
    private double y;
    private int r;
    private Color color;
    private int type;
    private int rank;
    private double speed;
    private double dx;
    private double dy;
    private double health;
    // Constructor

    public Enemy(int type,int rank){

        this.type = type;
        this.rank = rank;
        switch (type){
            case (1):color = Color.GREEN;
                switch (rank){
                    case (1):

                        x = Math.random() * GamePanel.WIDTH;
                        y = 0;
                        r = 7;
                        speed = 2;
                        health =1;
                        double angle = Math.toRadians(Math.random()*360);
                        dx = Math.sin(angle)*speed;
                        dy = Math.cos(angle)*speed;
                }
        }

    }
    // геттеры
    public double getX() {//для получения коорд х
        return x;}
    public double getY() {//для получения коорд у
        return y;
    }
    public int getR() {//для получения радиуса
        return r;}
    public int getH(){
        return (int) health;
    }
    // проверка здоровья
    public boolean remove_f() {
        if (health <= 0) {
            return true;
        }
        return false;
    }

    //уменьшение  здоровья
    public void hit() {
        health--;

    }

    // смещение
    public void update(){
        x += dx;
        y += dy;
        if(x <0 && dx < 0) dx =- dx;
        if(x>GamePanel.WIDTH && dx>0) dx = -dx;
        if(y<0 && dy < 0) dy =- dy;
        if(y>GamePanel.WIDTH && dy>0) dy = -dy;
    }
    // отрисовка
    public void draw(Graphics2D g){
        g.setColor(color);//передаём цвет
        g.fillOval((int)x-r,(int)y-r,2*r, 2*r);//рисуем пулю
        g.setStroke(new BasicStroke(3));
        g.setColor(color.darker());
        g.drawOval((int) (x-r),(int) (y-r), 2*r,2*r);
        g.setStroke(new BasicStroke(1));
    }

}

