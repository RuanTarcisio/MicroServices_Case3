package br.com.rtarcisio.bookservice.controller;

import br.com.rtarcisio.bookservice.domain.Book;
import br.com.rtarcisio.bookservice.proxy.CambioProxy;
import br.com.rtarcisio.bookservice.repository.BookRepository;
import br.com.rtarcisio.bookservice.response.Cambio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("book-service")
public class BookController {

	@Autowired
	private Environment environment;

	@Autowired
	private BookRepository repository;

	@Autowired
	private CambioProxy proxy;

	@GetMapping(value = "/{id}/{currency}")
	public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {

		var book = repository.findById(id).get();
		if (book == null)
			throw new RuntimeException("Book not Found");

		var cambio = proxy.getCambio(book.getPrice(), "USD", currency);

		var port = environment.getProperty("local.server.port");
		book.setEnvironment(port + " FEIGN");
		book.setPrice(cambio.getConvertedValue());
		return book;
	}

	@GetMapping(value = "/v1/{id}/{currency}")
	public Book findBookV1(@PathVariable("id") Long id, @PathVariable("currency") String currency) {

		var book = repository.getById(id);
		if (book == null)
			throw new RuntimeException("Book not Found");

		HashMap<String, String> params = new HashMap<>();
		params.put("amount", book.getPrice().toString());
		params.put("from", "USD");
		params.put("to", currency);

		var response = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/" + "{amount}/{from}/{to}",
				Cambio.class, params);

		var cambio = response.getBody();

		var port = environment.getProperty("local.server.port");
		book.setEnvironment(port);
		book.setPrice(cambio.getConvertedValue());
		return book;
	}
}