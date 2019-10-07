import kotlinx.serialization.enumFromName

/**
 * Created by Roope
 */

/**
 * This singleton handles all the user data
 */
object Users {
    val setofUsers = hashSetOf<String>()

    fun insertUser(name: String) {
        if(name !in setofUsers) {
            setofUsers.add(name)
        }
    }
    fun removeUser(name: String) {
        if (name in setofUsers) {
            setofUsers.remove(name)
        }
    }
    override fun toString(): String {
        var userNames = ""
        for (name in setofUsers) {
            userNames += (name) + "\n"
        }

        return userNames

    }
}