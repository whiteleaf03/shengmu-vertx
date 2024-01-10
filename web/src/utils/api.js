import axios from "axios";
const uri = 'http://127.0.0.1:8080'
// const uri = ''
/**
 * 车速图
 * @param vm
 * @param model
 * @param date
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
const getDetail = (vm, model, date) => {
    debugger
    return axios.get(`${uri}/api/detail?abModel=${model}&time=${date}`)
}
/**
 * 车流密度图
 * @param vm
 * @param model
 * @param date
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
const getGroup = (vm, model, date) => {
    return axios.get(`${uri}/api/group?abModel=${model}&time=${date}`)
}
/**
 * 速度分布图
 * @param vm
 * @param model
 * @param date
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
const getSpeedSummary = (vm, model, date) => {
    return axios.get(`${uri}/api/speedSummary?abModel=${model}&time=${date}`)
}
/**
 * 获取地图数据
 * @param vm
 * @param model
 * @param date
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
const getGeoMapData = (vm,model,date) => {
    console.log(`${uri}/api/geoGroup?abModel=${model}&time=${date}`)
    return axios.get(`${uri}/api/geoGroup?abModel=${model}&time=${date}`)
}
export default {
    getDetail,
    getGroup,
    getSpeedSummary,
    getGeoMapData
}
