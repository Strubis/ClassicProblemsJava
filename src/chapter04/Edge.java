package chapter04;

/**
 * Problemas com Grafos
 * 
 * Edge sera a ligacao entre um ponto e outro no nosso grafo.
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class Edge {

	public final int u; // vertice origem
	public final int v; // vertice destino
	
	Edge(int u, int v){
		this.u = u;
		this.v = v;
	}
	
	public Edge reversed() {
		return new Edge(v, u);
	}
	
	@Override
	public String toString() {
		return u + " -> " + v;
	}
}
