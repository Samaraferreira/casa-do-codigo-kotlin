package br.com.zup.endereco

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client("\${endpoints.api.viacep}")
interface EnderecoClient {

    @Get("/{cep}/json")
    fun consultarCep(cep: String) : HttpResponse<EnderecoResponse>
}