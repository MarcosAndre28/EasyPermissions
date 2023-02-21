package com.example.easypermissions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.easypermissions.databinding.ActivityMainBinding
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    lateinit var binding: ActivityMainBinding

    companion object {
        const val PERMISSON_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            if (hasPermissions()){
                btnRequestPermissions.isEnabled = false
                tvMsg.text = getString(R.string.yes_permissions)
            }
            else{
                btnRequestPermissions.isEnabled = true
                tvMsg.text = getString(R.string.not_permissions)
            }
            btnRequestPermissions.setOnClickListener {
                requestPermissions()
            }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
       if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
           SettingsDialog.Builder(this).build().show()
       }
        else{
            requestPermissions()
        }
    }


    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        binding.apply {
            tvMsg.text = "Todas as permissões são concedidas"
            btnRequestPermissions.isEnabled = false
        }
    }

    private fun hasPermissions() = EasyPermissions.hasPermissions(
            this,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_CONTACTS
        )


    private fun requestPermissions() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.app_permissions),
            PERMISSON_REQUEST_CODE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_CONTACTS

        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }
}