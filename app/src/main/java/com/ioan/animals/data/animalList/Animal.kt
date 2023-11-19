package com.ioan.animals.data.animalList


data class AnimalsResponse(
    val animals: List<Animal>,
    val pagination: Pagination
)

data class Animal(
    var name: String,
    val breeds: Breed,
    val size: String,
    val gender: String,
    val status: String,
    val distance: String
)

data class Breed(
    val primary: String,
    val secondary: String,
    val mixed: Boolean,
    val unknown: Boolean
)

data class Pagination(
    val count_per_page: Int,
    val total_count: Int,
    val current_page: Int,
    val total_pages: Int
)
