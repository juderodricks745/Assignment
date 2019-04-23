package com.interview.assignment.ui.dash.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.interview.assignment.R
import com.interview.assignment.data.wrappers.BottomWear
import com.interview.assignment.databinding.FragmentBottomItemViewBinding
import java.io.File

class BottomItemViewFragment : Fragment() {

    private lateinit var binding: FragmentBottomItemViewBinding

    companion object {
        fun newInstance(bottomWear: BottomWear): BottomItemViewFragment {
            val args = Bundle().apply { putParcelable("wear", bottomWear) }
            return BottomItemViewFragment().apply { arguments = args }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_item_view, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomWear = arguments?.getParcelable("wear") as BottomWear?
        bottomWear?.run {
            Glide.with(view).load(File(filePath)).into(binding.imgBottomWearView)
        }
    }
}
