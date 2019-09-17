import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.format.DateTimeFormatter

@Serializable
public class ChatMessage(var message: String = "", var formattedtime : String = "", var username : String = ""){


    override fun toString(): String {
        return message
    }
}

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

    val listOfChatMessages = mutableListOf<ChatMessage>()

    override fun toString(): String {

        var chatHistory : String = ""
        for (chatMessage in listOfChatMessages) {
            chatHistory += (chatMessage.formattedtime) + (" ") + (chatMessage.message) + "\n"
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