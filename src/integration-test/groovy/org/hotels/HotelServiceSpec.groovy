package org.hotels

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class HotelServiceSpec extends Specification {

    HotelService hotelService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Hotel(...).save(flush: true, failOnError: true)
        //new Hotel(...).save(flush: true, failOnError: true)
        //Hotel hotel = new Hotel(...).save(flush: true, failOnError: true)
        //new Hotel(...).save(flush: true, failOnError: true)
        //new Hotel(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //hotel.id
    }

    void "test get"() {
        setupData()

        expect:
        hotelService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Hotel> hotelList = hotelService.list(max: 2, offset: 2)

        then:
        hotelList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        hotelService.count() == 5
    }

    void "test delete"() {
        Long hotelId = setupData()

        expect:
        hotelService.count() == 5

        when:
        hotelService.delete(hotelId)
        sessionFactory.currentSession.flush()

        then:
        hotelService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Hotel hotel = new Hotel()
        hotelService.save(hotel)

        then:
        hotel.id != null
    }
}
