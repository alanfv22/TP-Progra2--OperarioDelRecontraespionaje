package grafos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import grafos.Grafo;
import JSON.ManejoDato;

public class Grafo implements Serializable {

	private ArrayList<Set<Integer>> vecinos;
	private ArrayList<Comunicacion> comunicaciones;
	private HashMap<String, Integer> Espias;
	private int cantVertices;
	private ManejoDato datos;
	private static final long serialVersionUID = 1L;

	// Constructor de Grafo

	public Grafo(int vertices) {

		vecinos = new ArrayList<Set<Integer>>(vertices);
		Espias = new HashMap<>();
		cantVertices = vertices;
		datos = new ManejoDato();
		for (int i = 0; i < cantVertices; i++) {
			vecinos.add(new HashSet<Integer>(i));
		}
		comunicaciones = new ArrayList<Comunicacion>();
	}

	// Getters y setters

	public ArrayList<Comunicacion> getAristas() {
		return comunicaciones;
	}

	public ArrayList<Set<Integer>> getVecinos() {
		return vecinos;
	}

	public Set<Integer> vecinos(int s) {
		return vecinos.get(s);
	}

	public HashMap<String, Integer> getEspias() {
		return Espias;
	}

	public void setAristas(ArrayList<Comunicacion> aristas) {
		this.comunicaciones = aristas;
	}

	public void setEspias(HashMap<String, Integer> espias) {
		Espias = espias;
	}

	public int getCantVertices() {
		return cantVertices;
	}

	// Grado del vertice

	public int grado(int s) {
		return vecinos.get(s).size();
	}

	// Tamanio del grafo

	public int tamanio() {
		return vecinos.size();
	}

	public void cargarEspiasJSON() {
		int pos = 0;
		for (String nombre : Espias.keySet())
			datos.agregarEspia(nombre, pos++);
	}

	public void actualizarJSON() {
		datos.actualizarDatos();
	}

	public void cargarComunicacionesJSON() {
		for (Comunicacion comunicacion : comunicaciones)
			datos.agregarComunicacion(comunicacion);
	}

	// Agrega la arista al grafo
	public void agregarArista(int s, int t, float peso) {
		vecinos.get(s).add(t);
		vecinos.get(t).add(s);
		comunicaciones.add(new Comunicacion(s, t, peso));
	}

	// Verifica si existe la arista

	public boolean existeArista(int s, int t) {
		return vecinos.get(s).contains(t);
	}

	// Agrega una nueva arista a la coleccion de comunicaciones

	public void agregarComunicacion(String nombreEspia1, String nombreEspia2, float pesoRiesgo) {

		String aux = Float.toString(pesoRiesgo);

		if (verificarComunicacionMensaje(nombreEspia1, nombreEspia2, aux) == "") {

			int s = Espias.get(nombreEspia1);
			int t = Espias.get(nombreEspia2);

			agregarArista(s, t, pesoRiesgo);
		}
	}

	public boolean existeEspia(String nombre) {
		return Espias.containsKey(nombre);
	}

	public boolean verificarEspiasIguales(String nombre, String nombre2) {
		return nombre.equals(nombre2);
	}

	public int obtenerPosicionEspia(String nombre) {
		return Espias.get(nombre);
	}

	public void agregarVerticesNombre(String s, int i) {
		Espias.put(s, i);
	}

	public String verificarCargarEspiaMensaje(String nombre) {

		if (nombre == null || nombre.equals(""))
			return "Por favor ingrese un nombre";

		if (existeEspia(nombre))
			return "El nombre ya esta registrado";

		return "";

	}

	public String verificarComunicacionMensaje(String nombre, String nombre2, String probabilidad) {

		if (nombre == null || nombre2 == null)
			return "Por favor ingrese comunicacion";

		if (nombre.equals("") || nombre2.equals(""))
			return "Por favor inserte nombre de los espias";

		if (!existeEspia(nombre) || !existeEspia(nombre2))
			return "Verificar que ambos espias existan en la tabla";

		if (verificarEspiasIguales(nombre, nombre2))
			return "No puede establecer comunicacion de un mismo espia";

		if (existeArista(obtenerPosicionEspia(nombre), obtenerPosicionEspia(nombre2)))
			return "Ya hay una comunicacion existente";

		if (probabilidad == null || probabilidad.equals(""))
			return "Debe ingresar un numero";

		if (!probabilidad.matches("[+-]?\\d*(\\.\\d+)?"))
			return "Debe ingresar un numero";

		if (Float.valueOf(probabilidad) < 0 || Float.valueOf(probabilidad) > 1)
			return "Debe ingresar un numero entre 0  y 1";

		return "";

	}

	public static Set<Integer> alcanzablesBFS(Grafo agm) {
		return BFS.alcanzables(agm, 0);
	}

	public static Grafo ordenDeEncuentrosConBFS(Grafo agm) {
		alcanzablesBFS(agm);
		Grafo aux = new Grafo(agm.tamanio());
		HashMap<String, Integer> espias = (HashMap<String, Integer>) agm.getEspias().clone();
		ArrayList<Tupla<Integer, Integer>> aparicionesBFS = BFS.getArray();
		for (int i = 0; i < aparicionesBFS.size(); i++) {
			aux.agregarArista(aparicionesBFS.get(i));
			aux.setEspias(espias);
		}
		return aux;
	}

	@Override
	public String toString() {
		return "Grafo [vecinos=" + vecinos + ", Comunicaciones=" + comunicaciones + ", map=" + Espias + "]";
	}

	public static boolean verificacionEsNumero(String numero) {

		if (numero == null || numero.equals("")) {
			return false;
		}
		if (!numero.matches("[+-]?\\d*(\\.\\d+)?")) {
			return false;
		}
		int numAux = Integer.parseInt(numero);

		if (numAux < 2 || numAux > 12) {
			return false;
		}
		return true;
	}

	public void agregarArista(Tupla<Integer, Integer> tupla) {
		vecinos.get(tupla.getX()).add(tupla.getY());
		vecinos.get(tupla.getY()).add(tupla.getX());
		comunicaciones.add(new Comunicacion(tupla.getX(), tupla.getY(), 0));
	}

	// Le paso un vertice (numero de espia), lo busca en Espias y se fija que
	// aristas tiene ese vertice para devolverlas
	public ArrayList<Comunicacion> aristasAsociadas(Integer s) {
		ArrayList<Comunicacion> aristasVertices = new ArrayList<>();
		Iterator<Entry<String, Integer>> it = Espias.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Integer> espia = it.next();
			if (espia.getValue() == s) {

				for (int i = 0; i < comunicaciones.size(); i++) {
					if (comunicaciones.get(i).getEspia1() == espia.getValue() && esVerticeInicial(s)) {
						aristasVertices.add(comunicaciones.get(i));

					} else if (vecinos(s).size() > 0 && !esVerticeInicial(s)
							&& comunicaciones.get(i).getEspia2() == s) {
						aristasVertices.add(comunicaciones.get(i));
					}
				}
			}
		}
		return aristasVertices;
	}

	// Se fija si el vertice pasado es un vertice inicial(Vertice_S) en el ArrayList
	// de las aristas
	public boolean esVerticeInicial(int s) {
		for (int i = 0; i < comunicaciones.size(); i++) {
			if (comunicaciones.get(i).getEspia1() == s) {
				return true;
			}
		}
		return false;
	}

	// Le paso un vertice (numero espia) y devulve el nombre asociado a ese numero
	public String dameNombreEspia(int i) {
		Iterator<Entry<String, Integer>> it = Espias.entrySet().iterator();
		String nombreEspia = "";
		while (it.hasNext()) {
			Entry<String, Integer> nombre = it.next();
			if (nombre.getValue() == i) {
				nombreEspia = nombre.getKey();
			}
		}
		return nombreEspia;
	}

	// Devuelve un ArrayList solamente con los numeros de los espia
	public ArrayList<Integer> dameNumeroEspia() {
		ArrayList<Integer> numerosEspia = new ArrayList<>();
		Iterator<Integer> it = Espias.values().iterator();
		while (it.hasNext()) {
			Integer numero = it.next();
			numerosEspia.add(numero);
			Collections.sort(numerosEspia);
		}
		return numerosEspia;
	}

	// Ordena el Map pasado por parametro y devuelve una lista de colecciones
	private List ordenarMapa(Map<String, Integer> mapa) {
		List<Map.Entry<String, Integer>> list = new ArrayList<>(mapa.entrySet());// Coloca el conjunto de entrada del
																					// mapa en la colección de la lista
		// Ordena la lista y pasa una regla de ordenación personalizada a través de
		// Comparator
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o1.getValue() - o2.getValue(); // Reescribe las reglas de clasificación, menor que 0 significa
														// orden ascendente, mayor que 0 significa orden descendente
			}
		});

		return list;
	}

	// Ordena el mapa por "orden de llegada" y devuelve un array con los nombres
	// ordenados
	public ArrayList<String> dameNombresEspia() {
		ArrayList<String> nombres = new ArrayList<>();
		Iterator<Map.Entry<String, Integer>> it = ordenarMapa(Espias).iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> espia = it.next();
			nombres.add(espia.getKey());
		}
		return nombres;
	}

	// Se fija si el vertice pertenece al HashMap de espias
	public boolean existeVertice(int vertice) {
		boolean existe = false;
		Iterator<Integer> it = Espias.values().iterator();
		while (it.hasNext()) {
			Integer numero = it.next();
			if (numero == vertice) {
				existe = true;
			}
		}
		return existe;
	}

}
