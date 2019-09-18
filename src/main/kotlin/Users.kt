import kotlinx.serialization.enumFromName

/**
 * Created by Topias, Roope and Tiia
 */

/**
 * This singleton handles all the user data
 */
object Users {

    val setofUsers = hashSetOf<String>()

    fun insertUser(name: String) {
        if (name in setofUsers) {

        } else {
            setofUsers.add(name)
        }
    }

    fun removeUser(name: String) {
        if (name in setofUsers) {
            setofUsers.remove(name)
        }
    }

    override fun toString(): String {
        var userNames: String = ""
        for (name in setofUsers) {
            userNames += (name) + "\n"
        }
        return userNames
    }
}