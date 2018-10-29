package cz.simondorociak.apparchiteture.kotlin.android.app.database.dao

import android.arch.lifecycle.LiveData

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
interface IDao<T, K> {

    fun create(entity: T)
    fun readById(id: K) : LiveData<T>
    fun update(entity: T)
    fun delete(entity: T)
}