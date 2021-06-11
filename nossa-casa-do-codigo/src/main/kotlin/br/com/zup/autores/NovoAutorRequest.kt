package br.com.zup.autores

import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import kotlin.math.max

@Introspected
data class NovoAutorRequest(@field:NotBlank val nome: String,
                            @field:NotBlank @field:Email val email: String,
                            @field:NotBlank @Size(max = 400) val descricao: String,
                            @field:NotBlank val cep: String,
                            @field:NotBlank val numero:String) {

    fun paraAutor(enderecoResponse: EnderecoResponse): Autor {

        val endereco = Endereco(enderecoResponse, numero)
        return Autor(nome, email, descricao, endereco)
    }
}

/*Comentários:
- No Kotlin não precisa passar a validação de novo no construtor
 */