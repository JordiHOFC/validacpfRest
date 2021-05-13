package br.com.zup.edu


import com.google.rpc.StatusProto
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.exceptions.HttpStatusException

import javax.inject.Inject

@Controller("/api/validacpf")
class ValidaCpfController(@Inject val gRpcClient: ValidacpfServiceGrpc.ValidacpfServiceBlockingStub) {

    @Get
    fun valido(@QueryValue cpf: String): MutableHttpResponse<StatusCpfResponse> {
        var reponse: StatusCpfResponse? =null
        try {
            var request = ValidacpfRequest.newBuilder()
                .setCpf(cpf)
                .build()
            var valida = gRpcClient.valida(request)
            reponse = StatusCpfResponse(valida)

        } catch (e: StatusRuntimeException) {
            val status = e.status
            val statusCode = status.code
            val description = status.description
            if (Status.Code.INVALID_ARGUMENT == statusCode) {
                val statusProto = io.grpc.protobuf.StatusProto.fromThrowable(e)
                if(statusProto==null){
                    throw HttpStatusException(HttpStatus.BAD_REQUEST,description)
                }

                val anyDetails = statusProto.detailsList.get(0)
                val details=anyDetails.unpack(ErrorDetails::class.java)

                throw HttpStatusException(HttpStatus.BAD_REQUEST,"${details.code} : ${details.message}")
            }
        }

        return HttpResponse.ok(reponse)
    }
}