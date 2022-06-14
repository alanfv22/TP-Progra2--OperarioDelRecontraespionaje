package grafosTest;

import static org.junit.Assert.*;

import org.junit.Test;

import grafos.BFS;
import grafos.Grafo;
import grafos.Kruskal;
import grafos.Prim;


public class KruskalUFTest {

	@Test
	public void tamanioAGMTest() {
		Grafo grafo = inicializarGrafoEjemplo();
		Grafo arbol = Kruskal.kruskalUF(grafo);
		assertEquals (arbol.getAristas().size(), grafo.tamanio() - 1);	
	}
				
	
	@Test
	public void grafoCompletoTamanioAGMTest() {
		Grafo grafo = inicializarGrafoEjemploCompleto();	
		Grafo arbol = Kruskal.kruskalUF(grafo);
		assertEquals (arbol.getAristas().size(), grafo.tamanio() - 1);
	}
		
	
	@Test
	public void grafoVacioAGM() {
		Grafo grafo = new Grafo (0);
		Kruskal.kruskalUF(grafo);
	}
		
	
	@Test 
	public void grafo2VerticesAGM() {
		Grafo grafo = new Grafo (2);
		grafo.agregarArista(0, 1, 2);
				
		Grafo arbol = Kruskal.kruskalUF(grafo);
		assertEquals (arbol.getAristas().size(), grafo.tamanio()-1);
	}
	
	
	@Test  
	public void cantVertices() {
		Grafo grafo =inicializarGrafoEjemplo();
		Grafo arbol = Kruskal.kruskalUF(grafo);
		assertEquals (arbol.getCantVertices(),grafo.getCantVertices());
	}
	
	//Inicializa el ejemplo 
	private Grafo inicializarGrafoEjemplo() {
			
		Grafo grafo = new Grafo(9);
		grafo.agregarArista(0, 1, 1);
		grafo.agregarArista(1, 2, 0);
		grafo.agregarArista(2, 3, 1);
		grafo.agregarArista(3, 4, 0);
		grafo.agregarArista(4, 5, 1);
		grafo.agregarArista(5, 6, 1);
		grafo.agregarArista(6, 7, 0);
		grafo.agregarArista(7, 8, 0);
		grafo.agregarArista(0, 7, 1);
		grafo.agregarArista(1, 7, 1);
		grafo.agregarArista(2, 5, 1);
		grafo.agregarArista(3, 5, 0);
		grafo.agregarArista(2, 8, 1);
		grafo.agregarArista(6, 8, 1);

		return grafo;
	}
		
		
	//Inicializa el ejemplo completo
	private Grafo inicializarGrafoEjemploCompleto() {
				
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(0, 1, 0);
		grafo.agregarArista(0, 2, 0);
		grafo.agregarArista(0, 3, 1); 
		grafo.agregarArista(0, 4, 0);
		grafo.agregarArista(1, 2, 0);
		grafo.agregarArista(1, 3, 1);
		grafo.agregarArista(1, 4, 1);
		grafo.agregarArista(2, 3, 0);
		grafo.agregarArista(2, 4, 0);
		grafo.agregarArista(3, 4, 1);
		
		return grafo;
	}

}


