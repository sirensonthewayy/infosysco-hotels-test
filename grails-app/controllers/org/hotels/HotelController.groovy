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
            flash.message = "Отель успешно обновлен"
            redirect action: "list", id: hotel.id, params: [message: flash.message]
        } catch(ValidationException validationException){
            if(validationException.message.contains("website")){
                flash.message = "Ссылка на веб-сайт указана некорректно"
            } else if(validationException.message.contains("name")){
                flash.message = "Отель с данным названием уже существует в этой стране"
            } else if(validationException.message.contains("stars")){
                flash.message = "Число звезд указано некорректно"
            }
            def hotels = hotelService.searchResult("", -1, 1)
            render view: "list", model: [
                    id: hotel.id,
                    name: params.name,
                    stars: params.stars,
                    website: params.website,
                    countries: countryService.list(sort: "name"),
                    countrySearch: params.countrySearch,
                    hotels: hotels,
                    hotelCount: hotels.getTotalCount(),
                    message: flash.message
            ]
        }
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
            if(validationException.message.contains("website")){
                flash.message = "Ссылка на веб-сайт указана некорректно"
            } else if(validationException.message.contains("name")){
                flash.message = "Отель с данным названием уже существует в этой стране"
            } else if(validationException.message.contains("stars")){
                flash.message = "Число звезд указано некорректно"
            }
            def hotels = hotelService.searchResult("", -1, 1)
            render view: "list", model: [
                    id: hotel.id,
                    editHotel: hotel,
                    hotels: hotels,
                    hotelCount: hotels.getTotalCount(),
                    countries: countryService.list(sort: "name"),
                    message: flash.message
            ]
        }
    }

    def list(String hotelNameSearch, Long countryIdSearch, Short pageNumber) {
        def countries = countryService.list(sort: "name")
        if(!pageNumber) pageNumber = 1
        def hotels = hotelService.searchResult(hotelNameSearch, countryIdSearch, pageNumber)
        [hotels: hotels,
         countries: countries,
         pageNumber: pageNumber,
         hotelCount: hotels.getTotalCount(),
         message: params.message,
         hotelNameSearch: hotelNameSearch,
         countryIdSearch: countryIdSearch]
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