import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def file = new File("/Users/liurui/develop/workspace/shengmu/src/test/resources/geo.json")

def map = new JsonSlurper().parse(file)

println(map)
def list = map.features.collect {
    def arr = it.geometry.coordinates
    def lon = arr[0]
    def lat = arr[1]
    [
            lon: lon,
            lat: lat
    ]
}

int i = 1
list.each {
    it.distance = i * 1000
    i++
}

println(JsonOutput.toJson(list))


