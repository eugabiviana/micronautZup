package br.com.zup.autores

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController {

    @Post
    fun cadastra(@Body @Valid request: NovoAutorRequest){
        println(request)

        //como fazer o request virar um domínio (request => domínio)

        println("Requisição => ${request}")

        val autor = request.paraAutor()

        println("Autor => ${autor.nome}")


    }
}