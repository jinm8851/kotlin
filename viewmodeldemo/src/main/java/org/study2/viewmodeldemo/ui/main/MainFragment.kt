package org.study2.viewmodeldemo.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.study2.viewmodeldemo.R
import org.study2.viewmodeldemo.databinding.MainFragmentBinding
import org.study2.viewmodeldemo.BR.myViewModel

class MainFragment : Fragment() {


//    private var _binding: MainFragmentBinding? = null
//    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    lateinit var binding:MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       // return inflater.inflate(R.layout.main_fragment, container, false)
//        _binding = MainFragmentBinding.inflate(inflater,container,false)
        binding  = DataBindingUtil.inflate(inflater,R.layout.main_fragment,container,false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.setVariable(myViewModel,viewModel)

       // binding.resultText.text = viewModel.getResult().toString()

//        var resultObserver = Observer<Float> {
//            result -> binding.resultText.text = result.toString()
//        }

//        viewModel.getResult().observe(viewLifecycleOwner,resultObserver)

//       binding.convertButton.setOnClickListener {
//        if (binding.dollarText.text.isNotEmpty())
//        {
//            viewModel.setAmount(binding.dollarText.text.toString())
//          //  binding.resultText.text = viewModel.getResult().toString()
//        }else{
//            binding.resultText.text = " NO Value"
//        }
//       }
    }

}