package br.com.anhaguera.cripwallet.cripto.dominio;


import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Criptomoeda {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "O campo nome não pode ser vazio")
	@Column(nullable = false)
	private String nome;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date dataDaCompra;

	private Double preco;
	
	private Double precoDaCompra;
	
	private Double profit;

	private String quantidade;
	
	@Deprecated
	protected Criptomoeda() {
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Criptomoeda(String nome) {
		this.nome = nome;
	}
	
	public Date getDataDaCompra() {
		return dataDaCompra;
	}

	public void setDataDaCompra(Date dataDaCompra) {
		this.dataDaCompra = dataDaCompra;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPrecoDaCompra() {
		return precoDaCompra;
	}

	public void setPrecoDaCompra(Double precoDaCompra) {
		this.precoDaCompra = precoDaCompra;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Criptomoeda other = (Criptomoeda) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Criptomoeda [nome=" + nome + "]";
	}
	
}