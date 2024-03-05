package com.example.myhome.utils

import com.google.android.material.textfield.TextInputLayout
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class InputValidatorTest {
    @Mock
    private lateinit var textInputLayout: TextInputLayout

    private lateinit var inputValidator: com.example.myhome.presentation.utils.InputValidator

    @Before
    fun setup() {
        textInputLayout = Mockito.mock(TextInputLayout::class.java)
        inputValidator =
            com.example.myhome.presentation.utils.InputValidator(textInputLayout, { text ->
                text?.length ?: 0 >= 5 // строка длиннее 5 символов
            }, "Ошибка????") { }
    }

    @After
    fun tearDown() {
        Mockito.reset(textInputLayout)
    }

    @Test
    fun `validate returns true for valid input`() {
        val input = "Верно! Всё верно!"
        val isValid = inputValidator.validate(input)
        assertTrue(isValid)
    }

    @Test
    fun `validate returns false for invalid input`() {
        val input = "Чево"
        val isValid = inputValidator.validate(input)
        assertFalse(isValid)
    }

    @Test
    fun `error message is set when input is invalid`() {
        val input = "Чево"
        inputValidator.validate(input)
        Mockito.verify(textInputLayout).error = "Ошибка????"
    }

    @Test
    fun `error message is cleared when input is valid`() {
        val validInput = "Верно! Всё верно!"
        inputValidator.validate(validInput)
        Mockito.verify(textInputLayout).error = null
    }

    @Test
    fun `errorReset is invoked only once when input is invalid`() {
        val input = "Чево"
        val errorResetMock: () -> Unit = Mockito.mock()

        inputValidator =
            com.example.myhome.presentation.utils.InputValidator(textInputLayout, { text ->
                text?.length ?: 0 >= 5 // строка длиннее 5 символов
            }, "Ошибка????") { errorResetMock() }

        inputValidator.validate(input)
        inputValidator.validate(input)

        Mockito.verify(errorResetMock, Mockito.times(1)).invoke()
    }
}