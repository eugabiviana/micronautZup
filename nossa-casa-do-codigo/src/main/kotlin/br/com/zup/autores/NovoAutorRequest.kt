package br.com.zup.autores

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import kotlin.math.max

@Introspected
data class NovoAutorRequest(@field:NotBlank val nome: String,
                            @field:NotBlank @field:Email val email: String,
                            @field:NotBlank @Size(max = 400) val descricao: String) {
    fun paraAutor(): Autor {

        return Autor(nome, email, descricao)
    }
}

/*Comentários:
- No Kotlin não precisa passar a validação de novo no construtor
 */