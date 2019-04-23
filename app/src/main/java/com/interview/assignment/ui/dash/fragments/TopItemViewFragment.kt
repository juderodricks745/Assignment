package com.interview.assignment.ui.dash.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide

import com.interview.assignment.R
import com.interview.assignment.data.wrappers.TopWear
import com.interview.assignment.databinding.FragmentTopItemViewBinding
import java.io.File

class TopItemViewFragment : Fragment() {

    private lateinit var binding: FragmentTopItemViewBinding

    companion object {
        fun newInstance(topWear: TopWear): TopItemViewFragment {
            val args = Bundle().apply { putParcelable("wear", topWear) }
            return TopItemViewFragment().apply { arguments = args }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_item_view, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topWear = arguments?.getParcelable("wear") as TopWear?
        topWear?.run {
            Glide.with(view).load(File(filePath)).into(binding.imgTopWearView)
        }
    }
}
