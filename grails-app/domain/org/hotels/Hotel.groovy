package org.hotels

class Hotel {
    String name
    Country country
    Integer stars
    String website

    static belongsTo = [country: Country]

    static constraints = {
        name maxSize: 255
        stars range: 1..5
        website url: true, nullable: true, matches: "https?:\\/\\/\\S{2,}"
        name validator: {val, obj ->
            Hotel existingHotel = Hotel.findByNameAndCountry(val, obj.country)
            if (existingHotel && existingHotel.id != obj.id) {
                return 'hotel.name.validator.error'
            }
        }
    }
}
