# Password Generator Telegram Bot

Этот Telegram-бот предназначен для генерации случайных паролей по заданной длине и количеству. Он поддерживает генерацию паролей, состоящих из букв (верхний и нижний регистр), цифр и специальных символов.

## Описание

Бот запрашивает у пользователя два параметра:

1. **Длина пароля** — от 6 до 200 символов.
2. **Количество паролей** — от 1 до 100.

После получения этих данных бот генерирует случайные пароли и отправляет их пользователю.

## Возможности

- Генерация паролей заданной длины.
- Генерация нескольких паролей за один раз.
- Пароли включают буквы (верхний и нижний регистр), цифры и специальные символы.

## Стек технологий

- Kotlin
- Telegram Bot API
- Gradle

## Установка и запуск

### 1. Клонирование репозитория

Для начала клонируйте репозиторий:

```bash
git clone https://github.com/your-username/password-generator-bot.git
cd password-generator-bot
```

### 2. Настройка бота

Создайте нового бота через **@BotFather** в Telegram.

Замените следующие строки в коде вашего бота (в файле PasswordGeneratorBot.kt):

```
override fun getBotUsername(): String {
    return "YourBotUsername"  // Замените на имя вашего бота
}

override fun getBotToken(): String {
    return "YourBotToken"  // Замените на токен вашего бота
}
```

### 3. Сборка проекта

Убедитесь, что у вас установлен Gradle, а затем выполните команду для сборки проекта:

```bash
./gradlew build
```

### 4. Запуск бота

После успешной сборки, вы можете запустить бот с помощью команды:

```bash
java -jar build/libs/password-generator-bot.jar
```

После запуска бот начнет работать и будет ожидать команды от пользователей.

### Как работает бот?

Пользователь начинает чат с ботом, отправляя команду /start.

Бот запрашивает у пользователя длину пароля (от 6 до 200 символов).

Затем бот запрашивает количество паролей (от 1 до 100).

После получения параметров бот генерирует пароли и отправляет их обратно пользователю.

--------------------------------------------------------------------------------------

# MIT License

```
Copyright (c) 2019-present Fenny and Contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

