package org.hotels

import grails.gorm.PagedResultList
import grails.gorm.services.Service

interface ICountryService {

    Country get(Serializable id)

    List<Country> list(Map args)

    Long count()

    void delete(Serializable id)

    Country save(Country country)

    Country save(String name, String capital)

    Country findByName(String name)

}

@Service(Country)
abstract class CountryService implements ICountryService{

    PagedResultList<Country> searchResult(String searchQuery, Short pageNumber){
        def criteria = Country.createCriteria()
        def results = criteria.list(max: 10, offset: (pageNumber - 1) * 10) {
            if(searchQuery){
                ilike("name", "%" + searchQuery + "%")
            }
            order("name")
        }
        return results as PagedResultList<Country>
    }

}