package com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response

/**
 * @author carlosleonardocamilovargashuaman on 4/20/18.
 */
class UserResponse {

    /**
     * id : 1
     * name : Leanne Graham
     * username : Bret
     * email : Sincere@april.biz
     * address : {"street":"Kulas Light","suite":"Apt. 556","city":"Gwenborough","zipcode":"92998-3874","geo":{"lat":"-37.3159","lng":"81.1496"}}
     * phone : 1-770-736-8031 x56442
     * website : hildegard.org
     * company : {"name":"Romaguera-Crona","catchPhrase":"Multi-layered client-server neural-net","bs":"harness real-time e-markets"}
     */

    var id: Int = 0
    var name: String? = null
    var username: String? = null
    var email: String? = null
    var address: AddressBean? = null
    var phone: String? = null
    var website: String? = null
    var company: CompanyBean? = null

    class AddressBean {
        /**
         * street : Kulas Light
         * suite : Apt. 556
         * city : Gwenborough
         * zipcode : 92998-3874
         * geo : {"lat":"-37.3159","lng":"81.1496"}
         */

        var street: String? = null
        var suite: String? = null
        var city: String? = null
        var zipcode: String? = null
        var geo: GeoBean? = null

        class GeoBean {
            /**
             * lat : -37.3159
             * lng : 81.1496
             */

            var lat: String? = null
            var lng: String? = null
        }
    }

    class CompanyBean {
        /**
         * name : Romaguera-Crona
         * catchPhrase : Multi-layered client-server neural-net
         * bs : harness real-time e-markets
         */

        var name: String? = null
        var catchPhrase: String? = null
        var bs: String? = null
    }
}