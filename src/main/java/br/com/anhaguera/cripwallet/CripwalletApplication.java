package br.com.anhaguera.cripwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"br.com.anhaguera", "br.com.anhanguera"})
public class CripwalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(CripwalletApplication.class, args);
	}
}
