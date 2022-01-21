package tour.donnees.journal.domain.usecase


import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.core.BaseUseCase
import tour.donnees.journal.domain.mapper.CategoryMapper
import tour.donnees.journal.domain.modal.Category
import tour.donnees.journal.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryUseCaseImpl @Inject constructor(
    private val repository: CategoryRepository,
    private val mapper: CategoryMapper
): BaseUseCase(), GetCategoryUseCase {
    override fun invoke(token: String, categoryId: Int): Observable<Result<Category>> {
        return super.invoke(repository.getCategoriesById(token, categoryId).map { result ->
            result.fold({
                Result.success(mapper.mapFrom(it))
            },{
                Result.failure(it)
            })
        }.onErrorReturn {
            Result.failure(it)
        })
    }
}