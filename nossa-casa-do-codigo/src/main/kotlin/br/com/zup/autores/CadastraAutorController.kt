package br.com.zup.autores

import io.micronaut.http.HttpResponse.uri
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.badRequest
import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController (val autorRepository: AutorRepository,
                               val enderecoClient: EnderecoClient){

    @Post
    @Transactional
    fun cadastra(@Body @Valid request: NovoAutorRequest) : HttpResponse<Any>{
        println("Request => ${request}")

        //fazer uma requisição para um serviço externo
       val enderecoResponse = enderecoClient.consulta(request.cep)

       val autor = request.paraAutor(enderecoResponse.body()!!)

        //como fazer o request virar um domínio (request => domínio)

        println("Autor => ${autor.nome}")
        autorRepository.save(autor)

        val uri = UriBuilder.of("/autores/{id}")
                            .expand(mutableMapOf(Pair("id", autor.id)))

        return HttpResponse.created(uri)

    }
}