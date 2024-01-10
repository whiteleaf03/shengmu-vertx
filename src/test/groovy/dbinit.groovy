import groovy.sql.Sql

import java.time.LocalDateTime

def db = Sql.newInstance("jdbc:postgresql://104.243.16.117:54321/shengmu", "postgres", "sm2023", "org.postgresql.Driver")
println(db.firstRow("select 1"))

def start = LocalDateTime.of(2023, 10, 24, 0, 0)


db.withBatch(500, "insert into sm_log (t_create, an_distance, an_speed,an_distance2,an_speed2) values (?.t_create,?.an_distance,?.an_speed,?.an_distance2,?.an_speed2);", { st ->
    (24 * 3600).times {
        def time = start.plusSeconds(it)

        def carCount = new Random().nextInt(25) + 10
        def distanceList = []
        def speedList = []

        carCount.times {
            distanceList << new Random().nextFloat(13).round(2) * 1000
            speedList << new Random().nextFloat(150).round(2)
        }

        def carCount2 = new Random().nextInt(25) + 10
        def distanceList2 = []
        def speedList2 = []

        carCount2.times {
            distanceList2 << new Random().nextFloat(13).round(2) * 1000
            speedList2 << new Random().nextFloat(150).round(2)
        }


        def item = [
                t_create    : time,
                an_distance : db.connection.createArrayOf("numeric", distanceList as Float[]),
                an_speed    : db.connection.createArrayOf("numeric", speedList as Float[]),
                an_distance2: db.connection.createArrayOf("numeric", distanceList2 as Float[]),
                an_speed2   : db.connection.createArrayOf("numeric", speedList2 as Float[]),
        ]

        st.addBatch(item)
    }
})




