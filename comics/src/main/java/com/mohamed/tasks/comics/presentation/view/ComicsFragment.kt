package com.mohamed.tasks.comics.presentation.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.mohamed.tasks.comics.R
import com.mohamed.tasks.comics.databinding.FragmentComicsBinding
import com.mohamed.tasks.comics.presentation.adapter.ComicsAdapter
import com.mohamed.tasks.comics.presentation.navigation.ComicsNavigation
import com.mohamed.tasks.comics.presentation.stateholder.ComicsViewModel
import com.mohamed.tasks.core.extensions.gone
import com.mohamed.tasks.core.extensions.visible
import com.mohamed.tasks.core.util.DialogHelper.confirmMenu
import com.mohamed.tasks.core.util.UtilHelper
import com.mohamed.tasks.core.util.UtilHelper.saveImageToGalleryWithCaption
import com.mohamed.tasks.core.util.UtilHelper.saveImageToGalleryWithCaptionAbove30
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ComicsFragment : Fragment() {
    @Inject
    lateinit var comicsNavigation: ComicsNavigation
    private lateinit var binding: FragmentComicsBinding
    private val viewModel: ComicsViewModel by viewModels()
    private val adapter: ComicsAdapter by lazy {
        ComicsAdapter(itemClick = {
            // nav with deeplink
            /*val request =
                NavDeepLinkRequest.Builder.fromUri(comicsNavigation.getDetailsDeepLink(it.id).toUri())
                    .build()
            findNavController().navigate(request)*/
            //if we want to nav with comic object
            comicsNavigation.navigateToDetails(it)
        }, itemLongClick = { comic ->
            if (UtilHelper.arePermissionsGranted(requireContext()))
                context?.confirmMenu(
                    title = getString(R.string.alert_save),
                    positiveBtnClickListener = { dialog ->
                        comic.images?.firstOrNull()?.let {
                            val url = "${it.path}.${it.extension}"
                            val caption=comic.textObjects?.firstOrNull()?.text
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                saveImageToGalleryWithCaptionAbove30(context = requireContext(), imageUrl = url, caption = caption, fileName = it.path)
                            }else{
                                saveImageToGalleryWithCaption(context = requireContext(), imageUrl = url, caption = caption, fileName = it.path)
                            }
                        }
                        dialog.dismiss()
                    }
                )
            else
                UtilHelper.requestPermission(requestPermissionLauncher)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentComicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeOnUiUpdates()
    }

    private fun initViews() = with(binding) {
        rvComics.adapter = adapter
        swipeRefresh.setOnRefreshListener {
            viewModel.getComics()
            swipeRefresh.isRefreshing = false
        }

    }

    private fun observeOnUiUpdates() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                comicsStateFlow.collectLatest { comics ->
                    adapter.submitList(comics)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorResChannel.collectLatest {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorMsgChannel.collectLatest {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadingChannel.collectLatest { isLoading ->
                    with(binding.pbLoading) {
                        if (isLoading) visible()
                        else gone()
                    }
                }
            }
        }
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
            }
        }

}