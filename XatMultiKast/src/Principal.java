
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sergi
 */
public class Principal {
    
    public static Gui ventanaReg;
    public static MulticastServerSocket mssReg;
    public static MulticastClientSocket mcsReg;
    public static HashMap listaMiembrosActivos=new HashMap();
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // creacion del GUI
        Gui ventana=new Gui();
        Principal.ventanaReg=ventana;
        
        ventanaReg.setVisible(true);
        ventanaReg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Creacion Listener
        ListenerTeclado lt=new ListenerTeclado();
        ventanaReg.jTextFieldInputText.addKeyListener(lt);
        
       
        
       
    }
    
    public static void enviar()
    {   
        String mensaje=Principal.ventanaReg.jTextFieldInputText.getText().toString();
        Principal.mcsReg.enviar(mensaje);
    }
    
    public static void enviar(String mensaje)
    {   
        
        Principal.mcsReg.enviar(mensaje);
    }
    public static void iniciarServerSocket() throws IOException
    {   
        //Aqui se une
        MulticastServerSocket mss=new MulticastServerSocket(ventanaReg.jTextFieldIp.getText()
                                                            ,new Integer(ventanaReg.jTextFieldPuerto.getText()));
        Principal.mssReg=mss;
    
    Timer timer = new Timer (2000, new ActionListener () 
    { 
    @Override
    public void actionPerformed(ActionEvent e) 
    { 
        Principal.mssReg.unirseGrupo(Principal.mssReg.ip);
        System.out.println("yea");
    } 
    }); 
    timer.start();
    
    mss.start();
        
    }
    public static void iniciarClientSocket() throws IOException
    {   
        MulticastClientSocket mcs=new MulticastClientSocket(ventanaReg.jTextFieldIp.getText()
                                                            ,new Integer(ventanaReg.jTextFieldPuerto.getText())); 
        Principal.mcsReg=mcs;
    }
    public static String recuperarArray(byte[] datos)
    {
        String cadena=new String(datos);
        String cadenaCortada[]=cadena.split("\\n");
        System.out.println(cadenaCortada[0]);
        return cadenaCortada[0]+"\n";
    }

    static void actualizar(String sMensaje) 
    {   
       Principal.ventanaReg.jEditorPaneOutputText.setText(Principal.ventanaReg.jEditorPaneOutputText.getText()+sMensaje);
    }

    static void añadirMiembro(String sMensaje) 
    {
       Principal.ventanaReg.jEditorPaneMiembros.setText("");
       
       listaMiembrosActivos.put(sMensaje,sMensaje);
        for (Iterator it = listaMiembrosActivos.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            Principal.ventanaReg.jEditorPaneMiembros.setText(Principal.ventanaReg.jEditorPaneMiembros.getText()
                                                        +"-> "+(String)key);
        }
       
    }
    
}
