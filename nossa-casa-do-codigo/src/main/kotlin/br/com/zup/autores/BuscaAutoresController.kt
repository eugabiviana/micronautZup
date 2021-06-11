package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/autores")
class BuscaAutoresController(val autorRepository: AutorRepository) {

    @Get
    fun lista(): HttpResponse<List<DetalhesDoAutorResponse>> {
        //pegar do banco de dados
        val autores = autorRepository.findAll()

        //conversao para um dto saida
        val resposta = autores.map { autor -> DetalhesDoAutorResponse(autor) }

        //retornar essa lista
        return HttpResponse.ok(resposta)


    }

}