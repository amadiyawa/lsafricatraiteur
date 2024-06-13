package com.amadiyawa.feature_service.domain.model

data class Prestation(
    val name: String,
    val price: Int,
    val guess: String,
    val location: String
)

fun generatePrestationList(): List<Prestation> {
    return listOf(
        Prestation(
            name = "Location de salles de reception",
            price = 50000,
            guess = "25 A 50",
            location = "Bertoua"
        ),
        Prestation(
            name = "Livraison de plateaux repas",
            price = 1000,
            guess = "1",
            location = "Bertoua"
        ),
        Prestation(
            name = "Elaboration de packs personnalises",
            price = 50000,
            guess = "25 A 50",
            location = "Bertoua"
        ),
        Prestation(
            name = "Decoration et couverture complete de l evenement",
            price = 50000,
            guess = "25 A 50",
            location = "Bertoua"
        )
    )
}
