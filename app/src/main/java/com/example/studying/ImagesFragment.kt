package com.example.studying

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.elveum.elementadapter.adapter
import com.elveum.elementadapter.addBinding
import com.elveum.elementadapter.simpleAdapter
import com.example.studying.databinding.EmptyItemBinding
import com.example.studying.databinding.FragmentImagesBinding
import com.example.studying.databinding.ItemImageBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ImagesFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentImagesBinding? = null
    private val binding get() = _binding!!

    private val settings =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
            checkPermission()
        }

    private val permissionReadExternalStorage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                getAllImages()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.containerError.visibility = View.VISIBLE
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        handleErrorPermission(Manifest.permission.READ_MEDIA_IMAGES)
                    else handleErrorPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }

    private val musics = mutableListOf<Image>()

    private val adapter = simpleAdapter<Image, ItemImageBinding> {
        bind {
            it.show(image)
        }

        listeners {
            image.onClick {
                it.setImage { path ->
                    setFragmentResult(IMAGE_URI_REQUEST, bundleOf( IMAGE_URI_KEY to path))
                }
                dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        binding.images.adapter = adapter
        binding.btnRetryEmpty.setOnClickListener {
            checkPermission()
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            checkPermission(Manifest.permission.READ_MEDIA_IMAGES)
        else
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun checkPermission(permission: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding.containerError.visibility = View.GONE
            getAllImages()
        }
        else {
            permissionReadExternalStorage.launch(permission)
        }
    }

    private fun handleErrorPermission(permission: String) {
        if (shouldShowRequestPermissionRationale(permission)) {
            binding.btnRetryError.setOnClickListener {
                checkPermission()
            }
        }
        else {
            binding.btnRetryError.setOnClickListener {
                settings.launch(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:${context?.packageName}")
                })
            }
        }
    }

    private fun getAllImages() {
        context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.ImageColumns.DATA),
            null,
            null,
            null
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                val data = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                musics.add(Image(cursor.getString(data)))
            }
        }

        if (musics.isEmpty()) {
            adapter.submitList(listOf())
            binding.images.visibility = View.GONE
            binding.containerEmpty.visibility = View.VISIBLE
        }
        else {
            adapter.submitList(musics.reversed())
            binding.images.visibility = View.VISIBLE
            binding.containerEmpty.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val IMAGE_URI_REQUEST = "IMAGE_URI_REQUEST"
        const val IMAGE_URI_KEY = "IMAGE_URI_KEY"
    }
}