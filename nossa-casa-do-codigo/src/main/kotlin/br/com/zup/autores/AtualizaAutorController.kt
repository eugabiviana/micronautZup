package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put
import javax.transaction.Transactional

@Controller("/autores/{id}")
class AtualizaAutorController(val autorRepository: AutorRepository) {

    @Put
    @Transactional
    fun atualiza(@PathVariable id: Long, descricao:String) : HttpResponse<Any>{
        //buscar o objeto no banco
       val possivelAutor = autorRepository.findById(id)

        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        //atualizar o campo
        val autor = possivelAutor.get()
        autor.descricao = descricao
        //autorRepository.update(autor) o código continua funcionando sem essa linha, por causa do select do db e da @Transactional

        //retornar status ok ou qualquer outro
        return HttpResponse.ok(DetalhesDoAutorResponse(autor))


    }

}