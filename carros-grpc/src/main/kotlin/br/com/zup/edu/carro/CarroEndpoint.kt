package br.com.zup.edu.carro

import br.com.zup.edu.CarrosGrpcReply
import br.com.zup.edu.CarrosGrpcRequest
import br.com.zup.edu.CarrosGrpcServiceGrpc
import io.grpc.Status
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
class CarroEndpoint (@Inject val repository: CarroRepository): CarrosGrpcServiceGrpc.CarrosGrpcServiceImplBase() {

    override fun adicionar(request: CarrosGrpcRequest, responseObserver: StreamObserver<CarrosGrpcReply>) {

        if (repository.existsByPlaca(request.placa)) {
            responseObserver.onError(Status.ALREADY_EXISTS
                .withDescription("Placa já cadastrada!")
                .asRuntimeException())

            return
        }

        val carro = Carro(
            modelo = request.modelo,
            placa = request.placa
        )

        try {
            repository.save(carro)
        } catch (e: ConstraintViolationException){
            responseObserver.onError(Status.INVALID_ARGUMENT
                .withDescription("Dados de entrada inválidos!")
                .asRuntimeException())

            return
        }

        responseObserver.onNext(CarrosGrpcReply.newBuilder().setId(carro.id!!).build())
        responseObserver.onCompleted()
    }
}