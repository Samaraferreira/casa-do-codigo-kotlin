package br.com.zup.autores.controllers

import br.com.zup.autores.AutorRepository
import br.com.zup.autores.NovoAutorRequest
import br.com.zup.endereco.EnderecoClient
import br.com.zup.endereco.EnderecoResponse
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

@MicronautTest
internal class CadastraAutorControllerTest {

    @field:Inject
    lateinit var enderecoClient: EnderecoClient

    @field:Inject
    lateinit var autorRepository: AutorRepository

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @AfterEach
    internal fun tearDown() {
        autorRepository.deleteAll()
    }

    @Test
    fun `deve cadastrar um novo autor`() {

        // CENÁRIO
        val novoAutorRequest = NovoAutorRequest("Samara", "sam@gmail.com", "bljsjj", "57036850", "12")
        val enderecoResponse = EnderecoResponse("Rua sla", "Maceió", "AL")

        Mockito
            .`when`(enderecoClient.consultarCep(novoAutorRequest.cep))
            .thenReturn(HttpResponse.ok(enderecoResponse))

        val request = HttpRequest.POST("autores", novoAutorRequest)

        // AÇÃO
        val response = client.toBlocking().exchange(request, Any::class.java)

        // VALIDAÇÃO
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.matches("/autores/\\d".toRegex()))
    }

    @MockBean(EnderecoClient::class)
    fun enderecoClientMock(): EnderecoClient {
        return Mockito.mock(EnderecoClient::class.java)
    }

}