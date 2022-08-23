package br.com.crystaldata.forum.mapper

interface Mapper<T, U> {

    fun map(t: T): U
}
