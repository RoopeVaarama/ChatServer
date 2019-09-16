import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.io.PrintWriter
import java.net.Socket
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Scanner


public class ChatConnector(s: Socket) : Runnable , ChatHistoryObserver{
    private val printStream = PrintWriter(s.getOutputStream())
    private val scanner1 = Scanner(s.getInputStream())
    override fun newMessage(message: ChatMessage) {

        var newmessage = Json.stringify(ChatMessage.serializer(), message)
        printStream.println(newmessage)
    }


    override fun run(){

        //creates a scanner for reading user input

        while(true) {
            println("Insert message")
            val userinput: String = scanner1.nextLine()
            val currentTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
            val formattedTime = currentTime.format(formatter)

            //creates an object of ChatMessage type of the user input
            val messageObject = ChatMessage(userinput, formattedTime)
            val messageObjectJson = Json.stringify(ChatMessage.serializer(), messageObject)
            val messageObjectparse = Json.parse(ChatMessage.serializer(), messageObjectJson)
            ChatHistory.insert(messageObjectparse)
            printStream.println(messageObjectJson)

        }

    }

}