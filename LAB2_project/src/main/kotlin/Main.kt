package org.example

import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import java.util.Scanner

val logger = LoggerFactory.getLogger("Main")

fun main() {
    logger.info("Программа запущена")

    println("Введите строку:")
    val scanner = Scanner(System.`in`)
    val input = scanner.nextLine()

    logger.debug("Пользователь ввел: $input")

    val reversed = StringUtils.reverse(input)
    val capitalized = StringUtils.capitalize(input)

    logger.info("Перевернутая строка: $reversed")
    logger.info("Строка с заглавной буквы: $capitalized")

    logger.info("Программа завершена")
    scanner.close()
}