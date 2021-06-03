package br.com.zup.autores

import br.com.zup.endereco.Endereco
import br.com.zup.endereco.EnderecoResponse
import br.com.zup.utils.annotations.UniqueValue
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/*
* @Introspected no momento de compilação o micronaut vai poder acessar os atributos
* */
@Introspected
data class NovoAutorRequest(
    @field:NotBlank
    val nome: String, /* field: indica que o atributo está sendo validado */
    @field:NotBlank @field:Email @field:UniqueValue(clazz = "Autor", fieldName = "email")
    val email: String,
    @field:NotBlank @field:Size(max = 400)
    val descricao: String,
    @field:NotBlank
    val cep: String,
    @field:NotBlank
    val numero: String
) {
    fun paraAutor(enderecoResponse: EnderecoResponse): Autor {
        val endereco = Endereco(enderecoResponse, cep, numero)
        return Autor(nome, email, descricao, endereco)
    }
}