package grafosTest;

import static org.junit.Assert.*;

import org.junit.Test;

import grafos.Grafo;
import grafos.Comunicacion;

public class AristaTest {

	@Test
	public void aristaExistenteTest() {
		Grafo grafo = new Grafo(4);
		grafo.agregarArista(2, 3, 0);
		assertTrue(grafo.existeArista(2, 3));
	}
	
	@Test
	public void aristaInexistenteTest() {
		Grafo grafo = new Grafo(4);
		grafo.agregarArista(2, 3, 0);
		assertFalse(grafo.existeArista(1, 3));
	}
	
	@Test
	public void aristaOpuestaTest() {
		Grafo grafo = new Grafo(4);
		grafo.agregarArista(2, 3, 0);
		assertTrue(grafo.existeArista(3, 2));
	}
	
	
	@Test
	public void verificarTamanioTest() {
		Grafo grafo = new Grafo (4);
		assertEquals(4, grafo.tamanio());
	}
	
	
	@Test
	public void testEquals() {
		Comunicacion arista21 = new Comunicacion(2, 1, 0);
		Comunicacion arista12 = new Comunicacion(1, 2, 0);
		assertEquals(arista21, arista12);
	}
	
	@Test
	public void nombreVerticesIgualesTest() {
		Grafo grafo = new Grafo(3);
		grafo.agregarVerticesNombre("Pedro", 0);
		grafo.agregarVerticesNombre("Roberto", 1);
		grafo.agregarVerticesNombre("Rita", 2);
		
		grafo.agregarComunicacion("Pedro", "Pedro", 0);
		grafo.agregarComunicacion("Pedro", "Roberto", 0);
		grafo.agregarComunicacion("Pedro", "Rita", 0);

		assertEquals(2, grafo.getAristas().size());	
	}
	
	
	@Test
	public void verticeExcedidoTest() throws Exception { 
		verticesGrafo().agregarVerticesNombre("Rolo", 1);
	}
	
	private Grafo verticesGrafo() {
		Grafo grafo = new Grafo(3);
		grafo.agregarVerticesNombre("Pedro", 0);
		grafo.agregarVerticesNombre("Roberto", 1);
		grafo.agregarVerticesNombre("Rita", 2);
		
		return grafo;
	}
	
	@Test
	public void aristaExcedidoTest() throws Exception { 
		
		verticesGrafo().agregarComunicacion("Pedro", "Ana", 0);
		verticesGrafo().agregarComunicacion("Ana", "Pedro", 0);
	}
}

