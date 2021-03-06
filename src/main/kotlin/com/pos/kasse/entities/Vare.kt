package com.pos.kasse.entities
import javax.persistence.*
import java.io.Serializable

/**
 * Endre pris til double
 * Legge til flere beskrivelser, f.eks. opprinnelsesland
 * Generere tilfeldig EAN? Lage en utils-metode
 */

@Entity
@javax.persistence.Table(name = "vare", schema = "varer")
@kotlinx.serialization.Serializable
data class Vare(
        @Id
        val ean: Long = 0,
        var navn: String = "",
        var pris: Int = 0,
        var beskrivelse: String = "",
        val plu: Int? = null,
        var sortimentskode: String = "",
        var kategori: String = ""
    ) : Serializable {
    constructor(vare: Vare) : this()
}
