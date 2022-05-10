package com.nor35.photos.domain

object Constants {

    const val BASE_URL: String = "https://loremflickr.com/"

    const val NUMBER_OF_ROWS: Int = 10
    const val NUMBER_OF_COLUMNS: Int = 7
    const val NUMBER_OF_PHOTOS_ON_PAGE: Int = NUMBER_OF_ROWS * NUMBER_OF_COLUMNS * 2

    const val PHOTO_DATABASE_NAME: String = "photo_database"

    const val DELAY_WHEN_ADDING_ONE_PICTURE = 1000L

    const val MY_NETWORK_TIMEOUT = 5L

    const val PHOTO_ID_ARRAY = "PHOTO_ID_ARRAY"

    const val MEOW_NOTIFICATION_TAG = "MEOW_NOTIFICATION_TAG"
    const val MEOW_ACTION = "com.nor35.action.SEND_DATA"
}
