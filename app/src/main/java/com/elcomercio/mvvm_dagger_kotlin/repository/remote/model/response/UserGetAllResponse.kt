package com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.response

/**
 * @author carlosleonardocamilovargashuaman on 4/27/18.
 */
class UserGetAllResponse {


    /**
     * status : SUCCESS
     * message : User was found
     * data : [{"Id":3,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"},{"Id":4,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"},{"Id":5,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"},{"Id":6,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"},{"Id":7,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"},{"Id":8,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"},{"Id":9,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"},{"Id":10,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"},{"Id":11,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"},{"Id":12,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"},{"Id":13,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"},{"Id":14,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"},{"Id":15,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"},{"Id":16,"FirstName":"EDUARDO","LastName":"VARGAS","Email":"eduardo@gmail"}]
     */

    var status: String? = null
    var message: String? = null
    var data: List<DataBean>? = null

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
