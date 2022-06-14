package grafos;

import java.io.Serializable;

public class Comunicacion implements Serializable{
	private int espia1;
	private int espia2;
	private float probabilidad;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor de aristas entre 2 vertice
	 * @param Vertice inicial
	 * @param Vertice destino
	 * @param Peso o distancia entre vertices
	 */
	public Comunicacion(int espia1, int espia2, float probabilidad) {
		this.espia1 = espia1;
		this.espia2 = espia2;
		this.probabilidad = probabilidad;
	}
	
	
	//Getters y setters de vertices y peso
	public int getEspia1() {
		return espia1;
	}

	public int getEspia2() {
		return espia2;
	}

	public float getProbabilidad() {
		return probabilidad;
	}

	public void setProbabilidad(float peso) {
		this.probabilidad = peso;
	}


	//Equals entre comunicaciones
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Comunicacion other = (Comunicacion) obj;
		if ((espia1 != other.espia1) && (espia1 != other.espia2)) {
			return false;
		}
		if ((espia2 != other.espia2) && (espia2 != other.espia1)) {
			return false;
		}
		return true;
	}


	@Override
	public String toString() {
		return "Comunicacion [espia1=" + espia1 + ",espia2=" + espia2 + ", probabilidad=" + probabilidad + "]";
	}		
	
}