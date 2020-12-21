package com.teamx.storage.storageKoin

import kg.nurtelecom.storage.roomDatabase.RoomDB
import kg.nurtelecom.storage.sharedpref.AppPreferences
import org.koin.dsl.module

val storageKoin = module {
    single { AppPreferences(get()) }
    single { RoomDB.getInstance(get()) }
    single { get<RoomDB>().getDataDao() }
}
