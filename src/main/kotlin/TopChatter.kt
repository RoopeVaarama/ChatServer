/**
 * Created by Topias, Roope and Tiia
 */

/**
 * TopChatter class includes a list of the most active chatters
 * and it updates everytime a new message is posted.
 */
class TopChatter() : ChatHistoryObserver {
    private val activeUsersMessages = mutableMapOf<String, Int>()
    override fun newMessage(message: ChatMessage) {
        if (message.username !in activeUsersMessages.keys) {
            activeUsersMessages[message.username] = 1
        } else {
            val value = activeUsersMessages[message.username]
            if (value != null) {
                activeUsersMessages[message.username] = value + 1
            }
        }
        activeUsersMessages.forEach {

            k, v -> println("$k: $v")
        }
    }
}