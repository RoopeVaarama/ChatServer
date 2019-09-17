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
        var messageObjectJson = Json.stringify(ChatMessage.serializer(), message)
        printStream.println(messageObjectJson) // goes to buffer
        printStream.flush() // pushes it from buffer
    }


    override fun run(){

        //creates a scanner for reading user input

        printStream.println("Insert message")
        printStream.flush()
        while(true) {
            val userinput: String = scanner1.nextLine()
            val currentTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
            val formattedTime = currentTime.format(formatter)

            //creates an object of ChatMessage type of the user input
            val messageObject = ChatMessage(userinput, formattedTime)
            ChatHistory.insert(messageObject)
            //println(messageObjectJson)
        }

    }

}