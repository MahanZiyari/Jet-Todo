package com.mahan.compose.jettodo.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mahan.compose.jettodo.data.models.Priority
import com.mahan.compose.jettodo.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = Constants.PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    private object Keys{
        val sortKey = stringPreferencesKey(name = Constants.PREFERENCE_KEY)
    }

    private val dataStore = context.dataStore

    suspend fun persistSortState(priority: Priority) {
        dataStore.edit {
            it[Keys.sortKey] = priority.name
        }
    }


    val readSortState: Flow<String> = dataStore.data.catch { exception ->
        if (exception is IOException)
            emit(emptyPreferences())
        else
            throw exception
    }.map {
        val sortState = it[Keys.sortKey] ?: Priority.None.name
        sortState
    }
}