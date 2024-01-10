package tech.youko.acms.util

import org.springframework.data.domain.Sort

fun commaSeparatedStringToSort(orderString: String): Sort {
    if (orderString.isEmpty()) {
        return Sort.unsorted()
    }
    val orderList = orderString.split(',')
    if (orderList.size.and(1) != 0) { // 排序列表长度必须为偶数
        throw IllegalArgumentException("The size of the sort list must be even")
    }
    return Sort.by(List(orderList.size.shr(1)) { // 除以2, 处理排序列表
        val propertyIndex = it.shl(1) // 属性索引
        val orderIndex = propertyIndex.plus(1) // 排序索引
        when (orderList[orderIndex]) {
            "asc" -> Sort.Order.asc(orderList[propertyIndex])
            "desc" -> Sort.Order.desc(orderList[propertyIndex])
            else -> throw IllegalArgumentException("The sort order must be 'asc' or 'desc'")
        }
    })
}
