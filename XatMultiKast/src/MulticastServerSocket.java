
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
public class MulticastServerSocket extends Thread
{
    private boolean servidorEscuchando=false;
    public MulticastSocket receptor;
    
MulticastServerSocket(String ip) throws IOException
{   
    MulticastSocket receptor = new MulticastSocket(55555);
    this.receptor=receptor;
    
    
    //union a grupo
    if(!unirseGrupo(ip))
    {
        System.out.println("Fallo en la union de grupo");
    }
    
    
    servidorEscuchando=true;
}
    @Override
public void run() 
{
    while(servidorEscuchando)
    {
        try 
        {
            sleep(200);
        } 
        catch (InterruptedException ex) 
        {
            System.out.println("Fallo en sleep");
        }
        try 
        {
            //Codigo Captura Socket y repintar la pantalla
            byte[] mensaje=recepcionarMensaje();
            String sMensaje=Principal.recuperarArray(mensaje);
            if(mensaje!=null)
            {
                Principal.actualizar(sMensaje);
            }
        } 
        
        catch (IOException ex) 
        {
            System.out.println("Fallo recepcion");
        }
    }
        
}
public boolean unirseGrupo(String ip)
{
        try {
            receptor.joinGroup(InetAddress.getByName(ip));
        } catch (UnknownHostException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        return true;
}

public byte[] recepcionarMensaje() throws IOException
{
    byte [] mensaje= new byte[1024];
    DatagramPacket paquete = new DatagramPacket(mensaje,mensaje.length);
    
    receptor.receive(paquete);
   
    mensaje=paquete.getData();
    if(mensaje.length!=0)
    {
        return mensaje;
    }
    else return null;
}

    private void imprirConsola(String mensajeAImprimir) 
    {
        System.out.println(mensajeAImprimir);
    }
  
}
