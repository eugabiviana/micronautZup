package br.com.zup.autores

import io.micronaut.http.HttpResponse.created
import io.micronaut.http.HttpResponse.uri
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import io.micronaut.http.HttpResponse
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController (@Inject val autorRepository: AutorRepository){

    @Post
    fun cadastra(@Body @Valid request: NovoAutorRequest) : HttpResponse<Any>{
        println(request)

        //como fazer o request virar um domínio (request => domínio)

        println("Requisição => ${request}")

        val autor = request.paraAutor()
        autorRepository.save(autor)

        println("Autor => ${autor.nome}")

        val uri = UriBuilder.of("/autores/{id}")
                            .expand(mutableMapOf(Pair("id", autor.id)))

        return HttpResponse.created(uri)


    }
}