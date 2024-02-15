package com.mohamed.tasks.details.presentation.view

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mohamed.tasks.core.extensions.visible
import com.mohamed.tasks.core.util.UtilHelper.arePermissionsGranted
import com.mohamed.tasks.core.util.UtilHelper.getDominantColor
import com.mohamed.tasks.core.util.UtilHelper.requestPermission
import com.mohamed.tasks.details.databinding.FragmentComicsDetailsBinding
import com.mohamed.tasks.details.presentation.service.UploadService
import com.mohamed.tasks.details.presentation.stateholder.ComicDetailsViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class ComicsDetailsFragment : Fragment() {

    private lateinit var imageUrl: String
    private lateinit var binding: FragmentComicsDetailsBinding
    private val viewModel: ComicDetailsViewModel by viewModels()
    private val args by navArgs<ComicsDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentComicsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //observeOnUiUpdates()
        //viewModel.sendIntent(ComicDetailsIntent.GetDetails(args.id))
        updateUI()
    }


    private fun updateUI() = with(binding) {

        args.comics?.let { comicsDetails ->
            with(binding) {
                clContent.visible()
                comicsDetails.textObjects?.firstOrNull()?.text?.let {
                    editCaption.setText(it)
                } ?: kotlin.run {
                    editCaption.setText("")
                }
                comicsDetails.images?.firstOrNull()?.let {
                    imageUrl = "${it.path}.${it.extension}"
                    Picasso.get()
                        .load(imageUrl)
                        .fit().into(ivPoster, object : Callback {
                            override fun onSuccess() {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    val drawable = ivPoster.drawable
                                    val bitmap = (drawable as BitmapDrawable).bitmap
                                    val backgroundColor = getDominantColor(bitmap)
                                    withContext(Dispatchers.Main) {
                                        root.setBackgroundColor(backgroundColor)
                                    }
                                }
                            }

                            override fun onError(e: Exception?) {
                            }
                        })
                } ?: kotlin.run {
                    ivPoster.setImageResource(0)
                }
            }
        }

        btSubmit.setOnClickListener {
            if (editWidth.text.toString().isNotEmpty() && editHeight.text.toString()
                    .isNotEmpty() && imageUrl.isNotEmpty()
            ) {
                if (arePermissionsGranted(requireContext())){
                   startService()
                }
                else
                    requestPermission(requestPermissionLauncher)
            }
        }

    }

    private fun startService(){
        // Start the foreground service to upload the image
        val intent = Intent(requireContext(), UploadService::class.java)
        intent.putExtra("imageUrl", imageUrl)
        intent.putExtra("width", binding.editWidth.text.toString().toIntOrNull())
        intent.putExtra("height", binding.editHeight.text.toString().toIntOrNull())
        requireContext().startService(intent)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val grantedPermissions = mutableListOf<String>()
            val deniedPermissions = mutableListOf<String>()

            for (entry in permissions.entries) {
                val permission = entry.key
                val isGranted = entry.value

                if (isGranted) {
                    grantedPermissions.add(permission)
                } else {
                    deniedPermissions.add(permission)
                }
            }

            // Handle granted and denied permissions
            if (grantedPermissions.isNotEmpty()) {
                startService()
            }
        }

    /*private fun updateUI(state: ComicDetailsState) = with(state) {
        with(binding.pbLoading) {
            if (isLoading) visible()
            else gone()
        }
        comicDetails?.let { comicsDetails ->
            with(binding) {
                clContent.visible()
                comicsDetails.textObjects?.firstOrNull()?.text?.let {
                    editCaption.setText(it)
                } ?: kotlin.run {
                    editCaption.setText("")
                }
                comicsDetails.images?.firstOrNull()?.let {
                    val url = "${it.path}.${it.extension}"
                    Picasso.get()
                        .load(url)
                        .fit().into(ivPoster, object : Callback {
                            override fun onSuccess() {
                                val drawable = ivPoster.drawable
                                val bitmap = (drawable as BitmapDrawable).bitmap

                                root.setBackgroundColor(getDominantColor(bitmap))
                            }

                            override fun onError(e: Exception?) {
                            }
                        })
                } ?: kotlin.run {
                    ivPoster.setImageResource(0)
                }
            }
        }
        errorRes?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
        errorMsg?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun observeOnUiUpdates() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                //state.collectLatest(::updateUI)
            }
        }
    }*/
}