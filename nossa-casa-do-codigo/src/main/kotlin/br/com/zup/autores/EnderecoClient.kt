package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client("https://viacep.com.br/ws/")
interface EnderecoClient {

    //a uri aparecerá assim: http://localhost:8081/cep/{00000000} <- numero do cep.
    @Get("{cep}/json")
    fun consulta(cep: String) : HttpResponse<EnderecoResponse>

//    @Get("{cep}/json"
//        , consumes = [MediaType.APPLICATION_XML])
//    fun consultaxMl(cep: String) : HttpResponse<EnderecoResponse>

    //também pode ser escrito assim (é a mesma coisa da linha de cima):
    @Get
    @Consumes(MediaType.APPLICATION_XML)
    fun consultaxMl(cep: String) : HttpResponse<EnderecoResponse>
}