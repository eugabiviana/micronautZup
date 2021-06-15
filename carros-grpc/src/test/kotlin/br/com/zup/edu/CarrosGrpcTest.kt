package br.com.zup.edu
import br.com.zup.edu.carro.Carro
import br.com.zup.edu.carro.CarroRepository
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest(
    rollback = false,
    transactionMode = TransactionMode.SINGLE_TRANSACTION,
    transactional = false

)
class CarrosGrpcTest {

    /** Entendendo as estratégias dos testes:
     * louça: sujou, limpou -> @AfterEach
     * louça: limpou, usou -> @BeforeEach [x] preferida
     * louça: usa louça descartável -> rollback=true
     * louça: uso a louça, jogo fora, compro nova -> recriar o banco a cada teste
     */

    @Inject
    lateinit var repository: CarroRepository

    @BeforeEach
    fun setup(){
        repository.deleteAll()
    }

    @AfterEach
    fun cleanUp(){
        repository.deleteAll()
    }

    @Test
    fun `deve inserir um novo carro`() {

        //ação
        repository.save(Carro(modelo = "Gol", placa = "HPX-1234"))

        //validação
        assertEquals(1, repository.count())

    }

    @Test
    fun `deve encontrar carro por placa`(){
        //cenário
        repository.save(Carro(modelo = "Palio", placa = "OIP-9876"))

        //ação
        val encontrado = repository.existsByPlaca("OIP-9876")

        //validação
        assertTrue(encontrado)

    }

}
/*
esses dados não aparecem na tabela, porque o micronaut faz o rollback, ou seja, já limpa a tabela após o teste.
se colocar o (rollback=false), ele gera um commit, e os dados aparecem na tabela.
 */

