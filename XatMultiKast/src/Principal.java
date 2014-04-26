
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JButton;
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
    public static HashMap listaMiembrosActivosVivos=new HashMap();
    public static Timer timer;
    
    
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
        if (Principal.ventanaReg.jCheckBoxEcho.isSelected())
        {
            Principal.actualizar("(Echo):"+Principal.ventanaReg.jTextFieldInputText.getText()+"\n");
        }
        Calendar ahora=Calendar.getInstance();
        String mensaje="3("+Principal.ventanaReg.jTextFieldNick.getText().toString()+"| "+ahora.get(Calendar.HOUR_OF_DAY)
                         +":"+ahora.get(Calendar.MINUTE)+"): "+
                       Principal.ventanaReg.jTextFieldInputText.getText().toString();
        Principal.mcsReg.enviar(mensaje);
        if(Principal.ventanaReg.jCheckBoxAck.isSelected())
        {
            Principal.mcsReg.enviar("2");
        }
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
    
    timer = new Timer (10000, new ActionListener () 
    { 
    @Override
    public void actionPerformed(ActionEvent e) 
    {   
        
        Principal.mssReg.unirseGrupo(Principal.mssReg.ip);
        Principal.weaken(listaMiembrosActivosVivos);
        checklife(listaMiembrosActivos);
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
   

    static void actualizar(String sMensaje) 
    {   
       Principal.ventanaReg.jEditorPaneOutputText.setText(Principal.ventanaReg.jEditorPaneOutputText.getText()+sMensaje);
    }

    static void añadirMiembro(String sMensaje) 
    {
       Principal.ventanaReg.jEditorPaneMiembros.setText("");
       
       listaMiembrosActivos.put(sMensaje,sMensaje);
       listaMiembrosActivosVivos.put(sMensaje, 5);
        for (Iterator it = listaMiembrosActivos.keySet().iterator(); it.hasNext();) 
        {
            Object key = it.next();
            Principal.ventanaReg.jEditorPaneMiembros.setText(Principal.ventanaReg.jEditorPaneMiembros.getText()
                                                        +"-> "+(String)key);
              
            
        }
    }

    public static void release() throws IOException 
    {   
        timer.stop();
        Principal.mssReg.servidorEscuchando=false;
        Principal.ventanaReg.jEditorPaneMiembros.setText("");
        
        Principal.mssReg.salirGrupo();
    }
     
    public static String recuperarArray(byte[] datos)
    {
        String cadena=new String(datos);
        String cadenaCortada[]=cadena.split("\\n");
        System.out.println(cadenaCortada[0]);
        //Recuerdese que se corta en \n
        return cadenaCortada[0]+"\n";
    }

    private static void checklife(HashMap listaMiembrosActivos) 
    {
        for(Iterator it=listaMiembrosActivosVivos.keySet().iterator();it.hasNext();)
        {
            Object Key=it.next();
            if((int)listaMiembrosActivosVivos.get(Key)==0)
            {
                listaMiembrosActivos.remove(Key);
            }
        }
        
    }

    private static void weaken(HashMap listaMiembrosActivosVivos) 
    {
       for(Iterator it=listaMiembrosActivosVivos.keySet().iterator();it.hasNext();)
        {
            Object Key=it.next();
            if((int)listaMiembrosActivosVivos.get(Key)!=0)
            {
                listaMiembrosActivosVivos.put(Key, ((int)(listaMiembrosActivosVivos.get(Key)))-1);
            }
            
        }
    }

    static void verificacionSolicitada(String sMensaje) {
        //Aun sin /n
        Principal.mcsReg.enviar("4("+Principal.ventanaReg.jTextFieldNick.getText()+")"+"ACK");
    }

}
