package tech.central.showcase.post.model

sealed class SortType {
    object NONE : SortType()
    object ASC : SortType()
    object DESC : SortType()
}