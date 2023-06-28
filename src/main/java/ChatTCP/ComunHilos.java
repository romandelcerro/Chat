package ChatTCP;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ComunHilos {	
	 int CONEXIONES;
	 int ACTUALES;
	 int MAXIMO;	
	 Socket tabla[] = new Socket[MAXIMO];
	 String mensajes;
         
         //Me he creado una lista de usuarios
	 private static ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
         
	public ComunHilos(int maximo, int actuales, int conexiones, Socket[] tabla) {
		MAXIMO = maximo;	 
		ACTUALES = actuales; 
		CONEXIONES = conexiones;	
		this.tabla = tabla;  
		mensajes="";        
	}

	public ComunHilos() { super(); }

        public int getMAXIMO() { return MAXIMO;	}
	public void setMAXIMO(int maximo) { MAXIMO = maximo;}


	public int getCONEXIONES() { return CONEXIONES;	}
	public synchronized void  setCONEXIONES(int conexiones) {
		CONEXIONES = conexiones;
	}

	public String getMensajes() { return mensajes; }
	public synchronized void setMensajes(String mensajes) {
		this.mensajes = mensajes;
	}
	
	public int getACTUALES() { return ACTUALES; }
	public synchronized void setACTUALES(int actuales) {
		ACTUALES = actuales;
	}

	public synchronized void addTabla(Socket s, int i) {		
		tabla[i] = s;
	}
        
        public synchronized Socket[] getTabla(){
            return tabla;
        }
                
        
	public Socket getElementoTabla(int i) { return tabla[i]; }
        
        //Me creo un metodo para registrar los usuarios en la listaUsuarios. Antes de registrarlos en la lista, compruebo que no existan para mandar un mensaje
        //de que ya están registrados y cerrar el socket que se abre.
        public synchronized void registroUsuarios(String ip, int puerto, String nombre, Socket socket){
            LocalDateTime fechaIni = LocalDateTime.now();
            boolean duplicado =false;
            try {
			
                for(Usuario u :listaUsuarios){
                    if(u.getNombre().equalsIgnoreCase(nombre)){
                        duplicado = true;
                    }
                }

                if(duplicado==false){
                   Usuario usuario = new Usuario(ip,puerto,nombre,fechaIni);
                    listaUsuarios.add(usuario);

                }else{
                    JOptionPane.showMessageDialog(null, "El usuario ya está registrado");
                    
                    socket.close();
                }     
         
	    }
	    catch (IOException e) {
                System.out.println("ERROR");
                e.printStackTrace();
	    }
            
        }
        
        //Método para guardar la información del usuario en un txt en el escritorio
        public synchronized void guardarUsuario(){
            
            File file = new File("C:\\Users\\Mario\\Desktop\\usuarios.txt");
          
            try (PrintWriter writer = new PrintWriter(file)) {
              
            for (Usuario u : listaUsuarios) {
              writer.println(u.toString());
            }
            } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
           
        }
        
        //Método para devolver un usuario de la lista comparándolo por nombre (Esto lo he creado para intentar borrar a los usuarios de la lista cuando se cierra el socket)
        public synchronized Usuario dameUsuario(Socket s,String nombre){
            boolean existe = false;
            Usuario usuario = new Usuario();

                for(Usuario u :listaUsuarios){
                    if(u.getNombre().equalsIgnoreCase(nombre)){
                       
                        return u;
                        
                        
                    }else{
                        System.out.println("Error");
                    }
                    
                }

            return null;
        }
        
        //Método para borrar a un usuario de la lista (Esto lo he creado para intentar borrar a los usuarios de la lista cuando se cierra el socket)
        public synchronized void borrarUsuarios(Usuario u){

            listaUsuarios.remove(u);
        }
        	
}
