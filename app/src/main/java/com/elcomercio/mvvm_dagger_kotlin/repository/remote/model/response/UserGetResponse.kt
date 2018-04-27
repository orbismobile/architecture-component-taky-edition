package com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response

/**
 * @author carlosleonardocamilovargashuaman on 4/20/18.
 */
class UserGetResponse {

    /**
     * status : SUCCESS
     * message : User was found
     * data : {"Id":3,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"}
     */

    var status: String? = null
    var message: String? = null
    var data: DataBean? = null

    class DataBean {
        /**
         * Id : 3
         * FirstName : EDUARDO
         * LastName : VARGAS
         * Email : eduardo@gmail
         */

        var id: Int = 0
        var firstName: String? = null
        var lastName: String? = null
        var email: String? = null
    }
}