package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client("https://viacep.com.br/ws/")
interface EnderecoClient {

    //a uri aparecerá assim: http://localhost:8081/cep/{00000000} <- numero do cep.
    @Get("{cep}/json")
    fun consulta(cep:String) :HttpResponse<EnderecoResponse>
}