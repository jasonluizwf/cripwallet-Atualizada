package br.com.anhaguera.cripwallet.cripto.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.anhaguera.cripwallet.cripto.dominio.dtos.PointDTO;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.anhaguera.cripwallet.cripto.dominio.Criptomoeda;
import br.com.anhaguera.cripwallet.cripto.dominio.CriptomoedaRepositorio;
import br.com.anhanguera.cripwallet.service.CoinMarketCapAPI;

@Service
public class CriptomoedaService {
	
	@Autowired
	private CriptomoedaRepositorio criptomoedaRep;
	
	@Autowired
	private CoinMarketCapAPI coinMarketCapAPI;
	
	public Criptomoeda salvarCriptomoeda(Criptomoeda cripto) {	
		Double profit, preco;
		
		List<Map<String, Object>> prices = coinMarketCapAPI.getPrice(List.of(cripto.getNome().toLowerCase()));
		
		preco = (Double) prices.get(0).get("price");
		profit = (preco - cripto.getPrecoDaCompra()) * 100 / cripto.getPrecoDaCompra();
		
		BigDecimal profitFormated = new BigDecimal(profit).setScale(2, RoundingMode.HALF_UP);
		BigDecimal precoDaCompraFormated = new BigDecimal(cripto.getPrecoDaCompra()).setScale(2, RoundingMode.HALF_UP);
		BigDecimal precoFormated = new BigDecimal(preco).setScale(2, RoundingMode.HALF_UP);

		cripto.setPreco(precoFormated.doubleValue());
		cripto.setProfit(profitFormated.doubleValue());
		cripto.setPrecoDaCompra(precoDaCompraFormated.doubleValue());
		
		return criptomoedaRep.save(cripto);
	}

	public List<PointDTO> dash() {
		List<Criptomoeda> lista = criptomoedaRep.findAll();

		Map<LocalDate, PointDTO> agg = new HashMap<>();

		for (Criptomoeda criptomoeda: lista) {
			LocalDate data = criptomoeda.getDataDaCompra().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			PointDTO point = agg.get(data);
			if(point == null) {
				point = new PointDTO();
				point.setData(data);
				agg.put(data, point);
			}
			Double quantidade = Double.valueOf(criptomoeda.getQuantidade());
			point.setValor(quantidade + point.getQuantidade());
		}
	}
}
