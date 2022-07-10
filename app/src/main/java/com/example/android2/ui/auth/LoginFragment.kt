package com.example.android2.ui.auth

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.pm.verify.domain.DomainVerificationUserState
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.VerifiedInputEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.android2.R
import com.example.android2.databinding.FragmentLoginBinding
import com.example.android2.databinding.NewsFragmentBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var callbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken?=null

    private  var mverificationID:String?=null
    private lateinit var firebaseAuth:FirebaseAuth

    private var timer: CountDownTimer? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.editcode.visibility = View.GONE
        binding.btnConfirm.visibility = View.GONE
        binding.textcode.visibility = View.GONE
        binding.resendcode.visibility = View.GONE
        binding.timer.visibility = View.GONE




        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                Log.e("Login","onVerificationCompleted")

                signInWithPhoneAuthCredential(phoneAuthCredential)
            }

            override fun onVerificationFailed(exception: FirebaseException) {

              Log.e("Login","onVerificationFailed: ${exception.message}")
                Toast.makeText(requireContext(),"${exception.message}",Toast.LENGTH_SHORT).show()

            }

            @SuppressLint("SetTextI18n")
            override fun onCodeSent(verificationID: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationID, token)
                Log.e("Login","onCodeSent: $verificationID")
                mverificationID = verificationID
                forceResendingToken=token


                binding.textphone.visibility = View.GONE
                binding.btnContinue.visibility = View.GONE
                binding.editPhone.visibility = View.GONE


                binding.editcode.visibility = View.VISIBLE
                binding.btnConfirm.visibility = View.VISIBLE
                binding.textcode.visibility = View.VISIBLE
                binding.resendcode.visibility = View.VISIBLE
                binding.timer.visibility = View.VISIBLE

                Toast.makeText(requireContext(),"Отправка кода...",Toast.LENGTH_SHORT).show()

            }


        }
        binding.btnContinue.setOnClickListener {

            val phone = binding.editPhone.text.toString().trim()

            if (TextUtils.isEmpty(phone)){
                Toast.makeText(requireContext(),"Введите номер телефона!",Toast.LENGTH_SHORT).show()
            }
            else{
                requestSMS()
            }
            countdownTimer(59_000/1000)
        }

        binding.resendcode.setOnClickListener{
            val phone = binding.editPhone.text.toString().trim()

            if (TextUtils.isEmpty(phone)){
                Toast.makeText(requireContext(),"Введите номер телефона!",Toast.LENGTH_SHORT).show()
            }
            else{
                resendSMSCode(forceResendingToken)
            }
        }

        binding.btnConfirm.setOnClickListener {
            val code = binding.editcode.text.toString().trim()
            if (TextUtils.isEmpty(code)){
                Toast.makeText(requireContext(),"Введите код!",Toast.LENGTH_SHORT).show()
            }
            else{
                verifyPhoneNumberWitchCode(mverificationID,code)
            }
        }
    }

    private fun countdownTimer(timeMillis: Long){
        timer?.cancel()
        timer = object : CountDownTimer(timeMillis,1){
            override fun onTick(millisUntilFinished: Long) {
               binding.timer.text = "00:$millisUntilFinished"
            }

            override fun onFinish() {
                binding.textphone.visibility = View.VISIBLE
                binding.btnContinue.visibility = View.VISIBLE
                binding.editPhone.visibility = View.VISIBLE

                binding.editcode.visibility = View.GONE
                binding.btnConfirm.visibility = View.GONE
                binding.textcode.visibility = View.GONE
                binding.resendcode.visibility = View.GONE
                binding.timer.visibility = View.GONE

            }

        }.start()
    }



    private fun requestSMS() {

        val phoneNumber = binding.editPhone.text.toString().trim()

        Log.e("Login","requestSMS: $phoneNumber")
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun resendSMSCode(token: PhoneAuthProvider.ForceResendingToken?){


        val phoneNumber = binding.editPhone.text.toString()

        Log.e("Login","resendSMSCode: $phoneNumber")

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)
            .setForceResendingToken(token!!)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyPhoneNumberWitchCode(verificationID:String?,code:String){
        Log.e("Login","verifyPhoneNumberWitchCode: $verificationID $code")



        val credential = PhoneAuthProvider.getCredential(verificationID!!,code)
        signInWithPhoneAuthCredential(credential)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth= FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                val phone = firebaseAuth.currentUser?.phoneNumber
                Toast.makeText(requireContext(),"Номер: $phone подтвержден",Toast.LENGTH_SHORT).show()
                //navigateUP
                findNavController().navigateUp()

            }
            .addOnFailureListener{e->
                //login failed
                Toast.makeText(requireContext(),"${e.message}",Toast.LENGTH_SHORT).show()

            }
    }


}