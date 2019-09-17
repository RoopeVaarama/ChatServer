import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.io.PrintWriter
import java.net.Socket
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Scanner


public class ChatConnector(s: Socket) : Runnable , ChatHistoryObserver {
    private val printStream = PrintWriter(s.getOutputStream())
    private val scanner1 = Scanner(s.getInputStream())
    override fun newMessage(message: ChatMessage) {
        var messageObjectJson = Json.stringify(ChatMessage.serializer(), message)
        printStream.println(messageObjectJson) // goes to buffer
        printStream.flush() // pushes it from buffer
    }


    override fun run() {

        /**
         * The next block asks the client for an username and checks if it is not in use.
         * ====================================================================
         */
        printStream.println("Insert username")
        printStream.flush()
        var userName: String = scanner1.nextLine()
        while (true) {
            if (userName !in Users.setofUsers) {
                Users.insertUser(userName)
                break;
            } else {
                printStream.println("Username already in use! Try another one.")
                printStream.flush()
                userName = scanner1.nextLine()
            }
        }
        printStream.println("You have entered the chat room.")
        printStream.flush()


        /**
         * This loop continously asks the user for input. If input is provided, it checks
         * if it's a command and issues the proper response. If it is not an command, it
         * adds the message to ChatHistory singleton.
         * ====================================================================
         */
        while (true) {
            val userinput: String = scanner1.nextLine()
            when (userinput) {
                "-history" -> printStream.println(ChatHistory.toString())
                "-users" -> printStream.println(Users.toString())
                else -> {
                    val currentTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                    val formattedTime = currentTime.format(formatter)

                    //creates an object of ChatMessage type of the user input
                    val messageObject = ChatMessage(userinput, formattedTime, userName)
                    ChatHistory.insert(messageObject)
                    //println(messageObjectJson)
                }

            }
        }

    }
}

