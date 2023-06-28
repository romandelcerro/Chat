package ChatTCP;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ClienteChat extends JFrame implements ActionListener, Runnable {
	private static final long serialVersionUID = 1L;
	Socket socket = null;
	
	DataInputStream fentrada;
	DataOutputStream fsalida;
	String nombre;
	static JTextField mensaje = new JTextField();

	private JScrollPane scrollpane1;
	static JTextArea textarea1;
	JButton botonEnviar = new JButton("Enviar");
	JButton botonSalir = new JButton("Salir");
	boolean repetir = true;

	public ClienteChat(Socket s, String nombre) {
		super(" CONEXION DEL CLIENTE CHAT: " + nombre);
		setLayout(null);

		mensaje.setBounds(10, 10, 400, 30);
		add(mensaje);

		textarea1 = new JTextArea();
		scrollpane1 = new JScrollPane(textarea1);
		scrollpane1.setBounds(10, 50, 400, 300);
		add(scrollpane1);

		botonEnviar.setBounds(420, 10, 100, 30);
		add(botonEnviar);
		botonSalir.setBounds(420, 50, 100, 30);
		add(botonSalir);

		textarea1.setEditable(false);
		botonEnviar.addActionListener(this);
		botonSalir.addActionListener(this);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		socket = s;
		this.nombre = nombre;
                
		try {
			fentrada = new DataInputStream(socket.getInputStream());
			fsalida = new DataOutputStream(socket.getOutputStream());
                        fsalida.writeUTF(nombre);
                        String texto = " > Entra en el Chat ... " + nombre;
			fsalida.writeUTF(texto);
                        
		}
		catch (IOException e) {
			System.out.println("ERROR DE E/S");
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == botonEnviar) {

			if (mensaje.getText().trim().length() == 0)
				return;
			String texto = nombre + "> " + mensaje.getText();

			try {
				mensaje.setText("");
				fsalida.writeUTF(texto);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == botonSalir) {
                        
			String texto = " > Abandona el Chat ... " + nombre;
			try {
				fsalida.writeUTF(texto);
				fsalida.writeUTF("*");
				repetir = false;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void run() {
		String texto = "";
		while (repetir) {
			try {
				texto = fentrada.readUTF();
				textarea1.setText(texto);
			}
			catch (IOException e) {
				JOptionPane.showMessageDialog(null, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage(),
						"<<MENSAJE DE ERROR:2>>", JOptionPane.ERROR_MESSAGE);
				repetir = false;
			}
		}

		try {
			socket.close();
			System.exit(0);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		
                Socket s= null;
                String nombre="";
                //Me creo un bucle para que no se salga del programa mientras estoy capturando excepciones en los JOptionPane
                for(int i =0;i<1;i--){
                String puerto = JOptionPane.showInputDialog("Introduce un puerto:");

                if(puerto!=null){
                    
                //Capturo excepción. Si el puerto tiene más de 5 dígitos y esos dígitos no son numéricos, muestro un mensaje de error
                if(puerto.length()>5||!puerto.matches("\\d+")){
                    JOptionPane.showMessageDialog(null,"Has introducido un puerto erroneo", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    i=2;
                    //Me creo un bucle para que no se salga del programa mientras estoy capturando excepciones en los JOptionPane
                    for(int k =0;k<1;k--){
                    nombre = JOptionPane.showInputDialog("Introduce tu nick:");
                    //Capturo excepción. Si no hay dígitos, muestro mensaje de error
                    if (nombre.trim().length() == 0) {
                            JOptionPane.showMessageDialog(null,"No has introducido ningún dígito", "Error", JOptionPane.ERROR_MESSAGE);
                            
                    }else{
                        k=2;
                        try {
                            s = new Socket("localhost", Integer.parseInt(puerto));
                            ClienteChat cliente = new ClienteChat(s, nombre);
                            cliente.setBounds(0, 0, 540, 400);
                            cliente.setVisible(true);
                            new Thread(cliente).start();
                        }
                        catch (IOException e) {
                                JOptionPane.showMessageDialog(null, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage(),
                                                "<<MENSAJE DE ERROR:1>>", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    }
                }
         
                }
                
	}
        
    }
}
