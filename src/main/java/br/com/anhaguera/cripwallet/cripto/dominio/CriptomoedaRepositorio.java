package br.com.anhaguera.cripwallet.cripto.dominio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.anhaguera.cripwallet.cripto.dominio.dtos.CriptomoedaListaDTO;

@Repository
public interface CriptomoedaRepositorio extends JpaRepository<Criptomoeda, Long> {
	
	@Query("select new br.com.anhaguera.cripwallet.cripto.dominio.dtos.CriptomoedaListaDTO(c.id, c.nome, c.quantidade, c.precoDaCompra, c.profit, c.preco, c.dataDaCompra)"
			+ "from Criptomoeda c")
	List<CriptomoedaListaDTO> findAllPessoaLista();
	
	@Query(value = "select new br.com.anhaguera.cripwallet.cripto.dominio.dtos.CriptomoedaListaDTO(c.id, c.nome, c.quantidade, c.precoDaCompra, c.profit, c.preco, c.dataDaCompra)"
			+ "from Criptomoeda c", countQuery = "select count(c) from Criptomoeda c")
	Page<CriptomoedaListaDTO> findAllPessoaListaPaginado(Pageable pageable);
	
	@Query("select c from Criptomoeda c where c.id = :indice")
	Optional<Criptomoeda> findCompletoById(@Param("indice") Long id);
}