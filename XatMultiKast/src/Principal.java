
import java.io.IOException;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sergioioooo
 */
public class Principal {
    
    public static Gui ventanaReg;
    public static MulticastServerSocket mssReg;
    public static MulticastClientSocket mcsReg;
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
    public static void iniciarServerSocket() throws IOException
    {   
        //Aqui se une
        MulticastServerSocket mss=new MulticastServerSocket(ventanaReg.jTextFieldIp.getText());
        Principal.mssReg=mss;
        mss.start();
    }
    public static void iniciarClientSocket() throws IOException
    {   
        MulticastClientSocket mcs=new MulticastClientSocket(ventanaReg.jTextFieldIp.getText()); 
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
    
}
