package ChatTCP;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;



public class ServidorChat  {
    
	static final int MAXIMO = 5;	
	static ComunHilos ch = new ComunHilos();
        
	public static void main(String args[]) throws IOException {
            
            //Me creo un bucle para que no se salga del programa mientras estoy capturando excepciones en los JOptionPane
            for(int i=0;i<1;i--){
            String puerto = JOptionPane.showInputDialog("Introduce un puerto:");

            if(puerto!=null){
                //Capturo excepción. Si el puerto tiene más de 5 dígitos y esos dígitos no son numéricos, muestro un mensaje de error
                if(puerto.length()>5||!puerto.matches("\\d+")){
                    JOptionPane.showMessageDialog(null,puerto, "Has introducido un puerto erroneo", JOptionPane.ERROR_MESSAGE);
                }else{
                
                    ServerSocket servidor = new ServerSocket(Integer.parseInt(puerto));
                    
                    //Creo un segundo bucle para evitar que salga del programa mientras capturo excepciones
                    for(int k=0;k<1;k--){
                        
                    String maximo = JOptionPane.showInputDialog("Introduce el número máximo de conexiones:");
                    
                    if(maximo!=null){
                        
                        //Capturo excepción. Si los dígitos de las conexiones no son numéricos, muestro mensaje de error
                        if(maximo.matches("\\d+")){
                            
                            System.out.println("Servidor iniciado...");
                            Socket tabla[] = new Socket[Integer.parseInt(maximo)];
                            ComunHilos comun = new ComunHilos(Integer.parseInt(maximo), 0, 0, tabla);

                            while (comun.getCONEXIONES() < Integer.parseInt(maximo)) {

                                    Socket socket = new Socket();

                                    socket = servidor.accept();
                                    
                                    //Guardo la informacion de los usuarios en variables
                                    InetAddress ia = socket.getInetAddress();
                                    String ip = ia.toString();
                                    int puertoUsuario = socket.getPort();
                                    DataInputStream fentrada= new DataInputStream(socket.getInputStream());
                                    String nombre =fentrada.readUTF();
                                    
                                    //Me creo un objeto usuario con las variables anteriores
                                    comun.registroUsuarios(ip, puertoUsuario, nombre, socket);
                                    //Guardo la información del objeto en un txt en el escritorio
                                    comun.guardarUsuario();

                                    comun.addTabla(socket, comun.getCONEXIONES());
                                    comun.setACTUALES(comun.getACTUALES() + 1);
                                    comun.setCONEXIONES(comun.getCONEXIONES() + 1);			

                                    HiloServidorChat hilo = new HiloServidorChat(socket, comun);
                                    hilo.start(); 

                                    //He intendado borrar la lista de usuarios cuando el socket está cerrado en la tabla de sockets pero no he sabido hacerlo
                                    /*
                                    for(Socket s:comun.getTabla()){
                                            if(s!=null){
                                            if(s.isClosed()){
                                                comun.borrarUsuarios(nombre);

                                            }
                                            }
                                        }
                                    */
                            }

                            servidor.close();
                            i=2;
                            k=2;
                        }else{
                            JOptionPane.showMessageDialog(null,maximo, "Has introducido digitos incorrectos", JOptionPane.ERROR_MESSAGE);
                        }

                    }else{
                        System.exit(0);
                    }

                    }
                }
            }else{
                System.exit(0);
            }
            
            }   
	}

} 

