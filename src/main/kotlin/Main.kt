package org.example


fun main() {
    val passwordLength: Int
    try {
        print("Введите длину пароля (минимум 6 символов, максимум 200 символов): ")
        passwordLength = getLength()
    } catch (e: Error) {
        println("Минимум 6 символов!")
        return main()
    } catch (e: ArithmeticException) {
        println("Максимум 200 символов!")
        return main()
    } catch (e: Exception) {
        println("Следует вводить только числа!")
        return main()
    }

    var numberOfGeneratedPasswords: Int

    while (true) {
        try {
            print("Сколько паролей нужно сгенерировать? (минимум 1, максимум 100): ")
            numberOfGeneratedPasswords = getNumberOfGeneratedPasswords()
            break
        } catch (e: Exception) {
            println("Следует вводить только целые числа!")
        } catch (e: Error) {
            println("Меньше 1 пароля не генерируется!")
        } catch (e: ArithmeticException) {
            println("Больше 100 паролей не генерируется!")
        }
    }

    val password = getPassword(passwordLength, numberOfGeneratedPasswords)

    println(password)
}

fun getLength(): Int {
    val length = readln().toInt()
    if (length < MINIMUM_LENGTH_OF_PASSWORD) throw Error()
    if (length > MAXIMUM_LENGTH_OF_PASSWORD) throw ArithmeticException()
    return length
}

fun getNumberOfGeneratedPasswords(): Int {
    val numberOfGeneratedPasswords = readln().toInt()
    if (numberOfGeneratedPasswords < MINIMUM_COUNT_OF_GENERATED_PASSWORDS) throw Error()
    if (numberOfGeneratedPasswords > MAXIMUM_COUNT_OF_GENERATED_PASSWORDS) throw ArithmeticException()
    return numberOfGeneratedPasswords
}

fun getPassword(length: Int, numberOfGeneratedPasswords: Int): String {
    val allChars: List<Char> = ('A'..'Z') + ('a'..'z') + ('0'..'9') + ('!'..'~')

    val listOfPasswords: MutableList<String> = mutableListOf()

    for (count in 1..numberOfGeneratedPasswords) {

        var password = ""

        for (i in 1..length) {
            password += allChars.random()
        }

        listOfPasswords.add(password)
    }

    return listOfPasswords.joinToString("\n")
}

const val MINIMUM_LENGTH_OF_PASSWORD = 6
const val MAXIMUM_LENGTH_OF_PASSWORD = 200

const val MINIMUM_COUNT_OF_GENERATED_PASSWORDS = 1
const val MAXIMUM_COUNT_OF_GENERATED_PASSWORDS = 100
