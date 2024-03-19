package com.example.myhome.presentation.features.chat

import com.example.myhome.presentation.features.chat.converters.ChatParcelableConverter
import com.example.myhome.presentation.features.chat.converters.ChatRemoteConverter
import com.example.myhome.presentation.features.chat.converters.ChatUiConverter
import javax.inject.Inject

class ChatMapper @Inject constructor(): ChatUiConverter, ChatRemoteConverter, ChatParcelableConverter