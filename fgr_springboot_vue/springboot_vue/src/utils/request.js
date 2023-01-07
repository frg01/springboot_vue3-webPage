//引入axios
import axios from 'axios'
// 通过 axios 创建对象 用于请求发送到后端
const request = axios.create({
    timeout: 5000
})

// request 拦截器
// 1. 可以对请求做一些处理
// 2. 比如统一加 token，Content-Type 等
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    return config
}, error => {
    return Promise.reject(error)
})
// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {//response是后端返回的结果
        let res = response.data; //取出返回response.data 的内容
// 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
// 如果返回的是 string ,就转成 json 对象
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },error => {
        console.log('err' + error) // 输出错误信息
        return Promise.reject(error)
    }
)

//导出request对象，在其他组件使用
export default request