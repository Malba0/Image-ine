package com.naw.image_ine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.naw.image_ine.ui.ImageUio
import com.naw.image_ine.ui.ImagesAdapter
import com.naw.image_ine.ui.ImagesViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ImagesFragment : Fragment() {

    private val imagesViewModel: ImagesViewModel by viewModels()

    // HIER:
    // Lost adapter and lost of view code (git error)
    // TODO:
    // 1. Redo RecyclerView and Adapter                     [ 1 hr ]
    //      https://www.raywenderlich.com/1560485-android-recyclerview-tutorial-with-kotlin
    //      https://developer.android.com/guide/topics/ui/layout/recyclerview
    //      views-widgets-samples
    //
    // 2. Unit test (especially for Repos and UseCase       [ 1 hr ]

    // 3. [Bonus] DO not download same image from online    [ 1 hr ]
    // 4. Drag Order images                                 [ 2 hr ]


    // 5. Swipe remove image                                [ 1 hr ]
    // 6. [Bonus] Deploy pipeline                           [ 2 hr ]
    // 7. Publish                                           [ 1 hr ]
    //
    //                                  TOTAL:              [ 9 hr ]

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_images, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_ImagesFragment_to_ErrorFragment)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = ImagesAdapter()
        imagesViewModel.getImages().observe(viewLifecycleOwner) {
            (recyclerView.adapter as ImagesAdapter).submitList(it as MutableList<ImageUio>)
        }
    }
}
