package Game;

import java.awt.*;

/**
 главный герой игры
 */
public class Player {

    private  double x;//координа х героя
    private  double y;
    private int r; // радиус героя
    private int speed;// скорость
    private Color color1;// цвет
    private Color color2;// цвет2

    // стаич прем - кавиши перемещения

    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;


    private double dx;// смещение
    private double dy;
    public static boolean isFiring;//стрельба
    public double health;// здоровье
    // Constructor
    public Player(){
        x = GamePanel.WIDTH / 2;// нач координаты героя
        y = GamePanel.HEIGHT / 2;
        r = 5;
        speed = 5;
        color1 = Color.WHITE;
        health = 3;
        // смещение
        dx = 0;
        dy = 0;
        // нач знач клавиш
        up = false;
        down = false;
        left = false;
        right = false;
        isFiring = false;
    }
    //  гетеры
    public double getX(){
        return  x;
    }
    public double getY(){
        return  y;
    }
    public double getR(){
        return  r;
    }

    // - здоровья
    public void hit() {
        health--;

    }



    // обновления
    public void update(){
        if (health <=0){
            //killed = true;
            health = 0;}

        // смещение героя по игровому полю
        if (up && y >r){
            y -=  speed;
        }
        if (down && y <GamePanel.HEIGHT -r){
            y += speed;
        }
        if (left && x >r){
            x -= speed;
        }
        if (right && x <GamePanel.WIDTH -r){
            x += speed;
        }
        if(up && left || up && right || down && left || down && right){
            dy = dy * Math.sin(Math.toRadians(45));
            dx = dx * Math.cos(Math.toRadians(45));
        }
        y += dy;
        x += dy;

        dy = 0;
        dx = 0;

        if (isFiring){ // если стрельба true
            GamePanel.bullets.add(new Bullet());
            isFiring = false;//запрет для стрельбы
        }

    }

    // отрисовка героя
    public void draw(Graphics2D g){


        Color newColor = new Color(0, 200, 250);//созд обьект клсса цвет
        g.setColor(newColor);// передаём цвет граф объекту
        g.fillRect(0, 0, GamePanel.WIDTH, 20);//рисуем прямоугольную область

        g.setColor(Color.WHITE);//задаем цвет объекту Соlor
        Font font = new Font("Arial",Font.ITALIC,20);//Создём объект класса фонт (передаем в конструктор параметры)
        g.setFont(font);//устанвливаем наш шрифт
        ((Graphics2D) g).drawString("жизнь - "+(int)health,50,15 );//отрисовываем строку

        g.setColor(color1);
        g.fillOval((int) (x-r),(int) (y-r), 2*r,2*r);
        g.setStroke(new BasicStroke(3));
        g.setColor(color1.darker());
        g.drawOval((int) (x-r),(int) (y-r), 2*r,2*r);
        g.setStroke(new BasicStroke(1));
    }

}

