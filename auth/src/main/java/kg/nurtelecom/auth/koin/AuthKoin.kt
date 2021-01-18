package kg.nurtelecom.auth.koin

import kg.nurtelecom.auth.repository.AuthRepository
import org.koin.dsl.module

val authKoin = module {
    single { AuthRepository(get(), get(), get()) }
}