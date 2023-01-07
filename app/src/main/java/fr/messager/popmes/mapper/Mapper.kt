package fr.messager.popmes.mapper

interface Mapper<T, V> {
    fun T.mapTo(): V
}

interface ReverseMapper<T, V>: Mapper<T, V> {
    fun V.reverseMapTo(): T
}