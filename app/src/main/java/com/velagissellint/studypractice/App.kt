package com.velagissellint.studypractice

import android.app.Application
import androidx.room.Room
import com.velagissellint.data.LoginRepositoryImpl
import com.velagissellint.data.UserRepositoryImpl
import com.velagissellint.data.db.AppDatabase
import com.velagissellint.data.db.UserDao
import com.velagissellint.data.firebase.ChartRepositoryFb
import com.velagissellint.data.firebase.DocumentRepositoryFb
import com.velagissellint.data.firebase.ProfileRepositoryFb
import com.velagissellint.data.firebase.StatementRepositoryFb
import com.velagissellint.data.firebase.TaskRepositoryFb
import com.velagissellint.data.firebase.TeamRepositoryFb
import com.velagissellint.data.firebase.UserRepositoryFb
import com.velagissellint.domain.useCases.chart.ChartRepository
import com.velagissellint.domain.useCases.chart.ChartUseCase
import com.velagissellint.domain.useCases.document.DocumentRepository
import com.velagissellint.domain.useCases.document.DocumentUseCase
import com.velagissellint.domain.useCases.logIn.LogInRepository
import com.velagissellint.domain.useCases.logIn.LogInUseCase
import com.velagissellint.domain.useCases.statement.StatementRepository
import com.velagissellint.domain.useCases.statement.StatementUseCase
import com.velagissellint.domain.useCases.task.TaskRepository
import com.velagissellint.domain.useCases.task.TaskUseCase
import com.velagissellint.domain.useCases.team.TeamRepository
import com.velagissellint.domain.useCases.team.TeamUseCase
import com.velagissellint.domain.useCases.user.ProfileRepository
import com.velagissellint.domain.useCases.user.ProfileUseCase
import com.velagissellint.domain.useCases.user.UserRepository
import com.velagissellint.domain.useCases.user.UserUseCase
import com.velagissellint.presentation.diplom.Main2ViewModel
import com.velagissellint.presentation.diplom.add_task.AddTaskViewModel
import com.velagissellint.presentation.diplom.admin.add_profile.AddProfileViewModel
import com.velagissellint.presentation.diplom.admin.check_profile.AdminProfileViewModel
import com.velagissellint.presentation.diplom.admin.search_admin.SearchProfileViewModel
import com.velagissellint.presentation.diplom.document.DocumentViewModel
import com.velagissellint.presentation.diplom.document_list.DocumentationsViewModel
import com.velagissellint.presentation.diplom.logIn.LogInViewModel
import com.velagissellint.presentation.diplom.profile.ProfileViewModel
import com.velagissellint.presentation.diplom.task.TaskViewModel
import com.velagissellint.presentation.diplom.task_list.TaskListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun provideDataBase(application: Application): AppDatabase =
    Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        AppDatabase.DB_NAME
    ).allowMainThreadQueries().build()

fun provideDao(postDataBase: AppDatabase): UserDao = postDataBase.resultDao()

val appModule = module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }

    single { UserRepositoryFb(androidContext()) }

    single<UserRepository> {
        UserRepositoryImpl(get())
    }

    single<ProfileRepository> {
        ProfileRepositoryFb()
    }

    single {
        UserUseCase(get())
    }

    single<LogInRepository> {
        LoginRepositoryImpl(get(), get())
    }

    single<StatementRepository> {
        StatementRepositoryFb()
    }

    single<TeamRepository> {
        TeamRepositoryFb()
    }

    single<ChartRepository> {
        ChartRepositoryFb()
    }

    single<TaskRepository> {
        TaskRepositoryFb()
    }

    single<DocumentRepository> {
        DocumentRepositoryFb()
    }

    single {
        DocumentUseCase(get())
    }

    single {
        TeamUseCase(get())
    }

    single {
        ProfileUseCase(get(), get(), get())
    }

    single {
        LogInUseCase(get())
    }

    single {
        StatementUseCase(get())
    }

    single {
        ChartUseCase(get())
    }

    single {
        TaskUseCase(get())
    }

    /*   viewModel { ( vehicleId: String?, vehicleIdentifier: String?) ->
        VehicleAlertViewModel(
            vehicleId = vehicleId,
            vehicleIdentifier = vehicleIdentifier,
            interactor = get()
        )
    }*/

    viewModel {
        LogInViewModel(get())
    }
    viewModel {
        ProfileViewModel(get(), get(), get(), get())
    }
    viewModel {
        Main2ViewModel(get())
    }
    viewModel {
        TaskListViewModel(get(), get(), get())
    }
    viewModel {
        AdminProfileViewModel(get(), get(), get())
    }
    viewModel {
        SearchProfileViewModel(get())
    }
    viewModel {
        AddTaskViewModel(get(), get())
    }
    viewModel {
        TaskViewModel(get(), get(), get())
    }

    viewModel {
        DocumentationsViewModel(get(), get(), get())
    }

    viewModel{
        DocumentViewModel(get(), get())
    }

    viewModel {
        AddProfileViewModel(get(), get())
    }
}

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}
