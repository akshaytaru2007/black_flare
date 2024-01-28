package com.movieapppoc.movielist.util.exceptions

data class ServerErrorException(val errorCode: Int) : Throwable()
data class ClientErrorException(val errorCode: Int) : Throwable()

