
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sergi
 */
public class ListenerTeclado implements KeyListener
{

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("KeyTyped: "+e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
       System.out.println("KeyPressed: "+e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("KeyReleased: "+e.getKeyCode());
    }
    
}
