import java.util.*
fun main() {
    val alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЫЭЮЯ"
    val tableSize = 20
    val random = Random()

    // Запрашиваем исходное сообщение и вспомогательный символ
    println("Введите исходное сообщение:")
    val inputMessage = readLine()!!.toUpperCase().replace(" ", "")

    println("Введите вспомогательный символ:")
    val auxiliaryChar = readLine()!!.toUpperCase()

    // Запрашиваем тип таблицы
    println("Выберите тип таблицы (1 - типовая, 2 - случайная):")
    val tableType = readLine()!!.toInt()

    // Генерируем таблицу
    val table = when (tableType) {
        1 -> createStandardTable(alphabet)
        2 -> createRandomTable(alphabet, random)
        else -> throw IllegalArgumentException("Неверный выбор типа таблицы.")
    }
    // Подготовка сообщения
    val preparedMessage = prepareMessage(inputMessage, auxiliaryChar)
    println("Подготовленное сообщение: $preparedMessage")
    // Шифрование
    val encryptedMessage = encryptMessage(preparedMessage, table)

    // Вывод результатов
    println("Зашифрованное сообщение: $encryptedMessage")
    println("Шифровальная таблица:")
    printTable(table)
}
fun createStandardTable(alphabet: String): Array<IntArray> {
    val table = Array(20) { IntArray(20) }
    var number = 0
    for (i in 0 until 20) {
        for (j in 0 until 20) {
            if (number < 999) {
                table[i][j] = number++
            }
        }
    }
    return table
}
fun createRandomTable(alphabet: String, random: Random): Array<IntArray> {
    val table = Array(20) { IntArray(20) }
    val usedNumbers = mutableSetOf<Int>()
    var number: Int
    for (i in 0 until 20) {
        for (j in 0 until 20) {
            do {
                number = random.nextInt(1000)
            } while (usedNumbers.contains(number))
            usedNumbers.add(number)
            table[i][j] = number
        }
    }
    return table
}
fun prepareMessage(message: String, auxiliaryChar: String): String {
    val sb = StringBuilder(message)
    if (sb.length % 2 != 0) {
        sb.append(auxiliaryChar)
    }
    return sb.toString()
}
fun encryptMessage(message: String, table: Array<IntArray>): String {
    val encrypted = StringBuilder()
    for (i in message.indices step 2) {
        val rowChar = message[i]
        val colChar = message[i + 1]
        val row = (rowChar - 'А') % 20
        val col = (colChar - 'А') % 20
        val number = table[row][col]
        encrypted.append(String.format("%03d", number)).append(" ")
    }
    return encrypted.toString().trim()
}
fun printTable(table: Array<IntArray>) {
    for (row in table) {
        for (number in row) {
            print(String.format("%03d ", number))
        }
        println()
    }
}
