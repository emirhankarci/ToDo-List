package com.example.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.room.Room
import com.example.todolist.databinding.FragmentBosBinding
import com.example.todolist.model.Not
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class BosFragment : Fragment() {

    private var _binding: FragmentBosBinding? = null
    private val binding get() = _binding!!
    private val mDisposable = CompositeDisposable()
    private var secilenNot : Not? = null

    private lateinit var db: NotDatabase
    private lateinit var notDao: NotDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(requireContext(), NotDatabase::class.java, "Not").build()
        notDao = db.notDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBosBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.kaydetButton.setOnClickListener {kkaydet(it)}
        binding.silButton.setOnClickListener {ssil(it)}

        arguments?.let {
            val bilgi = BosFragmentArgs.fromBundle(it).bilgi

            if(bilgi == "yeni")
            {
                secilenNot = null
                binding.kaydetButton.isEnabled = true
                binding.silButton.isEnabled = false
                binding.editText1.setText("")
            }
            else
            {
                binding.kaydetButton.isEnabled = true
                binding.silButton.isEnabled = true
                val id = BosFragmentArgs.fromBundle(it).id
                mDisposable.add(
                    notDao.get(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResponseForGet)
                )
            }
        }
    }

    fun handleResponseForGet(not: Not)
    {
        binding.editText1.setText(not.yazi)
        secilenNot = not
    }

    fun kkaydet(view: View)
    {
        val not = binding.editText1.text.toString()
        val not1 = Not(not)

        mDisposable.add(notDao.insert(not1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResponseForInsert))


    }

    private fun handleResponseForInsert(){
        val action = BosFragmentDirections.actionBosFragmentToMainFragmento()
        Navigation.findNavController(requireView()).navigate(action)
    }

    fun ssil(view: View)
    {
        if(secilenNot != null)
        {
            mDisposable.add(
                notDao.delete(not = secilenNot!!).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResponseForInsert)
            )
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDisposable.clear()
    }



}