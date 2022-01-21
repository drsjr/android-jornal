package tour.donnees.journal

import android.app.Application
//import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.internal.managers.ApplicationComponentManager

@HiltAndroidApp
class JournalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        //FirebaseApp.initializeApp(this)
    }
}