package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import javax.transaction.Transactional


@Controller("/autores/{id}")
class DeletaAutorController(val autorRepository: AutorRepository){

    @Delete
    @Transactional
    fun deleta(@PathVariable id: Long) : HttpResponse<Any> {
        //buscar o objeto no banco e, se existir, deleta
        val possivelAutor = autorRepository.findById(id)
        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }
        //autorRepository.deleteById(id) deleta o autor chamado pelo id

        //outra opção para deletar informações, mas aqui é chamando a entity. Funciona do mesmo jeito
        val autor = possivelAutor.get()
        autorRepository.delete(autor)

        //retornar status ok para informar que o autor foi deletado
        return HttpResponse.ok()


    }
}