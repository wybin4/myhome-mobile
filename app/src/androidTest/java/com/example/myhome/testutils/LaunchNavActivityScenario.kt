package com.example.myhome.testutils

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.example.myhome.MainActivity
import com.example.myhome.TestFragmentActivity

inline fun <reified T : Fragment> launchNavActivityScenario(
    navController: NavController,
    args: Bundle? = null
): ActivityScenario<TestFragmentActivity> {
    val intent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            TestFragmentActivity::class.java
        )
    )

    val scenario = ActivityScenario.launch<TestFragmentActivity>(intent)

    scenario.onActivity { activity ->
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            T::class.java.classLoader!!,
            T::class.java.name
        )
        fragment.arguments = args

        activity.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment)
            .commitNow()

        Navigation.setViewNavController(
            activity.findViewById(android.R.id.content),
            navController
        )
    }

    return scenario
}

inline fun <reified T : Fragment> ActivityScenario<out AppCompatActivity>.getFragment(): T {
    lateinit var fragment: T
    onActivity { activity ->
        fragment = activity.supportFragmentManager.fragments.firstOrNull { it is T } as? T
            ?: error("Fragment of type ${T::class.java.simpleName} not found")
    }
    return fragment
}
