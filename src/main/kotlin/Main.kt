import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

// Константы для длины пароля и числа паролей
const val MINIMUM_LENGTH_OF_PASSWORD = 6
const val MAXIMUM_LENGTH_OF_PASSWORD = 50
const val MINIMUM_COUNT_OF_GENERATED_PASSWORDS = 1
const val MAXIMUM_COUNT_OF_GENERATED_PASSWORDS = 50

// Основной класс бота
class PasswordGeneratorBot : TelegramLongPollingBot() {

    private var passwordLength: Int? = null
    private var numberOfPasswords: Int? = null
    private var step: Int = 0 // 0 - ввод длины пароля, 1 - ввод количества паролей

    override fun getBotUsername(): String {
        return "PasswordGeneratorOnKotlinBot" // Замените на имя вашего бота
    }

    override fun getBotToken(): String {
        return "7067214694:AAH5Xw8Yqyc1eIhFUFgq0MTaVn0Uh_vII2M" // Замените на токен вашего бота
    }

    override fun onUpdateReceived(update: Update) {
        val message: Message = update.message ?: return
        val chatId = message.chatId
        val input = message.text.trim()

        // Начальный старт
        if (input.startsWith("/start")) {
            sendMessage(chatId, "Привет! Я помогу тебе сгенерировать пароли.\nВведите длину пароля (минимум 6 символов, максимум 50 символов):")
            step = 0 // Начинаем с ввода длины пароля
            return
        }

        // Обработка ввода длины пароля
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

        // Обработка ввода количества паролей
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

        // Если введено что-то, что не соответствует ни длине пароля, ни количеству паролей
        sendMessage(chatId, "Неверный ввод. Попробуйте снова.")
    }

    private fun sendMessage(chatId: Long, text: String) {
        val message = SendMessage().apply {
            this.chatId = chatId.toString()
            this.text = text
        }

        try {
            execute(message) // Отправка сообщения
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

    // Явное удаление webhook
    override fun clearWebhook() {
        try {
            val deleteWebhook = DeleteWebhook()
            execute(deleteWebhook)  // Очищаем webhook
            println("Webhook очищен.")
        } catch (e: TelegramApiException) {
            e.printStackTrace()
            println("Ошибка при очистке webhook.")
        }
    }
}

// Точка входа
fun main() {
    val bot = PasswordGeneratorBot()

    try {
        // Очистка webhook перед запуском бота
        bot.clearWebhook() // Очистка старого webhook, если он существует

        // Инициализация TelegramBotsApi с использованием сессии по умолчанию
        val botsApi = TelegramBotsApi(DefaultBotSession::class.java)  // Передаем DefaultBotSession
        botsApi.registerBot(bot) // Регистрируем бота
        println("Бот успешно запущен!")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
