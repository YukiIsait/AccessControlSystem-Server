package tech.youko.acms.structure.response

import org.springframework.data.domain.Page

data class PageStructure<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalElements: Long
) {
    companion object {
        fun <T> fromPage(page: Page<T>): PageStructure<T> {
            return PageStructure(
                page.content,
                page.totalPages,
                page.totalElements
            )
        }
    }
}
