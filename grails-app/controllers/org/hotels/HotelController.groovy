package org.hotels

import grails.validation.ValidationException

class HotelController {

    HotelService hotelService
    CountryService countryService

    static allowedMethods = [save: 'POST', update: 'PUT', delete: 'DELETE']

    def index = {
    }

    def save = {
        def country = countryService.get(params.countryId)
        def hotel = new Hotel(name: params.name, country: country, stars: params.stars, website: params.website)
        try{
            hotelService.save(hotel)
            flash.message = "Отель успешно добавлен"
        } catch(ValidationException validationException){
            flash.message = "Ошибка при добавлении"
        }
        redirect action: "list", params: [message: flash.message]
    }

    def edit = {
        def hotel = hotelService.get(params.id)
        [editHotel: hotel]
    }

    def update = {
        def hotel = hotelService.get(params.id)
        def country = countryService.get(params.countryId)
        hotel.properties = params
        hotel.country = country
        try{
            hotelService.save(hotel)
            flash.message = "Отель успешно обновлен"
            redirect action: "list", id: hotel.id, params: [message: flash.message]
        } catch(ValidationException validationException){
            flash.message = "Ошибка при обновлении"
            render view: "list", model: [
                    editHotel: hotel,
                    hotels: hotelService.searchResult("", -1, 1),
                    countries: countryService.list(),
                    message: params.message
            ]
        }
    }

    def list(String hotelName, Long countryId, Short pageNumber) {
        def countries = countryService.list()
        if(!pageNumber) pageNumber = 1
        def hotels = hotelService.searchResult(hotelName, countryId, pageNumber)
        [hotels: hotels, countries: countries, pageNumber: pageNumber, hotelCount: hotels.getTotalCount(), message: params.message]
    }

    def delete = {
        try{
            hotelService.delete(params.id)
            flash.message = "Отель успешно удален"
        } catch(Exception exception){
            flash.message = "Ошибка при удалении"
        }
        redirect action: "list", params: [message: flash.message]
    }
}