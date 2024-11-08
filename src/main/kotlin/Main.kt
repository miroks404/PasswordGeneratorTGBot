import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

const val MINIMUM_LENGTH_OF_PASSWORD = 6
const val MAXIMUM_LENGTH_OF_PASSWORD = 50
const val MINIMUM_COUNT_OF_GENERATED_PASSWORDS = 1
const val MAXIMUM_COUNT_OF_GENERATED_PASSWORDS = 50

class PasswordGeneratorBot : TelegramLongPollingBot() {

    private var passwordLength: Int? = null
    private var numberOfPasswords: Int? = null
    private var step: Int = 0

    override fun getBotUsername(): String {
        return "ИМЯ_ВАШЕГО_БОТА"
    }

    override fun getBotToken(): String {
        return "ВАШ_ТОКЕН"
    }

    override fun onUpdateReceived(update: Update) {
        val message: Message = update.message ?: return
        val chatId = message.chatId
        val input = message.text.trim()

        // Начальный старт
        if (input.startsWith("/start")) {
            sendMessage(chatId, "Привет! Я помогу тебе сгенерировать пароли.\nВведите длину пароля (минимум 6 символов, максимум 50 символов):")
            step = 0
            return
        }

        if (step == 0) {
            val length = input.toIntOrNull()
            if (length != null && length in MINIMUM_LENGTH_OF_PASSWORD..MAXIMUM_LENGTH_OF_PASSWORD) {
                passwordLength = length
                step = 1 // Переходим ко второму шагу (ввод количества паролей)
                sendMessage(chatId, "Теперь укажи, сколько паролей нужно сгенерировать? (минимум 1, максимум 50):")
            } else {
                sendMessage(chatId, "Длина пароля должна быть от $MINIMUM_LENGTH_OF_PASSWORD до $MAXIMUM_LENGTH_OF_PASSWORD!")
            }
            return
        }

        if (step == 1) {
            val count = input.toIntOrNull()
            if (count != null && count in MINIMUM_COUNT_OF_GENERATED_PASSWORDS..MAXIMUM_COUNT_OF_GENERATED_PASSWORDS) {
                numberOfPasswords = count
                val passwords = generatePasswords(passwordLength!!, numberOfPasswords!!)
                sendMessage(chatId, passwords)
                step = 0 // Сброс шага для нового запроса
                passwordLength = null
                numberOfPasswords = null
            } else {
                sendMessage(chatId, "Количество паролей должно быть от $MINIMUM_COUNT_OF_GENERATED_PASSWORDS до $MAXIMUM_COUNT_OF_GENERATED_PASSWORDS!")
            }
            return
        }

        sendMessage(chatId, "Неверный ввод. Попробуйте снова.")
    }

    private fun sendMessage(chatId: Long, text: String) {
        val message = SendMessage().apply {
            this.chatId = chatId.toString()
            this.text = text
        }

        try {
            execute(message)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

    private fun generatePasswords(length: Int, numberOfGeneratedPasswords: Int): String {
        val allChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + ('!'..'~')

        val listOfPasswords = mutableListOf<String>()

        for (count in 1..numberOfGeneratedPasswords) {
            var password = ""
            for (i in 1..length) {
                password += allChars.random()
            }
            listOfPasswords.add(password)
        }

        return listOfPasswords.joinToString("\n")
    }

    override fun clearWebhook() {
        try {
            val deleteWebhook = DeleteWebhook()
            execute(deleteWebhook)
            println("Webhook очищен.")
        } catch (e: TelegramApiException) {
            e.printStackTrace()
            println("Ошибка при очистке webhook.")
        }
    }
}

fun main() {
    val bot = PasswordGeneratorBot()

    try {
        bot.clearWebhook()

        val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
        botsApi.registerBot(bot)
        println("Бот успешно запущен!")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
