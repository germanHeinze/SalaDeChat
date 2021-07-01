package graficos;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;

import clienteServidor.Cliente;
import clienteServidor.PaqueteDatos;
import clienteServidor.Sala;

import javax.swing.JTextArea;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import javax.swing.JList;
import javax.swing.JLabel;

public class JChatCliente extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4346394367563281130L;
	private JPanel contentPane;
	private String nombreUsuario;
	private JTextArea textArea_1;
	Cliente cliente;
	JChatCliente ventana = this;
	private JScrollPane scroll;
	private JTextField textField;
	private Sala sala;
	private JList list;
	private int puerto = 50000;
	private JButton btnNewButtonDescargar;


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
//		setTitle(sala.getNombre());
		setResizable(false);
		setBounds(100, 100, 742, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		textArea_1 = new JTextArea();
		textArea_1.setBounds(197, 54, 488, 297);
		contentPane.add(textArea_1);
		textArea_1.setEditable(false);
		
		list = new JList();
		list.setBounds(37, 54, 131, 297);
		contentPane.add(list);
		
		JLabel lblNewLabel = new JLabel("Usuarios:");
		lblNewLabel.setBounds(37, 31, 75, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Chat:");
		lblNewLabel_1.setBounds(197, 31, 45, 13);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(203, 377, 280, 31);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.setBounds(487, 377, 98, 32);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Adquiere el mensaje y lo manda al servidor
				String msj = textField.getText();
				try {
					cliente.enviarMensaje(msj, sala.getIdSala(), false, false);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				// borra y adquire foco
				textField.setText(null);
				textField.requestFocus();
				btnNewButtonDescargar.setEnabled(true);
				
//				ventana.actualizarList();
				
			}
		});
		
		btnNewButtonDescargar = new JButton("Descargar");
		btnNewButtonDescargar.setBounds(587, 377, 98, 32);
		contentPane.add(btnNewButtonDescargar);
		btnNewButtonDescargar.setEnabled(false);
		btnNewButtonDescargar.setToolTipText("Exportar el Chat");

		btnNewButtonDescargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jFileChooser = new JFileChooser();
				String userHome = System.getProperty("user.home");
				userHome = userHome + "/Downloads";

				jFileChooser.setCurrentDirectory(new java.io.File(userHome));

				jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				jFileChooser.showOpenDialog(btnNewButtonDescargar);

				if (jFileChooser.getSelectedFile() != null) {
					FileWriter guardado;

					{
						FileWriter fichero = null;
						PrintWriter pw = null;
						try {
							fichero = new FileWriter(jFileChooser.getSelectedFile().toString() + "/chat.txt");
							pw = new PrintWriter(fichero);

							pw.println(textArea_1.getText());

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								if (null != fichero)
									fichero.close();
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					}

				}

			}
		});

		
//		scroll = new JScrollPane(textArea_1);
//		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	}

	public void escribirMensaje(PaqueteDatos datos) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String fecha = dtf.format(LocalDateTime.now());
		String mensaje = ("[" + fecha + "] " + datos.getNombreUsuario() + ": " + datos.getMensaje());
		textArea_1.append(mensaje + "\n");
	}
	
	public void assignarSala(Sala sala) {
		this.sala = sala;
		
//		actualizarList();
	}

//	public void actualizarList() {
//		// Asigna lista de usuarios a la list
//		DefaultListModel modelo = new DefaultListModel();
//		for (String usuario : this.sala.getUsuarios()) {
//			modelo.addElement(usuario);	
//		}
//		
//		list.setModel(modelo);
//	}

	public void asignarCliente(Cliente cliente, String nombreUsuario) throws UnknownHostException, IOException {
		this.cliente = cliente;
		this.nombreUsuario = nombreUsuario;
		this.setTitle(nombreUsuario);
	}
	
}


