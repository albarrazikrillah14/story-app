package com.example.storyapps.utils

import com.example.storyapps.data.model.FileUploadResponse
import com.example.storyapps.data.model.ListStory
import com.example.storyapps.data.model.LoginResponse
import com.example.storyapps.data.model.LoginResult
import com.example.storyapps.data.model.RegisterResponse
import com.example.storyapps.data.model.StoriesResponse

object DataDummy {
    fun generateDummyStoryEntity(): List<ListStory> {
        val storyList = ArrayList<ListStory>()

        for(i in 0..10) {
            val story = ListStory(
                "user-Dummy",
                "Mamat Suharjo",
                "ini cuma deskripsi dummy",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "26 Mei 2023",
                -6.3385113,
                106.8746798
            )
            storyList.add(story)
        }
        return storyList
    }

    fun generateDummyLoginEntity(): LoginResponse {
        return LoginResponse(
            false,
            "message",
            LoginResult(
                "id",
                "name",
                "token"
            )
        )
    }

    fun generateDummyRegisterEntity(): RegisterResponse {
        return RegisterResponse(
            false,
            "message",
        )
    }

    fun generateDummyMapEntity(): StoriesResponse {
        val data: MutableList<ListStory> = arrayListOf()

        for(i in 0..100) {
           val story = ListStory(
               id = i.toString(),
               name = "name $i",
               description = "description",
               photoUrl = "photo_url",
               createdAt = "created_at",
               lat = i.toDouble(),
               lon = i.toDouble(),
           )
            data.add(story)
        }
        return StoriesResponse(
            false,
            "message",
            data as ArrayList<ListStory>
        )
    }

    fun generateDummyAddStoryEntity(): FileUploadResponse {
        return FileUploadResponse(
            false,
            "message"
        )
    }
}