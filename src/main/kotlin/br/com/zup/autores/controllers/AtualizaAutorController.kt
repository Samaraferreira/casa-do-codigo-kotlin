package br.com.zup.autores.controllers

import br.com.zup.autores.AutorRepository
import br.com.zup.autores.DetalhesDoAutorResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put
import javax.transaction.Transactional

@Controller("/autores")
class AtualizaAutorController(val autorRepository: AutorRepository) {

    @Put("/{id}")
    @Transactional
    fun atualiza(@PathVariable id: Long, descricao: String) : HttpResponse<Any> { /* vai fazer o bind do campo descricao: só pega o atributo descricao do json */
        val possivelAutor = autorRepository.findById(id)

        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()
        autor.descricao = descricao

//        autorRepository.update(autor)

        return HttpResponse.ok(DetalhesDoAutorResponse(autor))
    }
}