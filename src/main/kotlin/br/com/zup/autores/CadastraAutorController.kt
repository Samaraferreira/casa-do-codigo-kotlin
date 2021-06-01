package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController(
    val autorRepository: AutorRepository, /* @Inject pode ser omitido */
    val enderecoClient: EnderecoClient
) {

    private val logger = LoggerFactory.getLogger(CadastraAutorController::class.java)

    @Post
    @Transactional
    fun cadastra(@Body @Valid request: NovoAutorRequest) : HttpResponse<Any> {  /* @Body pode ser omitido */

        try {
            val enderecoResponse = enderecoClient.consultarCep(request.cep)

            val autor = request.paraAutor(enderecoResponse.body()!!)
            autorRepository.save(autor)

            val uri = UriBuilder.of("/autores/{id}")
                .expand(mutableMapOf(Pair("id", autor.id)))

            return HttpResponse.created(uri)
        } catch (e: HttpClientResponseException) {
            logger.error("Erro ao consultar cep. Mensagem: ${e.message} ")
            return HttpResponse.unprocessableEntity()
        }

    }
}