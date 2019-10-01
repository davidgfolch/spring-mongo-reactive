package com.dgf.casumotest

import com.dgf.casumotest.service.RentService
import mu.KotlinLogging
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableMongoAuditing
class App : ApplicationContextAware {

    override fun setApplicationContext(ctx: ApplicationContext) {
        ctx.getBean(RentService::class.java).initData()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(App::class.java, *args)
        }

        @JvmStatic
        fun logger() = KotlinLogging.logger {}

    }

}
