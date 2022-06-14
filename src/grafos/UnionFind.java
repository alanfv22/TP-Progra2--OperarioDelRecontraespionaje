package grafos;

public class UnionFind {
	
    private int [] A;
	
	
	/**
	 * Constructor de la estructura de datos Union-Find
	 * @param N
	 */
	public UnionFind(int N) {
		A = new int [N];
		for(int i = 0; i < N; i++) {
			A[i] = i;
		}
	}
	
	
	/**
	 * Raiz del vertice
	 * @param Vertice
	 * @return
	 */
	public int root (int i) {
		while(A[i] != i)
			i = A[i];
		return i;
	}
	
	
	/**
	 * Verifica si 2 vertices estan en la misma componente conexa
	 * @param Vertice inicial
	 * @param Vertice destino
	 * @return
	 */
	public boolean find(int i, int j) {
		return root(i) == root(j);
	}
	
	
	/**
	 * Union de 2 componentes conexas
	 * @param Componente conexa A
	 * @param Componente conexa B
	 */
	public void union(int i, int j) {
		int ri = root(i);
		int rj = root(j);
		A[ri] = rj;
		
	}
	
	
	/**
	 * Verifica si es conexo
	 * @return
	 */
	public boolean esConexo() {
		boolean flag = true;
		for(int indice : this.A) {
			flag = flag && this.root(indice) == this.root(A[0]);
		}
		return flag;
	}
}
