package br.com.zup.autores

import com.fasterxml.jackson.annotation.JsonProperty

data class EnderecoResponse(
    @JsonProperty("logradouro") val rua: String,
    @JsonProperty("localidade") val cidade: String,
    @JsonProperty("uf") val estado: String
)