package br.com.microservice.produto.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String init() {
        return "Rest Produto inicializado";
    }

}
