package com.example.easypermissions

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.example.easypermissions.databinding.ActivityMainBinding
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    lateinit var binding: ActivityMainBinding

    // Código da permissão usada para identificar a solicitação de permissão
    companion object {
        const val PERMISSON_REQUEST_CODE = 1
    }

    // Método que é executado ao criar a Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla o layout da Activity usando o binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Define o layout da Activity
        setContentView(binding.root)

        // Define o comportamento do botão "Request Permissions"
        binding.btnRequestPermissions.setOnClickListener {
            requestPermissions()
        }

        // Verifica se as permissões já foram concedidas e atualiza a interface
        updateUI(hasPermissions())
    }

    // Método para atualizar a interface de usuário
    private fun updateUI(hasPermissions: Boolean) {
        if (hasPermissions) {
            // Se as permissões já foram concedidas, desabilita o botão
            binding.btnRequestPermissions.isEnabled = false
            // Atualiza o TextView com uma mensagem indicando que as permissões foram concedidas
            binding.tvMsg.text = getString(R.string.yes_permissions)
        } else {
            // Se as permissões ainda não foram concedidas, habilita o botão
            binding.btnRequestPermissions.isEnabled = true
            // Atualiza o TextView com uma mensagem indicando que as permissões não foram concedidas
            binding.tvMsg.text = getString(R.string.not_permissions)
        }
    }

    // Verifica se as permissões já foram concedidas
    private fun hasPermissions() = EasyPermissions.hasPermissions(
        this,
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_CONTACTS
    )

    // Solicita as permissões
    private fun requestPermissions() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.app_permissions),
            PERMISSON_REQUEST_CODE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_CONTACTS
        )
    }

    // Trata o resultado da solicitação de permissão
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    // Trata o evento de permissões concedidas
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        updateUI(true)
    }

    // Trata o evento de permissões negadas
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            // Se alguma permissão foi negada permanentemente, mostra um diálogo personalizado
            showCustomPermissionDialog()
        } else {
            // Caso contrário, solicita as permissões novamente
            requestPermissions()
        }
    }

    // Mostra um diálogo personalizado informando o usuário sobre a necessidade de conceder as permissões
    private fun showCustomPermissionDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle(R.string.permission_dialog_title)
            .setMessage(R.string.permission_dialog_message)
            .setPositiveButton(R.string.permission_dialog_button) { dialog, which -> openAppSettings() }
        builder.show()
    }

    // Abre a tela de configurações do aplicativo para que o usuário
    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, PERMISSON_REQUEST_CODE)
    }
}


