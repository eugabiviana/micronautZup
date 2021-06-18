package br.com.zup.edu.carro

import br.com.zup.edu.CarrosGrpcRequest
import br.com.zup.edu.CarrosGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.inject.Singleton

@MicronautTest(transactional = false)
internal class CarroEndpointTest(
    val repository: CarroRepository,
    val grpcClient: CarrosGrpcServiceGrpc.CarrosGrpcServiceBlockingStub){

    @BeforeEach
    fun setup(){
        repository.deleteAll()
    }

    /**
     * 1. happy path - ok
     * 2. quando já existe carro com a placa - ok
     * 3. quando os dados de entrada são inválidos
     */

    @Test
    fun `deve adicionar um novo carro`(){
//        //Cenário
//        repository.deleteAll() -> não preciso mais dessa linha, por causa do @BeforeEach

        //Ação
        val response = grpcClient.adicionar(CarrosGrpcRequest.newBuilder()
                                                            .setModelo("Gol")
                                                            .setPlaca("HPX-1234")
                                                            .build())

        //Validação
        with(response){
            assertNotNull(id)
            assertTrue(repository.existsById(id)) //efeito colateral
        }
    }

    @Test
    fun `não deve adicionar um carro quando ele já estiver cadastrado`(){
//        //Cenário
//        repository.deleteAll() -> não preciso mais dessa linha, por causa do @BeforeEach
        val existente = repository.save(Carro("Palio", "OIP-9876"))
        //esse existente é para que eu não passe a placa errada no teste

        //Ação
        val error = assertThrows<StatusRuntimeException> {
            grpcClient.adicionar(CarrosGrpcRequest.newBuilder()
                                                .setModelo("Ferrari")
                                                .setPlaca(existente.placa) //aí aqui só digita a val existente com o parametro placa
                                                .build())
        }

        //Validação
        with(error){
            assertEquals(Status.ALREADY_EXISTS.code, status.code)
            assertEquals("Placa já cadastrada!", status.description)
        }

    }

    @Test
    fun `não deve adicionar novo carro quando os dados de entrada forem inválidos`(){
//        //Cenário
//        repository.deleteAll() -> não preciso mais dessa linha, por causa do @BeforeEach

        //esse existente é para que eu não passe a placa errada no teste

        //Ação
        val error = assertThrows<StatusRuntimeException> {
            grpcClient.adicionar(CarrosGrpcRequest.newBuilder().build())
        }

        //Validação
        with(error){
            assertEquals(Status.INVALID_ARGUMENT.code, status.code)
            assertEquals("Dados de entrada inválidos!", status.description)
            //TODO: verificar as violações da bean validation
        }
    }

    @Factory
    class Clients{

        @Singleton
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): CarrosGrpcServiceGrpc.CarrosGrpcServiceBlockingStub? {
            return CarrosGrpcServiceGrpc.newBlockingStub(channel)
        }
    }
}