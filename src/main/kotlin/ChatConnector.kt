import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import java.io.PrintWriter
import java.net.Socket
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Scanner

/**
 * Created by Roope
 */
class ChatConnector(s: Socket) : Runnable , ChatHistoryObserver {

    //Getting the input and output streams from the socket
    private var userName = ""
    private val printStream = PrintWriter(s.getOutputStream())
    private val scanner1 = Scanner(s.getInputStream())
    private var connected: Boolean = true


    //This function is called whenever a new message is received by the server.
    //Prints the message to every observer.
    @UnstableDefault
    override fun newMessage(message: ChatMessage) {
        val messageObjectJson = Json.stringify(ChatMessage.serializer(), message)
        printStream.println(messageObjectJson)
        printStream.flush()
    }


    override fun run() {

        ChatHistory.registerObserver(this)
        ChatHistory.registerObserver(TopChatter)
        ChatHistory.registerObserver(ChatConsole)

        while(connected){
            if(scanner1.hasNextLine()){
                val text = scanner1.nextLine()
                val message = Json.parse(ChatMessage.serializer(), text)
                when(message.message){
                    "-history" -> println(ChatHistory.toString())
                    "-users" -> println(Users.toString())
                    "-topchatter" -> println(TopChatter.toString())
                    "/disconnect" -> disconnect()
                    else -> ChatHistory.insert(message)
                }
            }
        }


    }
    private fun disconnect(){
        ChatHistory.deregisterObserver(this)
        Users.removeUser(userName)
        connected = false
        ChatHistory.insert(ChatMessage("I left the room: " , userName))
    }
}