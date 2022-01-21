package tour.donnees.journal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import tour.donnees.journal.data.local.cache.*
import tour.donnees.journal.data.local.dao.*

@Database(entities = [
    NewsCache::class,
    CategoryCache::class,
    ParagraphCache::class,
    FrontPageCache::class,
    FrontPageWithNewsCache::class,
    UserInfoCache::class], version = 1, exportSchema = false)
abstract class JournalDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun categoryDao(): CategoryDao
    abstract fun paragraphDao(): ParagraphDao
    abstract fun frontPageDao(): FrontPageDao
    abstract fun userInfoDao(): UserInfoDao
    abstract fun newsFromFrontPageDao(): FrontPageWithNewsDao

    companion object {
        const val DATABASE_NAME = "journal"
    }
}