package grafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class Kruskal {
	
	private static boolean grafoEsConexo;
	private static Grafo agm; 
	
	/**
	 * Crea un arbol generador minimo usando el algoritmo de Kruskal con estructura de datos Union-Find
	 * @param Grafo a probar 
	 * @return
	 */
	public static Grafo kruskalUF (Grafo g) {
	    agm = new Grafo(g.tamanio());
		HashMap<String, Integer> espias = (HashMap<String, Integer>) g.getEspias().clone();
		agm.setEspias(espias);
		grafoEsConexo = true;
		UnionFind uf = new UnionFind(g.tamanio());
		ArrayList<Comunicacion> listaAuxArista = (ArrayList<Comunicacion>) g.getAristas().clone(); 
		
		while (listaAuxArista.size()!=0 && agm.getAristas().size() < g.tamanio() - 1) { 
			Comunicacion arista = elegirArista(listaAuxArista, uf);
			agm.agregarArista(arista.getEspia1(), arista.getEspia2(), arista.getProbabilidad());
			uf.union(arista.getEspia1(), arista.getEspia2());
		}
		if(!uf.esConexo()) {
			grafoEsConexo = false;}

		return agm;
	}

	public static long tiempoEjecucion(Grafo f){
		long tiempoInicio, tiempoFin, tiempo;
		
		tiempoInicio = System.currentTimeMillis();
		for(int i = 0; i < 100; i++) {
			kruskalUF(f);
		}
		tiempoFin = System.currentTimeMillis();
		tiempo = tiempoFin - tiempoInicio;
		return tiempo;
		
	}

	//Metodo que utiliza elegirArista para seleccionar la arista de menor peso
	public static Comunicacion seleccionarMenorUF(ArrayList<Comunicacion> aristaList) {
		Comunicacion auxi = aristaList.get(0);
		for (Comunicacion arista : aristaList) {
			if(arista.getProbabilidad() < auxi.getProbabilidad()){
				auxi = arista;
			}
		}
		return auxi;
	}
	
	//Metodo que utiliza KruskalUF para elegir la arista y comenzar con el algoritmo
	public static Comunicacion elegirArista(ArrayList<Comunicacion> aristaList, UnionFind uf) {
		boolean flag = false;
		Comunicacion auxi = aristaList.get(0);
		while (flag == false) {
			auxi = seleccionarMenorUF(aristaList);
			if (uf.find(auxi.getEspia1(), auxi.getEspia2())) {
				aristaList.remove(auxi);
			} 
			else {
				flag = true;
				aristaList.remove(auxi);
			}		
		}
		return auxi;
	}

	public static boolean getEsConexo() {
		return grafoEsConexo;
	}
	

	public static Grafo getAgm() {
		return agm;
	}

	@Override
	public String toString() {
		return "Kruskal [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	
	

}