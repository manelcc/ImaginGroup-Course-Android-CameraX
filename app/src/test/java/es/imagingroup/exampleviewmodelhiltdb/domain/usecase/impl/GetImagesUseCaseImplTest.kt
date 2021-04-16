package es.imagingroup.exampleviewmodelhiltdb.domain.usecase.impl


import es.imagingroup.exampleviewmodelhiltdb.data.repository.CameraRepository
import es.imagingroup.exampleviewmodelhiltdb.domain.usecase.GetImagesUseCase

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetImagesUseCaseImplTest {



    @Mock
    lateinit var repository: CameraRepository

    private lateinit var SUT: GetImagesUseCase

    @Before
    fun setUp() {
        SUT = GetImagesUseCaseImpl(repository)
    }

    @Test
    fun getImages() {
        //When
        SUT.getImages()
        //Then
        verify(repository).loadImages()
        verify(repository, times(1)).loadImages()
        verify(repository, never()).saveVideo("name", "file")
    }

    @Test
    fun savePhoto() {
    }
}