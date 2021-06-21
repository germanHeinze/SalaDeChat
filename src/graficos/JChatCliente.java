package graficos;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;

import clienteServidor.Cliente;
import clienteServidor.Sala;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

public class JChatCliente extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4346394367563281130L;
	private JPanel contentPane;
	private JTextField textField_usuario;
	private JTextField textField_mensaje;
	private String usuario;
	private int puertoSala;
	private JTextArea textArea_chat = new JTextArea();
	Cliente cliente;
	JChatCliente ventana = this;
	private JScrollPane scroll;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		
//	}

	/**
	 * Create the frame.
	 */
	public JChatCliente() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt){
                if (JOptionPane.showConfirmDialog(rootPane, "¿Desea salir de la sala?", 
                        "Chat", JOptionPane.ERROR_MESSAGE) == JOptionPane.ERROR_MESSAGE){
                	try {
						cliente.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    System.exit(0);
                }
            }
        });
		setTitle("Chat");
		setResizable(false);
		setBounds(100, 100, 488, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnNewButton_enviar = new JButton("Enviar");
		btnNewButton_enviar.setEnabled(false);
		btnNewButton_enviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Adquiere el mensaje y lo manda al servidor formateado
				String msj = textField_mensaje.getText();
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				String fecha = dtf.format(LocalDateTime.now());
				cliente.enviarMensaje("[" + fecha + "] " + usuario + ": " + msj);
				
				// borra y adquire foco
				textField_mensaje.setText(null);
				textField_mensaje.requestFocus();
			}
		});

		textField_usuario = new JTextField();
		textField_usuario.setColumns(10);

		JButton btnNewButton_conectar = new JButton("Conectar");
		btnNewButton_conectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				usuario = textField_usuario.getText();
				try {
					cliente = new Cliente(puertoSala, "localhost");
					cliente.inicializarHiloCliente(ventana);
					textField_usuario.setEnabled(false);
					btnNewButton_conectar.setEnabled(false);

					textField_mensaje.setEnabled(true);
					btnNewButton_enviar.setEnabled(true);

				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		textField_mensaje = new JTextField();
		textField_mensaje.setColumns(10);
		textField_mensaje.setEnabled(false);
		
		scroll = new JScrollPane(textArea_chat);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textField_usuario, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNewButton_conectar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(textField_mensaje, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnNewButton_enviar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(textArea_chat, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scroll, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(11, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_conectar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(textField_usuario, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(scroll, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(textArea_chat, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
					.addGap(39)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnNewButton_enviar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(textField_mensaje, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(15, Short.MAX_VALUE))
		);
		textArea_chat.setEditable(false);
		contentPane.setLayout(gl_contentPane);
	}

	public void escribirMensaje(String mensaje) {
		textArea_chat.append(mensaje + "\n");
	}
	
	public void assignarPuerto(Sala sala) {
		this.puertoSala = sala.getPuerto();
	}
}
