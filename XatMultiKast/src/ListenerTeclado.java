
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
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
       System.out.println("click"+e.getKeyCode());
       if(e.getKeyCode()==10)
        {
            //Enter
            if(Principal.ventanaReg.jButtonSend.isEnabled())
            {
                Principal.ventanaReg.jButtonSend.doClick();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }
    
}
