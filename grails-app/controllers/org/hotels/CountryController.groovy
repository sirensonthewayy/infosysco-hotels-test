package org.hotels

import grails.validation.ValidationException

class CountryController {

    CountryService countryService

    static allowedMethods = [save: 'POST', update: 'PUT', delete: 'DELETE']

    def index = {}

    def save = {
        def country = new Country(params)
        try{
            countryService.save(country)
            flash.message = "Страна успешно добавлена"
        } catch(ValidationException validationException){
            flash.message = "Ошибка при добавлении"
        }
        redirect action: "list", params: [message: flash.message]
    }

    def edit = {
        def country = countryService.get(params.id)
        [editCountry: country]
    }

    def update = {
        def country = countryService.get(params.id)
        country.properties = params
        try{
            countryService.save(country)
            flash.message = "Страна успешно обновлена"
        } catch(Exception exception){
            flash.message = "Ошибка при обновлении"
            def countries = countryService.searchResult("", 1 as Short)
            render view: "list", model:[
                    editCountry: country,
                    countries: countries,
                    countryCount: countries.getTotalCount(),
                    message: params.message
            ]
        }
        redirect action: "list", params: [message: flash.message]
    }

    def list(String searchQuery, Short pageNumber){
        if(!pageNumber) pageNumber = 1
        def countries = countryService.searchResult(searchQuery, pageNumber)
        [countries: countries, pageNumber: pageNumber, countryCount: countries.getTotalCount(), message: params.message]
    }

    def delete = {
        try{
            countryService.delete(params.id)
            flash.message = "Страна успешно удалена"
        } catch(Exception exception){
            flash.message = "Ошибка при удалении"
        }
        redirect action: "list", params: [message: flash.message]
    }
}