package graficos;

import java.awt.EventQueue;

import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import clienteServidor.Cliente;
import clienteServidor.HiloSalas;
import clienteServidor.Sala;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;

public class Lobby {

	private List<Sala> salas = new LinkedList<Sala>();
	private JFrame frame;
	private JTextField textField;
	JButton btnConectar;
	private List<Cliente> clientes = new LinkedList<Cliente>();
	private Cliente cliente;
	private String usuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lobby window = new Lobby();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Lobby() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		try {
			Sala sala1 = new Sala("Sala 1");
			Sala sala2 = new Sala("Sala 2");

			this.salas.add(sala1);
			this.salas.add(sala2);

			new HiloSalas(this.salas.get(0)).start();
			new HiloSalas(this.salas.get(1)).start();

		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		JList list = new JList();
		DefaultListModel modelo = new DefaultListModel();
		modelo.addElement(this.salas.get(0));
		modelo.addElement(this.salas.get(1));
		list.setModel(modelo);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(22, 83, 248, 142);
		frame.getContentPane().add(list);

		JButton btnCrearSala = new JButton("Crear Sala");
		btnCrearSala.setEnabled(false);
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreSala = JOptionPane.showInputDialog("Ingrese nombre de la sala: ");
				JOptionPane.showMessageDialog(null, "Sala " + nombreSala + " creada exitosamente");

				try {
					Sala sala = new Sala(nombreSala);
					salas.add(sala);
					modelo.addElement(sala);
					list.setModel(modelo);

					new HiloSalas(sala).start();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCrearSala.setBounds(298, 82, 109, 21);
		frame.getContentPane().add(btnCrearSala);

		JButton btnUnirseASala = new JButton("Unirse a Sala");
		btnUnirseASala.setEnabled(false);
		btnUnirseASala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int select = list.getSelectedIndex();
				if (select != -1) {
					Sala sala = salas.get(select);
					sala.addUsuario(usuario);

					JChatCliente frame = new JChatCliente();
					frame.setTitle(sala.getNombre() + " | " + usuario);
					frame.setVisible(true);
					frame.assignarSala(sala);
					
					try {
						frame.asignarCliente(cliente, usuario);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					list.setModel(modelo);
					
					textField.setEnabled(true);
					btnUnirseASala.setEnabled(false);
					btnCrearSala.setEnabled(false);
					btnConectar.setEnabled(true);

				}

				else
					JOptionPane.showMessageDialog(null, "No se eligió sala..");

			}
		});
		btnUnirseASala.setBounds(298, 113, 109, 21);
		frame.getContentPane().add(btnUnirseASala);

		textField = new JTextField();
		textField.setBounds(22, 32, 129, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		btnConectar = new JButton("Conectar");
		btnConectar.setBounds(161, 32, 109, 21);
		frame.getContentPane().add(btnConectar);
		
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				usuario = textField.getText();

				textField.setEnabled(false);
				btnUnirseASala.setEnabled(true);
				btnCrearSala.setEnabled(true);
				btnConectar.setEnabled(false);
			}
		});

		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setBounds(22, 9, 72, 13);
		frame.getContentPane().add(lblNewLabel);
	}
}
