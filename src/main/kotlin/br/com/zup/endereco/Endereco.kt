package br.com.zup.endereco

import javax.persistence.Embeddable

@Embeddable
class Endereco(enderecoResponse: EnderecoResponse?, val cep: String, val numero: String) {
    val rua = enderecoResponse?.rua
    val cidade = enderecoResponse?.cidade
    val estado = enderecoResponse?.estado
}
