package tour.donnees.journal.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tour.donnees.journal.core.shared.JournalSharedPreference
import tour.donnees.journal.core.shared.JournalSharedPreferenceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class BaseModule {

    @Binds
    abstract fun provideJournalSharedPreference(journalSharedPreferenceImpl: JournalSharedPreferenceImpl): JournalSharedPreference

}