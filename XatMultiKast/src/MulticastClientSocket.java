
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sergi
 */
public class MulticastClientSocket 
{   
    MulticastSocket enviador;
    String ip;
    private int port;
    
   
    

    //Constructor
    MulticastClientSocket(String ip, int port) throws IOException
    {
        MulticastSocket enviador=new MulticastSocket();
        this.enviador=enviador;
        this.ip=ip;
        this.port=port;
    }
    
    public boolean enviar(String textoSalida)
    {   
        
        byte [] datos = new byte[textoSalida.length()];
        
        //Por ahora todo lo envia con \n
        datos = (textoSalida+"\n").getBytes();
        
        
       
        try 
        {
            DatagramPacket paquete = new DatagramPacket(datos, datos.length,
            InetAddress.getByName(this.ip), this.port);
            enviador.send(paquete);
        } catch (UnknownHostException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        
        return true;
    }
    
    
}
