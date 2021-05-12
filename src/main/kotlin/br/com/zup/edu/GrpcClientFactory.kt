package br.com.zup.edu

import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory {
    @Singleton
    fun validaCpfClientStub(@GrpcChannel("validacpf") channel:ManagedChannel):ValidacpfServiceGrpc.ValidacpfServiceBlockingStub{
        return ValidacpfServiceGrpc.newBlockingStub(channel)
    }
}