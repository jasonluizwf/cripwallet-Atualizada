package br.com.anhaguera.cripwallet.cripto.dominio.dtos;

import java.util.Date;

public class CriptomoedaListaDTO {
	
	private Long id;
	private String nome;
	private Double precoDaCompra;
	private Double preco;
	private Double profit;
	private String quantidade;
	private Date dataDaCompra;

	public CriptomoedaListaDTO(Long id, String nome, String quantidade, Double precoDaCompra, Double profit, Double preco, Date dataDaCompra) {
		this.id = id;
		this.nome = nome;
		this.quantidade = quantidade;
		this.precoDaCompra = precoDaCompra;
		this.profit = profit;
		this.preco = preco;
		this.dataDaCompra = dataDaCompra;
	}
	
	public String getQuantidade(){
		return quantidade;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Double getPrecoDaCompra() {
		return precoDaCompra;
	}

	public Double getProfit() {
		return profit;
	}
	public Double getPreco() {
		return preco;
	}

	public Date getDataDaCompra() {
		return dataDaCompra;
	}

}