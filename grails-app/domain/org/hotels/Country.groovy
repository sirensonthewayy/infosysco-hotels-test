package org.hotels

class Country {
    String name
    String capital

    static hasMany = [hotels: Hotel]

    static constraints = {
        name maxSize: 255, unique: true
        capital maxSize: 128
    }
}
