package graficos;

import java.awt.EventQueue;

import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import clienteServidor.HiloSalas;
import clienteServidor.Sala;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;

public class Lobby {

	private List<Sala> salas = new LinkedList<Sala>();
	private JFrame frame;

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
		list.setBounds(22, 32, 248, 193);
		frame.getContentPane().add(list);

		JButton btnNewButton = new JButton("Crear Sala");
		btnNewButton.addActionListener(new ActionListener() {
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
		btnNewButton.setBounds(299, 32, 109, 21);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Unirse a Sala");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int select = list.getSelectedIndex();
				if (select != -1) {
					Sala sala = salas.get(select);
					

					JChatCliente frame = new JChatCliente();
					frame.setVisible(true);
					frame.assignarPuerto(sala);

					list.setModel(modelo);
					
				}

				else
					JOptionPane.showMessageDialog(null, "No se eligi� sala..");

			}
		});
		btnNewButton_1.setBounds(299, 63, 109, 21);
		frame.getContentPane().add(btnNewButton_1);
	}
}