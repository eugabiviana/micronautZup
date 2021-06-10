package br.com.zup.autores

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class Autor (val nome: String,
             val email: String,
             val descricao: String)
