import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.io.PrintWriter
import java.net.Socket
import java.util.Scanner

public class ChatConnector(s: Socket) : Runnable , ChatHistoryObserver{
    private val printStream = PrintWriter(s.getOutputStream())
    private val scanner1 = Scanner(s.getInputStream())
    override fun newMessage(message: ChatMessage) {

        printStream.println(message)
    }


    override fun run(){

        //creates a scanner for reading user input

        while(true) {
            println("Insert message")
            val userinput: String = scanner1.nextLine()


            //creates an object of ChatMessage type of the user input
            val messageObject = ChatMessage(userinput)
            ChatHistory.insert(messageObject)
        }
        println(ChatHistory.toString())
    }

}