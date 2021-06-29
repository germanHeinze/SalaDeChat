package clienteServidor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PaqueteDatos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int sala;
	private String nombreUsuario;
	private String mensaje;
	private int cantSalas;
	private String fecha;
	private boolean crearSala;
	private String nombreSala;
	private HashMap<Integer, Sala> salas = new HashMap<Integer, Sala>();
	private boolean dataServer;

	public PaqueteDatos(int sala, String nombreUsuario, String mensaje, int cantSalas, boolean crearSala, boolean dataServer) {
		this.sala = sala;
		this.nombreUsuario = nombreUsuario;
		this.cantSalas = cantSalas;
		this.mensaje = mensaje;
		this.crearSala = crearSala;
		this.dataServer = dataServer;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		this.fecha = dtf.format(LocalDateTime.now());
	}

	public PaqueteDatos() {
	}

	public byte[] serialize(PaqueteDatos datos) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(datos);
		return out.toByteArray();
	}

	public PaqueteDatos deserialize(byte[] datos) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(datos);
		ObjectInputStream is = new ObjectInputStream(in);
		return (PaqueteDatos) is.readObject();
	}

	@Override
	public String toString() {
		return ("[" + this.fecha + "] " + this.nombreUsuario + ": " + this.mensaje);
	}
	
	// Setters
	public void setSala(int sala) {
		this.sala = sala;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public void setCantSalas(int cantSalas) {
		this.cantSalas = cantSalas;
	}
	
	public void setSalas(HashMap<Integer, Sala> salas) {
		this.salas = salas;
	}
	
	public void setDataServer() {
		this.dataServer = true;
	}

	// Getters
	public int getSala() {
		return sala;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public int getCantSalas() {
		return cantSalas;
	}
	
	public String getFecha() {
		return fecha;
	}

	public HashMap<Integer, Sala> getSalas() {
		return this.salas;
	}

	public boolean isDataServer() {
		return dataServer;
	}
	
	

}
