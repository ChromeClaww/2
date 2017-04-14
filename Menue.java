package Game;

import java.awt.*;
import java.io.Console;

/**
 * меню игры
 */
public class Menue {
    private int buttonWidth;//шширина кнопки
    private int buttonHeight;//высота кнопки
    private Color color1;//цвет
    private String s;//надпись
    private int transp = 0;// прозрачность

    //
    public Menue(){
        buttonWidth = 120;
        buttonHeight =60;
        color1 = Color.WHITE ;
        s = "Старт";
    }
    public void update(){
        if (GamePanel.mouseX >GamePanel.WIDTH/2 - buttonWidth/2 &&
                GamePanel.mouseX < GamePanel.WIDTH/2 + buttonWidth/2 &&
                GamePanel.mouseY > GamePanel.HEIGHT/2 - buttonHeight/2 &&
                GamePanel.mouseY < GamePanel.HEIGHT/2 + buttonHeight/2){// если курсор попал в кнопку
            transp = 60;
            if (GamePanel.leftMouse){
                GamePanel.state = GamePanel.STATES.PLAY;
            }
        }else {
            transp = 0;
        }
    }

    public void draw(Graphics2D g){
        g.setColor(color1);//передаём цвет
        g.setStroke(new BasicStroke(3));//обводка
        g.drawRect(GamePanel.WIDTH/2-buttonWidth/2,GamePanel.HEIGHT/2-buttonHeight/2,buttonWidth,buttonHeight);//квадрат по центру

        g.setColor(new Color(255,255,255, transp));//передаём цвет c прозрачностью
        g.fillRect(GamePanel.WIDTH/2-buttonWidth/2,GamePanel.HEIGHT/2-buttonHeight/2,buttonWidth,buttonHeight);//квадрат по центру

        g.setStroke(new BasicStroke(1));//обводка
        g.setColor(color1);//передаем цвет
        g.setFont(new Font("Consolas",Font.BOLD,40));//передаем шрифт
        long length = (int) g.getFontMetrics().getStringBounds(s,g).getWidth();//ширина строки
        g.drawString(s,(int)(GamePanel.WIDTH/2-length/2),(int)(GamePanel.HEIGHT/2+buttonHeight/4));//рисуем надпись
    }

}
