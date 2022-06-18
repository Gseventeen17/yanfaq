import dayjs from 'dayjs'

// 引入中文语言包
import 'dayjs/locale/zh-cn'

// 引入插件 dayjs/plugin/relativeTime
// 在你npm i daysj时，插件已经下载了。
// 具备计算相对时间的功能
import rTime from 'dayjs/plugin/relativeTime'

// 使用中文语言包。固定格式
dayjs.locale('zh-cn')

// 使用插件。固定格式dayjs.extend(插件)
dayjs.extend(rTime)

export const relativeTime = function (yourTime) {
    // 使用dayjs提供的api对用户传入的时间 yourTime
    // 进行格式化，以返回一个相对时间
    return dayjs().to(dayjs(yourTime))
    // console.log(yourTime)
    // return dayjs(yourTime).format('DD/MM/YYYY')
}