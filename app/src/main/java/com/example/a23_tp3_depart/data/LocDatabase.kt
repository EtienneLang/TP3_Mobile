package com.example.a23_tp3_depart.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.a23_tp3_depart.model.Locat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * AppDatabase est une classe abstraite qui hérite de RoomDatabase
 * Elle contient une méthode abstraite qui retourne un objet de type UserDao
 * Room va générer le code nécessaire pour implémenter cette classe
 */
// Noter que entities reçoit une liste : une BD peut avoir plus d'une table...
@Database(entities = [Locat::class], version = 1)
abstract class LocDatabase : RoomDatabase() {
    // Méthode abstraite qui retourne un objet de type UserDao
    abstract fun locDao(): LocDao

    // Singleton
    companion object {
        // @Volatile permet de garantir que les modifications sur cette variable
        // sont immédiatement visibles par tous les threads
        @Volatile
        // INSTANCE est un singleton, c'est-à-dire qu'il n'y a qu'une seule instance
        // de cette variable dans l'application
        private var INSTANCE: LocDatabase? = null

        // Méthode qui retourne l'instance de la base de données
        // Si la base de données n'existe pas, elle est créée
        // Cette méthode est thread-safe (synchronized), c'est-à-dire qu'elle peut être appelée
        // par plusieurs threads en même temps
        fun getInstance(context: Context): LocDatabase {
            // Si l'instance n'existe pas, on la crée
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    LocDatabase::class.java,
                    "database"
                )
                    .build()

                // On assigne l'instance à la variable INSTANCE
                INSTANCE = instance
                // On retourne l'instance
                instance
            }
        }
    }
}


