import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

useCase("speed test") {
    runBlocking {
        coroutineScope {
            val host = "http://localhost:8080"
            val count = 20000

            GET("$host/slow") {
                header("Accept", "application/json")
            }
            GET("$host/fast") {
                header("Accept", "application/json")
            }

            val startFastTime = System.currentTimeMillis()
            repeat (count) {
                launch {
                    GET("$host/fast") {
                        header("Accept", "application/json")
                    }
                }
            }
            yield()
            val stopFastTime = System.currentTimeMillis()
            val startSlow = System.currentTimeMillis()
            repeat (count) {
                launch {
                    GET("$host/slow") {
                        header("Accept", "application/json")
                    }
                }
            }
            yield()
            val stopSlowTime = System.currentTimeMillis()

            val slowTime = stopSlowTime - startSlow
            val fastTime = stopFastTime - startFastTime
            println("fast time = $fastTime")
            println("slow time = $slowTime")
            println("diff time = ${slowTime - fastTime} ms")
        }
    }
}