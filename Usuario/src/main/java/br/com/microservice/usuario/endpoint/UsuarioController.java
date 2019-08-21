package br.com.microservice.usuario.endpoint;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String inicial() {
        return "Microservice Enabled.";
    }

}
