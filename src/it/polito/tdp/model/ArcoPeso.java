package it.polito.tdp.model;

public class ArcoPeso {
	
	private String v1;
	private String v2;
	private Integer peso;
	public ArcoPeso(String v1, String v2, Integer peso) {
		super();
		this.v1 = v1;
		this.v2 = v2;
		this.peso = peso;
	}
	public String getV1() {
		return v1;
	}
	public String getV2() {
		return v2;
	}
	public Integer getPeso() {
		return peso;
	}
	@Override
	public String toString() {
		return String.format("v1=%s, v2=%s, peso=%s", v1, v2, peso);
	}
	
	

}
