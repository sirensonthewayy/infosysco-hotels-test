package org.hotels

class BootStrap {
    CountryService countryService
    HotelService hotelService

    def init = { servletContext ->
        String hotelsFilePath = "grails-app/init/resources/hotels_data.csv"
        File hotelsFile = new File(hotelsFilePath)
        def hotelsRows = hotelsFile.readLines().tail()*.split(',')
        for(row in hotelsRows){
            Country country = countryService.findByName(row[1])
            if (!country) {
                country = new Country(name: row[1], capital: row[4])
                countryService.save(country)
            }
            hotelService.save(row[0], country, Integer.valueOf(row[2]), row[3])
        }

    }
    def destroy = {
    }
}
