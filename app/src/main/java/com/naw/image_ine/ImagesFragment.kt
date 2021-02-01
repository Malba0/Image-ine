package com.naw.image_ine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.naw.image_ine.ui.ImageUio
import com.naw.image_ine.ui.ImagesAdapter
import com.naw.image_ine.ui.ImagesViewModel
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ImagesFragment : Fragment() {

    private val imagesViewModel: ImagesViewModel by viewModels()

    // HIER:
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

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            imagesViewModel.getNewImage()
            Snackbar.make(it, getString(R.string.fetch), Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

        initDnD(recyclerView)

        imagesViewModel.load()
    }

    private fun initDnD(recyclerView: RecyclerView) {
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or
                    ItemTouchHelper.DOWN or
                    ItemTouchHelper.START or
                    ItemTouchHelper.END, 0) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                val adapter = (recyclerView.adapter as ImagesAdapter)
                val list = adapter.currentList
                val mutList = list.toMutableList()
                Collections.swap(mutList, fromPosition, toPosition)
                adapter.submitList(mutList)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onPause() {
        imagesViewModel.saveImages()
        super.onPause()
    }
}
