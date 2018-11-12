package cz.simondorociak.apparchiteture.kotlin.android.app.api.response

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
open class BaseResponse(
    val success: Boolean,
    val error: ErrorBody?
)


class ErrorBody(
    val code: Int,
    val message: String?
)
