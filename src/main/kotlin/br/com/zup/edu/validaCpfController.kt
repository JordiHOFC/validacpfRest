package br.com.zup.edu

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue

import javax.inject.Inject

@Controller("/api/validacpf")
class validaCpfController(@Inject val gRpcClient: ValidacpfServiceGrpc.ValidacpfServiceBlockingStub) {

    @Get
    fun valido(@QueryValue cpf: String): MutableHttpResponse<StatusCpfResponse>? {
        var request = ValidacpfRequest.newBuilder()
            .setCpf(cpf)
            .build()
        var valida = gRpcClient.valida(request)
        val reponse=StatusCpfResponse(valida)
        return HttpResponse.ok(reponse)
    }
}