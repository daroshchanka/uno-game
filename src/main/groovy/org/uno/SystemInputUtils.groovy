package org.uno

import groovy.util.logging.Log4j2

@Log4j2
class SystemInputUtils {

    static String readLine() {
        String s = new BufferedReader(new InputStreamReader(System.in)).readLine()
        log.debug("readLine: $s")
        s
    }

    static String enterIntegerByRule(String errorMessage, Closure rule) {
        String input
        boolean isValid = false
        do {
            input = readLine()
            try {
                rule.call(input)
                isValid = true
            } catch (Throwable ignored) {
                // Throwable caught in order to catch any Exception or Error
                log.info(errorMessage)
                log.debug(ignored)
            }
        } while (!isValid)
        input
    }
}
