package br.com.anhanguera.cripwallet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.anhaguera.cripwallet.CriptomoedasInicialBanco;

@SpringBootTest
class CripwalletApplicationTests {
	
	@MockBean
	CriptomoedasInicialBanco populacaoInicialBanco;

	@Test
	void contextLoads() {
	}

}
