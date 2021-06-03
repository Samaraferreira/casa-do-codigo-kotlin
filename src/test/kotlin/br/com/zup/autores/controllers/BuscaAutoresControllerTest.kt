package br.com.zup.autores.controllers

import br.com.zup.autores.Autor
import br.com.zup.autores.AutorRepository
import br.com.zup.endereco.Endereco
import br.com.zup.endereco.EnderecoResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.inject.Inject

@MicronautTest
internal class BuscaAutoresControllerTest {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @field:Inject
    lateinit var autorRepository: AutorRepository

    lateinit var autor: Autor

    @BeforeEach
    internal fun setUp() {
        val enderecoResponse = EnderecoResponse("Rua Kotlin", "Maceió", "AL")
        val endereco = Endereco(enderecoResponse, "57036850", "12")
        autor = Autor("Samara", "sam@gmail.com", "bla bla bla", endereco)

        autorRepository.save(autor)
    }

    @AfterEach
    internal fun tearDown() {
        autorRepository.deleteAll()
    }

    @Test
    fun `deve retornar os detalhes de um autor`() {

        val response = client.toBlocking().exchange("/autores?email=${autor.email}", Autor::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(autor.nome, response.body()!!.nome)
        assertEquals(autor.descricao, response.body()!!.descricao)
        assertEquals(autor.email, response.body()!!.email)
    }

    @Test
    fun `deve retornar 404 quando autor nao for encontrado`() {

        assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange("/autores?email=lorem@ipsum.com", Any::class.java)
        }.let { exception ->
            assertEquals(HttpStatus.NOT_FOUND, exception.status)
        }
    }

    @Test
    fun `deve retornar uma lista de autores`() {

        val response = client.toBlocking().exchange("/autores", List::class.java)

        assertEquals(1, response.body().size)
    }
}