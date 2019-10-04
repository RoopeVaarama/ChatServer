import kotlinx.serialization.Serializable

/**
 * Created by Topias, Roope and Tiia
 */


/**
 * Chat message is the basic message class.
 */
@Serializable
class ChatMessage(var message: String = " ", /*var formattedtime : String = " ".*/ var username : String = " "){
    override fun toString(): String {
        /*Time: $formattedtime*/
        return "Message: $message User: $username"
    }
}

/**
 * ChatHistory holds a list of the entire chat history and observers that
 * observe it's changes.
 */
object ChatHistory : ChatHistoryObservable {
    private val observers = mutableSetOf<ChatHistoryObserver>()

    fun insert(message: ChatMessage) {
        listOfChatMessages.add(message)
        notifyObservers(message)
    }

    override fun registerObserver(observer: ChatHistoryObserver) {
        observers.add(observer)
    }

    override fun deregisterObserver(observer: ChatHistoryObserver) {
        observers.remove(observer)
    }

    override fun notifyObservers(message: ChatMessage) {
        observers.forEach{it.newMessage(message)}
    }

    private val listOfChatMessages = mutableListOf<ChatMessage>()

    //Returns the entire chat history as a single string
    override fun toString(): String {
        var chatHistory = ""
        for (chatMessage in listOfChatMessages) {
            /*(chatMessage.formattedtime) + (" ") +*/
            chatHistory +=  (chatMessage.message) + "\n"
        }
        return chatHistory
    }
}

interface ChatHistoryObservable {
    fun registerObserver(observer:ChatHistoryObserver)
    fun deregisterObserver(observer:ChatHistoryObserver)
    fun notifyObservers (message:ChatMessage)
}

interface ChatHistoryObserver {
    fun newMessage(message:ChatMessage)

}
object ChatConsole : ChatHistoryObserver {
    override fun newMessage(message: ChatMessage) {
        println(message)
    }
}
