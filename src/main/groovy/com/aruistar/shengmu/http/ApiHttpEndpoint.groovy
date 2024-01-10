package com.aruistar.shengmu.http

import com.aruistar.shengmu.domain.CarItem
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import io.vertx.core.Future
import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.ext.web.RoutingContext
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Tuple
import io.vertx.sqlclient.data.Numeric

import java.time.LocalDateTime

@Slf4j
class ApiHttpEndpoint extends HttpEndpoint {

    PgPool pgPool

    static int TOTAL_DISTANCE = 13

    static String GEO_POINTS_JSON = """
[
    {
        "lng": 121.33859297,
        "lat": 29.69236637,
        "distance": 65.5886611900546
    },
    {
        "lng": 121.33797089,
        "lat": 29.69212995,
        "distance": 186.73285493110313
    },
    {
        "lng": 121.33837913,
        "lat": 29.69109981,
        "distance": 260.06323266513004
    },
    {
        "lng": 121.33834024,
        "lat": 29.6904412,
        "distance": 368.43329575726773
    },
    {
        "lng": 121.33765013,
        "lat": 29.68967281,
        "distance": 476.5091450971422
    },
    {
        "lng": 121.33678507,
        "lat": 29.68905642,
        "distance": 716.814632793693
    },
    {
        "lng": 121.33450918,
        "lat": 29.68818385,
        "distance": 877.1556996168317
    },
    {
        "lng": 121.33299005,
        "lat": 29.68760278,
        "distance": 1040.839661311001
    },
    {
        "lng": 121.33144825,
        "lat": 29.68699216,
        "distance": 1227.9500728017897
    },
    {
        "lng": 121.32970239,
        "lat": 29.68626336,
        "distance": 1384.645806402205
    },
    {
        "lng": 121.32825577,
        "lat": 29.68562586,
        "distance": 1545.3311802291762
    },
    {
        "lng": 121.32676799,
        "lat": 29.68497961,
        "distance": 1730.5812905520174
    },
    {
        "lng": 121.32511749,
        "lat": 29.68413139,
        "distance": 1932.9624610105386
    },
    {
        "lng": 121.32331949,
        "lat": 29.68319726,
        "distance": 2132.9480244577844
    },
    {
        "lng": 121.3215821,
        "lat": 29.68221934,
        "distance": 2357.559243255147
    },
    {
        "lng": 121.31957555,
        "lat": 29.68119889,
        "distance": 2588.3689564505307
    },
    {
        "lng": 121.317569,
        "lat": 29.68007214,
        "distance": 2768.7922495227267
    },
    {
        "lng": 121.31597844,
        "lat": 29.67922175,
        "distance": 2958.53089364385
    },
    {
        "lng": 121.31429001,
        "lat": 29.6783501,
        "distance": 3178.738776033586
    },
    {
        "lng": 121.31230795,
        "lat": 29.67737214,
        "distance": 3397.842109613059
    },
    {
        "lng": 121.31033918,
        "lat": 29.67639401,
        "distance": 3631.476506046322
    },
    {
        "lng": 121.30820484,
        "lat": 29.67540612,
        "distance": 3969.6487199665075
    },
    {
        "lng": 121.30504827,
        "lat": 29.67409179,
        "distance": 4265.082835714929
    },
    {
        "lng": 121.30227228,
        "lat": 29.6729775,
        "distance": 4647.656548409946
    },
    {
        "lng": 121.29865512,
        "lat": 29.67157749,
        "distance": 5105.523782645141
    },
    {
        "lng": 121.29433223,
        "lat": 29.66989012,
        "distance": 5622.0043630002565
    },
    {
        "lng": 121.28946344,
        "lat": 29.66797249,
        "distance": 6056.690371859828
    },
    {
        "lng": 121.28535136,
        "lat": 29.6663865,
        "distance": 6370.495356219478
    },
    {
        "lng": 121.28222535,
        "lat": 29.66562085,
        "distance": 6648.325150321608
    },
    {
        "lng": 121.2793511,
        "lat": 29.66554793,
        "distance": 7008.674938799253
    },
    {
        "lng": 121.27565862,
        "lat": 29.66600368,
        "distance": 7289.38571667313
    },
    {
        "lng": 121.27275448,
        "lat": 29.66607518,
        "distance": 7703.992784737371
    },
    {
        "lng": 121.26850437,
        "lat": 29.66556105,
        "distance": 8021.587342509299
    },
    {
        "lng": 121.26526175,
        "lat": 29.66509317,
        "distance": 8358.187760964925
    },
    {
        "lng": 121.26183071,
        "lat": 29.66456877,
        "distance": 8627.397197586462
    },
    {
        "lng": 121.25924186,
        "lat": 29.66367378,
        "distance": 8849.70369409275
    },
    {
        "lng": 121.25732111,
        "lat": 29.66257318,
        "distance": 9062.271339260815
    },
    {
        "lng": 121.25570981,
        "lat": 29.66127163,
        "distance": 9259.064768089473
    },
    {
        "lng": 121.25452263,
        "lat": 29.65983359,
        "distance": 9566.885281126526
    },
    {
        "lng": 121.25311962,
        "lat": 29.65734823,
        "distance": 9863.214749393655
    },
    {
        "lng": 121.25184252,
        "lat": 29.65492536,
        "distance": 10257.215013883599
    },
    {
        "lng": 121.2500922,
        "lat": 29.65172513,
        "distance": 10671.234245467822
    },
    {
        "lng": 121.24827581,
        "lat": 29.64835295,
        "distance": 11032.167177007992
    },
    {
        "lng": 121.24672709,
        "lat": 29.64539922,
        "distance": 11361.349964492758
    },
    {
        "lng": 121.24513534,
        "lat": 29.64278193,
        "distance": 11727.080174104793
    },
    {
        "lng": 121.24268319,
        "lat": 29.64027676,
        "distance": 12154.497670283297
    },
    {
        "lng": 121.23915554,
        "lat": 29.63795849,
        "distance": 12661.035682079495
    },
    {
        "lng": 121.23489656,
        "lat": 29.63530363,
        "distance": 12745.892965675834
    },
    {
        "lng": 121.23404426,
        "lat": 29.63512038,
        "distance": 12808.216775816249
    },
    {
        "lng": 121.23339986,
        "lat": 29.63514113,
        "distance": 12843.747447197944
    },
    {
        "lng": 121.23318505,
        "lat": 29.63540044,
        "distance": 12882.978397827872
    },
    {
        "lng": 121.23319698,
        "lat": 29.6357531,
        "distance": 12927.12276079783
    },
    {
        "lng": 121.23344758,
        "lat": 29.63608501,
        "distance": 13050.335674822754
    }
]
"""

    ApiHttpEndpoint(Vertx vertx, PgPool pgPool) {
        super(vertx)
        this.pgPool = pgPool
    }

    @Override
    void initRoutes() {
        router.get()
                .produces("application/json")
                .handler(this::dataBuilder)

        router.get("/speedSummary").handler(this::speedSummary)
        router.get("/detail").handler(this::detail)
        router.get("/group").handler(this::group)
        router.get("/geoGroup").handler(this::geoGroup)
    }

    static def speedSummary(RoutingContext ctx) {
        List<CarItem> carList = ctx.get("data")

        def result = [
                [label: "大于130", count: 0, pct: 0],
                [label: "120-130", count: 0, pct: 0],
                [label: "100-120", count: 0, pct: 0],
                [label: "80-100", count: 0, pct: 0],
                [label: "60-80", count: 0, pct: 0],
                [label: "小于60", count: 0, pct: 0],
                [label: "速度0", count: 0, pct: 0],
        ]

        carList.each {
            if (it.speed >= 130) {
                result[0].count++
            } else if (it.speed >= 120) {
                result[1].count++
            } else if (it.speed >= 100) {
                result[2].count++
            } else if (it.speed >= 80) {
                result[3].count++
            } else if (it.speed >= 60) {
                result[4].count++
            } else if (it.speed > 0) {
                result[5].count++
            } else {
                result[6].count++
            }
        }

        def all = carList.size()
        result.each {
            it.pct = (it.count / all).round(2)
        }

        ctx.response()
                .end(JsonOutput.toJson(result))
    }

    static def detail(RoutingContext ctx) {
        List<CarItem> carList = ctx.get("data")
        ctx.response()
                .end(JsonOutput.toJson(carList))
    }

    static def group(RoutingContext ctx) {
        List<CarItem> carList = ctx.get("data")

        def points = []

        TOTAL_DISTANCE.times {
            points << [
                    no      : it,
                    count   : 0,
                    avgSpeed: 0,
                    speeds  : []
            ]
        }

        carList.each {
            def p = points[it.distance.intValue() / 1000]
            if (p) {
                p.count++
                p.speeds << it.speed
            }

        }

        def all = carList.size()
        points.each {
            if (it.speeds.size() > 0) {
                it.avgSpeed = (it.speeds as BigDecimal[]).average().round(2)
            }

            (it as LinkedHashMap).remove("speeds")
        }

        ctx.response()
                .end(JsonOutput.toJson(points))
    }

    static def geoGroup(RoutingContext ctx) {
        List<CarItem> carList = ctx.get("data")

        def geoPoints = new JsonSlurper().parseText(GEO_POINTS_JSON)

        def points = []

        geoPoints.eachWithIndex { it, i ->
            points << [
                    no      : i,
                    count   : 0,
                    avgSpeed: 0,
                    speeds  : [],
                    lng     : it.lng,
                    lat     : it.lat,
                    distance: it.distance
            ]
        }

        carList.each { car ->

            def matchPoint

            def nextPoint = points.find { p ->
                car.distance < p.distance
            }

            if (nextPoint == null) { // car 在最后一个节点
                matchPoint = points.last()
            } else if (nextPoint == points.first()) { // car 在第一个节点
                matchPoint = nextPoint
            } else {
                def beforePoint = points[points.indexOf(nextPoint) - 1]

                if ((car.distance - beforePoint.distance).abs() < (car.distance - nextPoint.distance).abs()) {
                    //说明离前面的点较近
                    matchPoint = beforePoint
                } else {
                    matchPoint = nextPoint
                }
            }

            if (matchPoint) {
                matchPoint.count++
                matchPoint.speeds << car.speed
            }

        }

        def all = carList.size()

        points.each {
            if (it.speeds.size() > 0) {
                it.avgSpeed = (it.speeds as BigDecimal[]).average().round(2)
            }

            it.pct_count = (it.count / all).round(2)
            it.pct_speed = (it.avgSpeed / 130).round(2)

            (it as LinkedHashMap).remove("speeds")
        }

        ctx.response()
                .end(JsonOutput.toJson(points))
    }

    def dataBuilder(RoutingContext ctx) {
        def request = ctx.request()
        def response = ctx.response()
        def time = request.getParam("time") ?: '2023-10-24T10:10'
        def abModel = (request.getParam("abModel") ?: '0').toInteger()

        def localDateTime = LocalDateTime.parse(time)

        findRowByTime(localDateTime, abModel)
                .onSuccess {
                    ctx.put("data", it["carList"])
                    ctx.response()
                            .putHeader("Content-Type", "application/json")
                            .putHeader("time", it["time"] as String)

                    ctx.next()
                }
                .onFailure {
                    it.printStackTrace()
                    response.end("null")
                }


    }

    Future findRowByTime(LocalDateTime time, int abModel) {
        def promise = Promise.promise()

        pgPool.preparedQuery('select * from sm_log where t_create >= $1 order by t_create  limit 1')
                .execute(Tuple.of(time))
                .onSuccess {
                    if (it.size() == 1) {
                        try {


                            def row = it.first()
                            def t_create = row.getLocalDateTime("t_create").toString()
                            List<CarItem> carList = []
                            def an_distance
                            def an_speed
                            if (abModel > 0) { // A-B 方向
                                an_distance = row.getArrayOfNumerics('an_distance')
                                an_speed = row.getArrayOfNumerics('an_speed')
                            } else if (abModel < 0) { // B-A 方向
                                an_distance = row.getArrayOfNumerics('an_distance2')
                                an_speed = row.getArrayOfNumerics('an_speed2')
                            } else { //全向
                                an_distance = row.getArrayOfNumerics('an_distance').toList()
                                an_speed = row.getArrayOfNumerics('an_speed').toList()
                                an_distance.addAll(row.getArrayOfNumerics('an_distance2'))
                                an_speed.addAll(row.getArrayOfNumerics('an_speed2'))
                            }

                            an_distance.eachWithIndex { Numeric entry, int i ->
                                def item = new CarItem()
                                item.distance = entry.bigDecimalValue()
                                item.speed = an_speed[i].bigDecimalValue()
                                carList << item
                            }


                            promise.complete([
                                    time   : t_create,
                                    carList: carList
                            ])
                        } catch (e) {
                            promise.fail(e)
                        }
                    } else {
                        promise.fail("no data")
                    }

                }
                .onFailure {
                    promise.fail(it)
                }

        return promise.future()
    }

}
