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
            redirect action: "list", params: [message: flash.message]
        } catch(ValidationException validationException){
            flash.message = "Страна с таким названием уже существует"
            def countries = countryService.searchResult("", 1 as short)
            render view: "list", model: [
                    name: params.name,
                    capital: params.capital,
                    countries: countries,
                    countryCount: countries.getTotalCount(),
                    message: flash.message
            ]
        }
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
            redirect action: "list", params: [message: flash.message]
        } catch(ValidationException validationException){
            flash.message = "Страна с таким названием уже существует"
            def countries = countryService.searchResult(params.nameQuery, params.pageNumber)
            render view: "list", model: [
                    editCountry : country,
                    countries   : countries,
                    countryCount: countries.getTotalCount(),
                    message     : flash.message
            ]
        }
    }

    def list(String searchQuery, Short pageNumber){
        if(!pageNumber) pageNumber = 1
        def countries = countryService.searchResult(searchQuery, pageNumber)
        [countries: countries, pageNumber: pageNumber, countryCount: countries.getTotalCount(), message: params.message, nameQuery: searchQuery]
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