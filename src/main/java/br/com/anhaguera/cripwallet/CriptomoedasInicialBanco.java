package br.com.anhaguera.cripwallet;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import br.com.anhaguera.cripwallet.adm.dominio.Role;
import br.com.anhaguera.cripwallet.adm.dominio.Usuario;
import br.com.anhaguera.cripwallet.adm.dominio.UsuarioRepositorio;
import br.com.anhaguera.cripwallet.cripto.dominio.Criptomoeda;
import br.com.anhaguera.cripwallet.cripto.service.CriptomoedaService;

@Component
@Transactional
public class CriptomoedasInicialBanco implements CommandLineRunner {
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CriptomoedaService criptomoedaService;
	
	@Override
	public void run(String... args) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dataDaCompra = sdf.parse("29/08/2022");

		Criptomoeda c1 = new Criptomoeda("bitcoin");
		c1.setDataDaCompra(dataDaCompra);
		c1.setPrecoDaCompra(10000.0);
		c1.setQuantidade("1");
		
		criptomoedaService.salvarCriptomoeda(c1);
		
		Usuario u1 = new Usuario("victor", passwordEncoder.encode("victor"));
		u1.setRole(Role.ADMIN.getNome());
		
		Usuario u2 = new Usuario("user", passwordEncoder.encode("user"));
		
		usuarioRepo.save(u1);
		usuarioRepo.save(u2);
	}
}