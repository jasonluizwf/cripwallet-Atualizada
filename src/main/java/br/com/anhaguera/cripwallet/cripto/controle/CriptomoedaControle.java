package br.com.anhaguera.cripwallet.cripto.controle;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.anhaguera.cripwallet.cripto.dominio.Criptomoeda;
import br.com.anhaguera.cripwallet.cripto.dominio.CriptomoedaRepositorio;
import br.com.anhaguera.cripwallet.cripto.dominio.dtos.CriptomoedaListaDTO;
import br.com.anhaguera.cripwallet.cripto.service.CriptomoedaService;

@Controller
public class CriptomoedaControle {
	
	@Autowired
	private CriptomoedaRepositorio criptomoedaRep;
	
	@Autowired
	private CriptomoedaService criptomoedaService;
	
	public CriptomoedaControle(CriptomoedaRepositorio criptomoedaRep) {
		this.criptomoedaRep = criptomoedaRep;
	}
	
	@GetMapping("/carteira/criptomoedas/")
	public String cripto(Model model, @RequestParam("page") Optional<Integer> pagina, @RequestParam("size") Optional<Integer> tamanho) {
		int paginaAtual = pagina.orElse(1) - 1;
		int tamanhoPagina = tamanho.orElse(5);
		
		PageRequest requisicao = PageRequest.of(paginaAtual, tamanhoPagina, Sort.by("nome"));
		Page<CriptomoedaListaDTO> listaPaginada = criptomoedaRep.findAllPessoaListaPaginado(requisicao);
		
		model.addAttribute("listaCriptomoedas", listaPaginada);
		int totalPaginas = listaPaginada.getTotalPages();
		if (totalPaginas > 0) {
			List<Integer> numerosPaginas = IntStream.rangeClosed(1, totalPaginas)
						.boxed()
						.collect(Collectors.toList());
			model.addAttribute("numerosPaginas", numerosPaginas);
		}
		return "carteira/criptomoedas/index";
	}
	
	@GetMapping("/carteira/criptomoedas/nova")
	public String novaCriptomoeda(Model model) {
		model.addAttribute("cripto", new Criptomoeda(""));
		return "carteira/criptomoedas/form";
	}
	
	@GetMapping("/carteira/criptomoedas/{id}")
	public String alterarCriptomoeda(@PathVariable("id") long id, Model model) {
		Optional<Criptomoeda> criptoOpt = criptomoedaRep.findCompletoById(id);
		if (!criptoOpt.isPresent()) {
			throw new IllegalArgumentException("Criptomoeda inválida.");
		}
		
		model.addAttribute("cripto", criptoOpt.get());
		
		return "carteira/criptomoedas/form";
	}
	
	@PostMapping("/carteira/criptomoedas/salvar")
	public String salvarCriptomoeda(@Valid @ModelAttribute("cripto") Criptomoeda cripto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "carteira/criptomoedas/form";
		}
		
		criptomoedaService.salvarCriptomoeda(cripto);
		return "redirect:/carteira/criptomoedas/";
	}
	
	@GetMapping("/carteira/criptomoedas/excluir/{id}")
	public String excluirCriptomoeda(@PathVariable("id") long id) {
		Optional<Criptomoeda> criptoOpt = criptomoedaRep.findById(id);
		if (criptoOpt.isEmpty()) {
			throw new IllegalArgumentException("Cripto inválida.");
		}
		
		criptomoedaRep.delete(criptoOpt.get());
		return "redirect:/carteira/criptomoedas/";
	}
}