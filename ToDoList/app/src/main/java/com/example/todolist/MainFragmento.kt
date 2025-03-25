package com.example.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.todolist.databinding.FragmentMainFragmentoBinding
import com.example.todolist.model.Not
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class MainFragmento : Fragment() {

    private var _binding: FragmentMainFragmentoBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: NotDatabase
    private lateinit var notDao: NotDAO

    private val mDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(requireContext(), NotDatabase::class.java, "Not").build()
        notDao = db.notDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainFragmentoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener{yeniekle(it)}
        binding.todoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        verileriAl()
    }

    fun verileriAl()
    {
        mDisposable.add(
            notDao.getall().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResponse)
        )
    }


    fun handleResponse(notlar: List<Not>)
    {
        val notAdapter = NotAdapter(notlar)
        binding.todoRecyclerView.adapter = notAdapter
    }
    fun yeniekle(view: View)
    {
        val action = MainFragmentoDirections.actionMainFragmentoToBosFragment(bilgi = "yeni",id = 0)
        Navigation.findNavController(view).navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDisposable.clear()
    }
}