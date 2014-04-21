
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

    //Constructor
    MulticastClientSocket(String ip) throws IOException
    {
        MulticastSocket enviador=new MulticastSocket();
        this.enviador=enviador;
        this.ip=ip;
    }
    
    public boolean enviar(String textoSalida)
    {   
        
        byte [] datos = new byte[textoSalida.length()];
        datos = (textoSalida+"\n").getBytes();
        
        
       
        try 
        {
            DatagramPacket paquete = new DatagramPacket(datos, datos.length,
            InetAddress.getByName(this.ip), 55555);
            enviador.send(paquete);
        } catch (UnknownHostException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        
        return true;
    }
    
    
}
