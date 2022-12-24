package com.example.turmurom.database

import androidx.lifecycle.*
import com.example.turmurom.database.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(database: MainDb) : ViewModel() {
    private val excursionDao = database.getExcursionDao()
    private val excursionPhotoDao = database.getExcursionPhotoDao()
    private val routeDao = database.getRouteDao()
    private val markDao = database.getMarkDao()
    private val markPhotoDao = database.getMarkPhotoDao()
    private val guideDao = database.getGuideDao()
    private val scheduleDao = database.getScheduleDao()
    private val registerEntityDao = database.getRegisterEntityDao()


    var guideId = 0

    //val allExcursions: LiveData<List<Excursion>> = excursionDao.getAllExcursions().asLiveData()
    val allCategories: LiveData<List<Category>> = markDao.getAllCategories().asLiveData()

    lateinit var allExcursionPhotosById: List<ExcursionPhoto>
    lateinit var allSchedule: LiveData<List<Schedule>>
    val allRoutes: LiveData<List<Route>> = routeDao.getAllRoutes().asLiveData()
    //val allMarks: LiveData<List<Mark>> = markDao.getAllMarks().asLiveData()
    var allMarksForRoute: MutableList<MarksWithPhotos> = mutableListOf()
    lateinit var allRouteMarksById: List<RouteMarks>
    lateinit var allMarkPhotosById: List<MarkPhoto>
    val allMarksByCategory: MutableLiveData<List<MarksWithPhotos>> = MutableLiveData()
    val allExcursionsWithPhotos: MutableLiveData<List<ExcursionWithPhoto>> = MutableLiveData()
//    lateinit var allExcursionsForGuide: List<ExcursionWithPhoto>
    val allExcursionsForGuide: MutableLiveData<List<ExcursionWithPhoto>> = MutableLiveData()

    val markPhoto : MutableLiveData<MarkPhoto> = MutableLiveData()
    val markById : MutableLiveData<Mark> = MutableLiveData()

    val allGuides: LiveData<List<Guide>> = guideDao.getAll().asLiveData()

    /**
     * Лайв-дата с выбранными категориями. Обновляется в соответствующем методе
     * @see updateChosenCategories()
     */
    val chosenCategoriesLiveData = MutableLiveData<List<Category>>()
    val electedMarks = MutableLiveData<List<ElectedMarks>>()


    val dictOfCategories: MutableMap<Int, String> = mutableMapOf()
    val currentCategories: MutableMap<Int, Boolean> = mutableMapOf()


    lateinit var currentUser: RegisterEntity


    val markId: MutableLiveData<Mark> by lazy {
        MutableLiveData<Mark>()
    }
    val route: MutableLiveData<Route> by lazy {
        MutableLiveData<Route>()
    }
    val excursion: MutableLiveData<Excursion> by lazy {
        MutableLiveData<Excursion>()
    }
    val guide: MutableLiveData<Guide> by lazy {
        MutableLiveData<Guide>()
    }
    val excursionPhoto: MutableLiveData<ExcursionPhoto> by lazy {
        MutableLiveData<ExcursionPhoto>()
    }
//    val markPhoto: MutableLiveData<MarkPhoto> by lazy {
//        MutableLiveData<MarkPhoto>()
//    }
//    val currentUser: MutableLiveData<RegisterEntity> by lazy {
//        MutableLiveData<RegisterEntity>()
//    }

    fun selectGuide(id: Int): Guide? {
        return guideDao.getGuide(id)
    }

    fun selectMarkPhoto(id: Int): MarkPhoto {
        return markPhotoDao.getMarkPhotoById(id)
    }

    fun selectExcursionPhoto(id: Int): ExcursionPhoto {
        //photo = excursionPhotoDao.getExcursionPhotoById(id)
        return excursionPhotoDao.getExcursionPhotoById(id)
    }

    fun addCategory(id: Int, title: String) {
        dictOfCategories[id] = title
    }


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)


    fun selectUserById(id: Int) {
        uiScope.launch {
            currentUser = registerEntityDao.getUserById(id)
        }
    }


    //ПОИСК по достопримечательностям
    //===============================================================================

    val readData = markDao.readData().asLiveData()

    fun searchMark(searchQuery: String): LiveData<List<Mark>> {
        return markDao.searchMark(searchQuery).asLiveData()
    }

    //===============================================================================


    fun getListOfMarksForRoute(id: Int) {
        allMarksForRoute.add(markDao.getMarkById(id))
    }

    /**
     * Обновляем лайв-дату со списком достопремечательностей по категориям
     * @see allMarksByCategory
     */
    fun filterMarksByCategory(userId: Int) {
        viewModelScope.launch {
            val marks = markDao.getAllMarksWithPhotos(userId)
            if (currentCategories.all { !it.value }) {
                allMarksByCategory.value = marks
            } else {
                allMarksByCategory.value = marks.filter { currentCategories[it.mark.categoryId] == true }
            }
        }
    }


    fun getCurrentCategories() {
        for (cat in dictOfCategories) {
            currentCategories[cat.key] = true           //Поправить на фолс
        }
    }


    /**
     * Обновляем лайв-дату со списком избранных достопремечательностей
     * @see electedMarks
     */
    fun getElectedMarks(userId : Int) {
        viewModelScope.launch {
            val elMarks = markDao.getElectedMarksWithPhoto(userId)
            electedMarks.value = elMarks
        }
    }

    /**
     * Обновляем лайв-дату со списком Экскурсий с фотографиями
     * @see allExcursionsWithPhotos
     */
    fun getAllExcursionsWithPhotos() {
        viewModelScope.launch {
            val excursions = excursionDao.getAllExcursionsWithPhotos()
            allExcursionsWithPhotos.value = excursions

        }
    }

//    fun selectExcursionsForGuide(id : Int) : LiveData<List<Excursion>> {
//        return excursionDao.getAllExcursionsForGuide(id).asLiveData()
//    }

//    fun getExcursionsForGuide(id: Int) {
//        viewModelScope.launch {
//            val excursions = excursionDao.getAllExcursionsForGuide(id)
//            allExcursionsForGuide.value = excursions
//        }
//    }

    /**
     * Обновляем лайв-дату со списком Экскурсий с фотографиями
     * @see allExcursionsForGuide
     */
    fun getAllExcursionsForGuide(id: Int) {
        viewModelScope.launch {
            val excursions = excursionDao.getAllExcursionsForGuide(id)
            allExcursionsForGuide.value = excursions

        }
    }











    fun selectExcursionPhotosById(id: Int) {
        allExcursionPhotosById = excursionPhotoDao.getExcursionPhotosById(id)
    }

    fun selectRouteMarksById(id: Int) {
        allRouteMarksById = routeDao.getRouteMarksById(id)
    }

//    fun selectMarkById(id: Int): MarksWithPhotos? {
//        return markDao.getMarkById(id)
//    }

    fun selectMarkPhotosById(id: Int) {
        allMarkPhotosById = markPhotoDao.getMarkPhotosById(id)
    }

    fun selectScheduleForMark(id: Int) {
        allSchedule = scheduleDao.getAllScheduleForMark(id).asLiveData()
    }

    //    fun insertExcursion(excursion: Excursion) = viewModelScope.launch {
//        excursionDao.insertExcursion(excursion)
//    }
    fun insertRoute(route: Route) = viewModelScope.launch {
        routeDao.insertRoute(route)
    }

    fun insertEntity(registerEntity: RegisterEntity) = viewModelScope.launch {
        registerEntityDao.insertEntity(registerEntity)
    }

    fun insertElectedMark(electedMark: UserElected) {
        viewModelScope.launch {
            registerEntityDao.insertElectedMark(electedMark)
        }
    }

    fun deleteElectedMark(markId: Int, userId: Int) {
        viewModelScope.launch {
            registerEntityDao.deleteElectedMark(markId, userId)
        }
    }

    /**
     * Обновляет лайв-дату согласно выбранным категориям
     * @see chosenCategoriesLiveData
     */
    fun updateChosenCategories() {
        chosenCategoriesLiveData.postValue(
            currentCategories.filter { it.value }.mapNotNull { entry ->
                allCategories.value?.firstOrNull { it.id == entry.key }
            }
        )
    }

    class MainViewModelFactory(val database: MainDb) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}