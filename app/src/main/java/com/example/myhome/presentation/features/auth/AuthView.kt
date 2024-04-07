package com.example.myhome.presentation.features.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.LoginViewBinding
import com.example.myhome.presentation.state.add.AddState
import com.example.myhome.presentation.utils.InputValidator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthView : Fragment() {
    private var _binding: LoginViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var emailValidator: InputValidator
    private lateinit var passwordValidator: InputValidator

    val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupValidators()

        binding.nextButton.setOnClickListener {
            val isEmailValid = emailValidator.validate(viewModel.selectEmail)
            val isPasswordValid = passwordValidator.validate(viewModel.selectPassword)
            if (isEmailValid && isPasswordValid) {
                viewModel.login()
            }
        }
        binding.exitButton.setOnClickListener {
            requireActivity().finish()
        }
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.addState.observe(viewLifecycleOwner) { state ->
            observeAddState(state)
        }
    }

    private fun setupValidators() {
        val colorRed = ContextCompat.getColor(requireActivity(), R.color.red)

        emailValidator = InputValidator(
            binding.emailInput,
            { text: String? -> text?.length!! > 0 && '@' in text },
            "Некорректный email",
            {
                binding.emailInput.editText?.doOnTextChanged { _, _, _, _ ->
                    emailValidator.validate(viewModel.selectEmail)
                }
            }, colorRed
        )
        passwordValidator = InputValidator(
            binding.passwordInput,
            { text: String? -> text?.length!! >= 8 },
            "Пароль должен содержать не менее 8 символов",
            {
                binding.passwordInput.editText?.doOnTextChanged { _, _, _, _ ->
                    passwordValidator.validate(viewModel.selectPassword)
                }
            }, colorRed
        )
    }

    private fun observeAddState(state: AddState) {
        when (state) {
            is AddState.Success -> findNavController().navigate(R.id.list_meter)
            is AddState.CodeError -> {
                binding.errorMessage.text = "Что-то пошло не так"
                binding.errorMessage.visibility = View.VISIBLE
                binding.emailInput.error = "ㅤ"
                binding.passwordInput.error = "ㅤ"
            }
            is AddState.NetworkError -> {
                binding.errorMessage.text = "Неверный логин или пароль"
                binding.errorMessage.visibility = View.VISIBLE
                binding.emailInput.error = "ㅤ"
                binding.passwordInput.error = "ㅤ"
            }
            else -> {
                binding.errorMessage.visibility = View.GONE
                binding.emailInput.error = null
                binding.passwordInput.error = null
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}