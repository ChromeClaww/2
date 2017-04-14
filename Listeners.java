package Game;

import java.awt.event.*;

/**
 обработчик событий
 */
public class Listeners implements MouseListener, KeyListener, MouseMotionListener {
    // проверка нажатой клавиши
    private boolean isFiring_on; // дослать патрон

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();// получить код нажатой клавиши
        // проверка  клавиши
        if (key == KeyEvent.VK_W) {
            Player.up = true;
        }
        if (key == KeyEvent.VK_S) {
            Player.down = true;
        }
        if (key == KeyEvent.VK_A) {
            Player.left = true;
        }
        if (key == KeyEvent.VK_D) {
            Player.right = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            if (isFiring_on)// если патрон в патроннике
                Player.isFiring = true; //стрельба разрешена
            isFiring_on = false;  // нет патрона
        }
        if (key == KeyEvent.VK_ESCAPE) {
            GamePanel.state = GamePanel.STATES.MENUE;
        }

    }
    // проверка отжатой клавиши
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            Player.up = false;
        }
        if (key == KeyEvent.VK_S) {
            Player.down = false;
        }
        if (key == KeyEvent.VK_A) {
            Player.left = false;
        }
        if (key == KeyEvent.VK_D) {
            Player.right = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            Player.isFiring = false;
            isFiring_on = true; // патрон в патроннике
        }
    }
    public void keyTyped(KeyEvent e){

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        //GamePanel.player.isFiring = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {

            GamePanel.leftMouse = true;

            // if (isFiring_on)
            Player.isFiring = true;
            //isFiring_on = false;
        }
    }



    public void mouseReleased(MouseEvent e){
        if (e.getButton() == MouseEvent.BUTTON1) {
            GamePanel.player.isFiring = false;
            //isFiring_on = true;
            GamePanel.leftMouse = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {// метод переноса мышкой
        GamePanel.mouseX = e.getX();// получаем коорд. мыши
        GamePanel.mouseY = e.getY();//
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        GamePanel.mouseX = e.getX();//
        GamePanel.mouseY = e.getY();//
    }
}

