package tour.donnees.journal.presentation.base

import android.content.Context
import android.content.Intent
import tour.donnees.journal.domain.modal.Category
import tour.donnees.journal.domain.modal.News
import tour.donnees.journal.domain.modal.UserInfo
import tour.donnees.journal.presentation.article.ArticleActivity
import tour.donnees.journal.presentation.login.SignInActivity
import tour.donnees.journal.presentation.main.MainPageActivity
import tour.donnees.journal.presentation.section.SectionPageActivity

object NavigationService {

    const val EXTRA_ARTICLE = "EXTRA_ARTICLE"
    const val EXTRA_COME_FROM = "EXTRA_COME_FROM"
    const val EXTRA_USER_INFO = "EXTRA_USER_INFO"
    const val EXTRA_CATEGORY = "EXTRA_CATEGORY"

    fun launchMainPage(context: Context, userInfo: UserInfo, cameFrom: String) {
        val intent = Intent(context, MainPageActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(EXTRA_USER_INFO, userInfo)
        intent.putExtra(EXTRA_COME_FROM, cameFrom)
        context.startActivity(intent)
    }

    fun launchMainPageFromSplash(context: Context) {
        val intent = Intent(context, MainPageActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun launchLoginFromSplash(context: Context) {
        val intent = Intent(context, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun launchSingOut(context: Context) {
        val intent = Intent(context, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun launchArticle(context: Context, news: News) {
        val intent = Intent(context, ArticleActivity::class.java)
        intent.putExtra(EXTRA_ARTICLE, news)
        context.startActivity(intent)
    }

    fun launchSection(context: Context, category: Category) {
        val intent = Intent(context, SectionPageActivity::class.java)
        intent.putExtra(EXTRA_CATEGORY, category)
        context.startActivity(intent)
    }

}