package com.aruistar.shengmu.http

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.pgclient.PgPool

abstract class HttpEndpoint {

    Router router
    Vertx vertx

    HttpEndpoint(Vertx vertx) {
        this.vertx = vertx
        this.router = Router.router(vertx)
        initRoutes()
    }

    abstract void initRoutes()
}
