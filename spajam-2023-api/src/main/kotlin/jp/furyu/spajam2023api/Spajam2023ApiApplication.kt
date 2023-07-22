package jp.furyu.spajam2023api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Spajam2023ApiApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<Spajam2023ApiApplication>(*args)
        }
    }
}
