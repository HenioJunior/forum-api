package br.com.crystaldata.forum.exception

import java.lang.RuntimeException

class NotFoundException(message: String?) : RuntimeException(message) {
}