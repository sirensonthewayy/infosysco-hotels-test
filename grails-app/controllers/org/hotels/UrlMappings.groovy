package org.hotels

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(redirect: '/hotel/list')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
