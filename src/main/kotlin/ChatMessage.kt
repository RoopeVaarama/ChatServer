import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

public class ChatMessage(var message: String = ""){
    val currentTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    val formattedTime = currentTime.format(formatter)
}