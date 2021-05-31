package br.com.zup.autores

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/*
* @Introspected no momento de compilação o micronaut vai poder acessar os atributos
* */
@Introspected
data class NovoAutorRequest(
    @field:NotBlank val nome: String, /* field: indica que o atributo está sendo validado */
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank @field:Size(max = 400) val descricao: String
) {
    fun paraAutor(): Autor {
        return Autor(nome, email, descricao)
    }
}