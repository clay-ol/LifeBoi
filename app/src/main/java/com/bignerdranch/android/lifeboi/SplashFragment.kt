package com.bignerdranch.android.lifeboi

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import pl.droidsonroids.gif.GifImageView

class SplashFragment: Fragment() {

    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var wwImageView: GifImageView

    interface  Callbacks {
        fun goToLogin()
        fun goToSignUp()
    }

    private var callbacks: Callbacks? = null // will act as my "MainActivity controller"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks? // attaching the MainActivity to callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        loginButton = view.findViewById(R.id.login_button)
        signUpButton = view.findViewById(R.id.signUp_button)
        wwImageView = view.findViewById(R.id.ww_image)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wwImageView.setImageResource(R.drawable.frontrun)
    }

    override fun onStart() {
        super.onStart()

        loginButton.setOnClickListener {
            callbacks?.goToLogin()
        }

        signUpButton.setOnClickListener {
            callbacks?.goToSignUp()
        }
    }

    companion object {
        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }
}