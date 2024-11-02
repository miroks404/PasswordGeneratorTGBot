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
    }
    catch (e: Exception) {
        println("Следует вводить только числа!")
        return main()
    }

    val password = getPassword(passwordLength)

    println(password)
}

fun getLength() : Int {
    val length = readln().toInt()
    if (length < 6) throw Error()
    if (length > 200) throw ArithmeticException()
    return length
}

fun getPassword(length: Int) : String {
    val allChars: List<Char> = ('A'..'Z') + ('a'..'z') + ('0'..'9') + ('!'..'~')

    var password = ""

    for (i in 1..length) {
        password += allChars.random()
    }

    return password
}