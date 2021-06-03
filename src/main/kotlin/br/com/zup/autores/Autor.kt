package br.com.zup.autores

import br.com.zup.endereco.Endereco
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Autor(
    @Column(nullable = false)
    val nome: String,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    var descricao: String,
    @Column(nullable = false)
    val endereco: Endereco
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    val dataCriacao: LocalDateTime = LocalDateTime.now()
}