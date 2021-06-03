package br.com.zup.autores.controllers

import br.com.zup.autores.AutorRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import javax.transaction.Transactional

@Controller("/autores")
class DeletaAutorController(val autorRepository: AutorRepository) {

    @Delete("/{id}")
    @Transactional
    fun deleta(@PathVariable id: Long) : HttpResponse<Any> {
        val possivelAutor = autorRepository.findById(id)

        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()
        autorRepository.delete(autor)

        return HttpResponse.ok()
    }
}