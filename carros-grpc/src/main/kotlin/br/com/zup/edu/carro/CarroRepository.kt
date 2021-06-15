package br.com.zup.edu.carro

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface CarroRepository : JpaRepository<Carro, Long>{

    fun existsByPlaca(palaca: String): Boolean
}