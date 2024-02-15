package tech.youko.acms.structure

import org.springframework.data.domain.Page

data class PageResponseStructure<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalElements: Long
) {
    companion object {
        fun <T> fromPage(page: Page<T>): PageResponseStructure<T> {
            return PageResponseStructure(
                page.content,
                page.totalPages,
                page.totalElements
            )
        }
    }
}
