package Game;

/**
 Задний фон игровой панели
 */
import javax.swing.*;
import java.awt.*;

public class GameBack {

    private Color color;

    Image img = new ImageIcon("res/Java.jpg").getImage();//загрузка картинки

    public GameBack() {
        color = Color.BLUE;

    }

    public void update() {  //обновление

    }

    public void draw(Graphics2D g) {  //прорисовка в Graphics2D

        g.setColor(color);//передаём цвет граф объекту
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);//рисуем прямоугольную область

        Color newColor = new Color(200, 200, 0);//созд обьект клсса цвет
        g.setColor(newColor);// передаём цвет граф объекту
        g.fillRect(0, 0, GamePanel.WIDTH, 20);//рисуем прямоугольную область
        g.setColor(Color.WHITE);//задаем цвет объекту Соlor
        Font font = new Font("Arial",Font.ITALIC,20);//Создём объект класса фонт (передаем в конструктор параметры)
        g.setFont(font);//устанвливаем наш шрифт font

        ((Graphics2D) g).drawString("Виктор Курышев -       www.programmingnew.tk",50,15 );//отрисовываем строку
    }
}