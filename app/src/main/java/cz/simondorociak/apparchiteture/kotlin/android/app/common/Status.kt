package cz.simondorociak.apparchiteture.kotlin.android.app.common

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
enum class Status {

    LOADING, SUCCESS, ERROR, OFFLINE;

    fun isSuccessful() = this == SUCCESS

    fun isLoading() = this == LOADING

    fun isError() = this == ERROR
}