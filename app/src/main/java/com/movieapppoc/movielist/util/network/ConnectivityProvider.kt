package com.movieapppoc.movielist.util.network

interface ConnectivityProvider {
    fun isConnected(): Boolean
}