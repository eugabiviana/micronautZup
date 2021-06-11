package br.com.zup.autores

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository //No micronaut essa annotation é obrigatória, senão ele não reconhece o repository!
interface AutorRepository : JpaRepository<Autor, Long> {

}