package com.aruistar.shengmu.http

import groovy.util.logging.Slf4j
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.CorsHandler
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.pgclient.PgConnectOptions
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.PoolOptions

@Slf4j
class HttpVerticle extends AbstractVerticle {

    PgPool pgPool

    @Override
    void start(Promise<Void> startPromise) throws Exception {

        log.info(config().toString())
        def httpConfig = config().getJsonObject("http")
        def dbConfig = config().getJsonObject("db")
        int port = httpConfig.getInteger("port")

        pgPool = initPgPool(dbConfig)

        testDB()

        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx)


        router.route("/api/*")
                .handler(
                        CorsHandler.create()
                                .addOrigin("*")
                                .allowedMethod(HttpMethod.GET)
                )
                .subRouter(new ApiHttpEndpoint(vertx, pgPool).router)

        router.route().handler(StaticHandler.create())

        server.requestHandler(router)
                .listen(port)
                .onComplete {
                    if (it.succeeded()) {
                        log.info("HTTP service run on port " + it.result().actualPort())
                        startPromise.complete()
                    } else {
                        log.error("HTTP service failed to start", it.cause())
                        startPromise.fail(it.cause())
                    }
                }

    }

    PgPool initPgPool(JsonObject dbConfig) {
        PgConnectOptions connectOptions = new PgConnectOptions()
                .setPort(dbConfig.getInteger("port"))
                .setHost(dbConfig.getString("host"))
                .setDatabase(dbConfig.getString("database"))
                .setUser(dbConfig.getString("user"))
                .setPassword(dbConfig.getString("password"))
                .setReconnectAttempts(5)
                .setReconnectInterval(5000)

        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(dbConfig.getInteger("poolSize"))


        return PgPool.pool(vertx, connectOptions, poolOptions);
    }

    void testDB() {
        pgPool.query("select 1")
                .execute()
                .onSuccess {
                    log.info("db connect success.")
                }
                .onFailure {
                    log.info("db connect success.")
                    it.printStackTrace()
                }
    }
}
