package grafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class BFS {
		
	private static ArrayList<Integer> L;
	private static boolean[] marcados;
	private static Tupla <Integer,Integer> tupla;
	private static ArrayList <Tupla<Integer,Integer>> ordenComunicaciones;
	
	
	/**
	 * Busca desde el vertice tomado como origen a cuantos vertices alcanza
	 * @param Grafo en donde se busca
	 * @param Vertice desde donde se verifica
	 * @return
	 */
	
	public static Set<Integer> alcanzables(Grafo g, int origen) {
		ordenComunicaciones = new ArrayList <Tupla<Integer,Integer>>();
		Set<Integer> ret = new HashSet<Integer>();		
		inicializar(g, origen);
		
		while(L.size()> 0) {
			int i = L.get(0);
			marcados[i] = true;
			ret.add(i);

			agregarVecinosPendientes(g, i);

			L.remove(0);
		}
	
		return ret;
	}

	//Metodo privado que utiliza alcanzables para agregar a los vecinos que no hayan sido agregados
	public static void agregarVecinosPendientes(Grafo g, int i) {

		for(int vertice : g.vecinos(i)) {
			tupla = new Tupla<Integer, Integer>(0,0);
			if(marcados[vertice] == false && L.contains(vertice) == false) {
				L.add(vertice);
				
				tupla.setX(L.get(0));
				tupla.setY(vertice);
				ordenComunicaciones.add(tupla);

			}
		
		}
	}
	
	/**
	 * Verifica si el grafo ingresado es conexo
	 * @param Grafo a verificar
	 * @return
	 */
	public static boolean esConexo(Grafo g) {
		if(g == null) {
			throw new IllegalArgumentException("Se ingreso un grafo null");
		}
		if(g.tamanio() == 0) {
			return true;
		}
		if(alcanzables(g, 0).size() == g.tamanio()) {
			return true;
		}
		return alcanzables(g, 0).size() == g.tamanio();
	}

	

	public static ArrayList<Tupla<Integer, Integer>> getArray() {
		return ordenComunicaciones;
	}

	public static void setArray(ArrayList<Tupla<Integer, Integer>> array) {
		BFS.ordenComunicaciones = array;
	}

	public static ArrayList<Integer> getL() {
		return L;
	}


	public static void setL(ArrayList<Integer> l) {
		L = l;
	}


	//Inicializa el grafo
	private static void inicializar(Grafo g, int origen) {
		L = new ArrayList<Integer>();	
		L.add(origen);
		marcados = new boolean[g.tamanio()];		
	}
}
