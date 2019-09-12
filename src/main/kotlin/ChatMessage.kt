import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

public class ChatMessage(var message: String = ""){
    val currentTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    val formattedTime = currentTime.format(formatter)
}

object ChatHistory {
    val listOfChatMessages = mutableListOf<ChatMessage>()

    fun insert(message: ChatMessage){
        listOfChatMessages.add(message)
    }

    override fun toString(): String {
        var chatHistory : String = ""
        for (chatMessage in listOfChatMessages) {
            chatHistory += (chatMessage.formattedTime) + (" ") + (chatMessage.message) + "\n"
        }
        return chatHistory
    }
}

interface ChatHistoryObserver {
    fun registerObserver(observer:ChatHistoryObserver)
    fun deregisterObserver(observer:ChatHistoryObserver)
    fun notifyObservers (message:ChatMessage)
}

interface ChatHistoryObservable {
    fun newMessage(message:ChatMessage)
}