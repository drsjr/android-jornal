package tour.donnees.journal.domain.usecase


import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.core.BaseUseCase
import tour.donnees.journal.domain.mapper.CategoryMapper
import tour.donnees.journal.domain.modal.Category
import tour.donnees.journal.domain.repository.CategoryRepository
import javax.inject.Inject

class GetAllCategoriesUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val mapper: CategoryMapper
): BaseUseCase(), GetAllCategoriesUseCase {
    override fun invoke(token: String): Observable<Result<List<Category>>> {
        return super.invoke(categoryRepository.getAllCategories(token).map { result ->
            result.fold({
                Result.success(mapper.mapFromList(it))
            },{
                Result.failure(it)
            })
        }.onErrorReturn {
            Result.failure(it)
        })
    }
}