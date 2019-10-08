import kotlinx.serialization.Serializable

/**
 * Created by Roope
 */


/**
 * Chat message is the basic message class.
 */
@Serializable
class ChatMessage(var message: String = " ", var username : String = " "){
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

    //Add message to the ChatMessage list and notifies observers
    fun insert(message: ChatMessage) {
        listOfChatMessages.add(message)
        notifyObservers(message)
    }
    //registering observer
    override fun registerObserver(observer: ChatHistoryObserver) {
        observers.add(observer)
    }
    //deregister observer
    override fun deregisterObserver(observer: ChatHistoryObserver) {
        observers.remove(observer)
    }
    //notify observer
    override fun notifyObservers(message: ChatMessage) {
        observers.forEach{it.newMessage(message)}
    }
    //list of ChatMessages
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
//Prints the new message to the Console when notified by observer
object ChatConsole : ChatHistoryObserver {
    override fun newMessage(message: ChatMessage) {
        println(message)
    }
}
