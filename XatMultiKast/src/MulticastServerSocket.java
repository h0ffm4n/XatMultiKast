
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

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
    public boolean servidorEscuchando=false;
    public MulticastSocket receptor;
    public final String ip;
    public final int tipoUnion=1;
    public final int tipoVerificacion=2;
    public final int tipoNormal=3;
    public final int tipoVerificado=4;
    
MulticastServerSocket(String ip,int port) throws IOException
{   
    MulticastSocket receptor = new MulticastSocket(port);
    this.receptor=receptor;
    this.ip=ip;
    
    
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
            sleep(500);
            
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
            if(sMensaje!=null)
            {   
                //Verificacion tipo de mensaje
                int i=tipoMensaje(sMensaje);
                sMensaje=cortarCabecera(sMensaje);
                    switch(i)
                    {
                        
                        case 49://tipo union
                            Principal.añadirMiembro(sMensaje);
                            break;
                            
                        case 50://tipo Verificacion
                            Principal.verificacionSolicitada(sMensaje);  
                            break;
                            
                        case 51 ://Mensaje normal
                            Principal.actualizar(sMensaje);
                            break;
                        case 52:
                            if(Principal.ventanaReg.jCheckBoxAck.isSelected())
                            {
                            Principal.actualizar(sMensaje);
                            }
                            break;
                    
                    }
                
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
    System.out.println("uniendose");
        try {
            receptor.joinGroup(InetAddress.getByName(ip));
        } catch (UnknownHostException ex) {
            return false;
        } catch (IOException ex) {
            int hello=tipoUnion;
            Principal.enviar(hello+Principal.ventanaReg.jTextFieldNick.getText());
        }
        int hello=tipoUnion;
        Principal.enviar(hello+Principal.ventanaReg.jTextFieldNick.getText());
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

    private int tipoMensaje(String sMensaje) {
       byte[] data=sMensaje.getBytes();
       //Check header
       int header=0;
       header=(int)data[0];
      
       //devolvemos la cabecera
       return header;
      
       
    }

    private String cortarCabecera(String sMensaje) 
    {
        sMensaje=sMensaje.substring(1);
        return sMensaje;
    }

    public void salirGrupo() throws IOException 
    {
        receptor.leaveGroup(InetAddress.getByName(ip));
        receptor.close();
    }

   
}
