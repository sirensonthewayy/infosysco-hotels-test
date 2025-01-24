package org.hotels

import grails.gorm.PagedResultList
import grails.gorm.services.Service

interface IHotelService {

    Hotel get(Serializable id)

    List<Hotel> list(Map args)

    Long count()

    void delete(Serializable id)

    Hotel save(Hotel hotel)

    Hotel save(String name, Country country, Integer stars, String website)
}

@Service(Hotel)
abstract class HotelService implements IHotelService{

    CountryService countryService

    PagedResultList<Hotel> searchResult(String hotelName, Long countryId, Integer pageNumber){
        def criteria = Hotel.createCriteria()
        def results = criteria.list(max: 10, offset: (pageNumber - 1) * 10){
            if(hotelName){
                ilike("name", "%" + hotelName + "%")
            }
            if(countryId > -1){
                def country = countryService.get(countryId)
                eq("country", country)
            }
            order("stars", "desc")
            order("name", "asc")
        }
        return results as PagedResultList<Hotel>
    }
}

