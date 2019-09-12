import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

public class ChatMessage(var message: String = ""){
    val currentTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
    val formattedTime = currentTime.format(formatter)
}

object ChatHistory : ChatHistoryObservable {
    private val observers = mutableListOf<ChatHistoryObserver>()

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
            chatHistory += (chatMessage.formattedTime) + (" ") + (chatMessage.message) + "\n"
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