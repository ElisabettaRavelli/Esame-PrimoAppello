package it.polito.tdp.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private List<String> vertici;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<ArcoPeso> archiEpesi;
	List<ArcoPeso> result = new LinkedList<>();
	
	public Model() {
		this.dao = new EventsDao();
		this.vertici = new LinkedList<>();
		this.archiEpesi = new LinkedList<>();
	}
	
	public List<String> listCategorie(){
		List<String> categorie = this.dao.listCategorie();
		Collections.sort(categorie);
		return categorie;
	}
	
	public List<Integer> listMesi(){
		List<Integer> mesi = this.dao.listMesi();
		Collections.sort(mesi);
		return mesi;
	}
	
	public List<ArcoPeso> creaGrafo(String categoria, Integer mese) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.vertici = this.dao.listVertici(categoria, mese);
		Graphs.addAllVertices(this.grafo, this.vertici);
		

		System.out.println("num vertici: " + this.grafo.vertexSet().size());
		int somma = 0;
		for(String v1: this.grafo.vertexSet()) {
			for(String v2: this.grafo.vertexSet()) {
				if(!v1.equals(v2) && this.grafo.getEdge(v1, v2) ==null) {
					int peso = this.dao.getPeso(categoria, mese, v1, v2);
					if(peso>0) {
						System.out.println("Aggiunto arco con peso: " + peso);
						Graphs.addEdge(this.grafo, v1, v2, (double) peso);
						this.archiEpesi.add(new ArcoPeso(v1, v2, peso));
						somma = somma + peso;
					}
				}
			}
		}
		System.out.println("finito doppio ciclo for");
		if(this.grafo.edgeSet().size()>0) {
		double pesoMedio = somma/(this.grafo.edgeSet().size());
		System.out.println("Peso medio: "+ pesoMedio);
		for(ArcoPeso tmp: archiEpesi) {
			if(tmp.getPeso() > pesoMedio)
			result.add(tmp);
		}
		}
		 
		return result;
		
	}

	public Integer getVertici() {
		return this.grafo.vertexSet().size();
	}

	public Integer getArchi() {
		return this.grafo.edgeSet().size();
	}
	
}
