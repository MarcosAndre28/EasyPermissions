# EasyPermissions
EasyPermissions é uma biblioteca Android que simplifica a solicitação de permissões de maneira fácil e rápida. Ela fornece uma abordagem simplificada para solicitar permissões em tempo de execução e lidar com a lógica de resposta do usuário.

# Como Usar
## Configuração

1. Adicione a seguinte dependência no arquivo build.gradle do seu projeto:
 ```
  implementation 'pub.devrel:easypermissions:3.0.0'
  ``` 
 2. Certifique-se de que sua aplicação tenha a versão mínima do SDK do Android 23 ou superior.
 
 # Solicitação de Permissão
 
 1. Crie uma lista com as permissões que deseja solicitar:
 ```
val permissions = listOf(
    Manifest.permission.CAMERA,
    Manifest.permission.READ_CONTACTS
)
  ``` 

2. Chame o método EasyPermissions.requestPermissions() passando os seguintes parâmetros: 

° Uma instância do Activity ou Fragment que fará a solicitação de permissão;  <br>
° Uma mensagem de explicação para o usuário;  <br>
° Um código de solicitação;  <br>
° A lista de permissões que deseja solicitar.  <br>

 ```
if (EasyPermissions.hasPermissions(this, *permissions.toTypedArray())) {
    // Permissões já concedidas, execute a lógica desejada.
} else {
    EasyPermissions.requestPermissions(
        this,
        "Precisamos de acesso à sua câmera e contatos para funcionar corretamente.",
        PERMISSION_REQUEST_CODE,
        *permissions.toTypedArray()
    )
}
  ``` 
  3. Sobrescreva os métodos onRequestPermissionsResult() e onActivityResult() da sua Activity ou Fragment e os encaminhe para o método EasyPermissions.onRequestPermissionsResult() e EasyPermissions.onActivityResult(), respectivamente.
   ```
override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
}
  ``` 
  4. Implemente o método onPermissionsGranted() para lidar com o caso em que o usuário concede todas as permissões solicitadas.
  ```
  override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    // Todas as permissões foram concedidas, execute a lógica desejada.
}
```
5. Implemente o método onPermissionsDenied() para lidar com o caso em que o usuário nega uma ou todas as permissões solicitadas.
```
override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
        // Alguma permissão foi negada permanentemente, exiba uma mensagem ou abra as configurações do sistema para que o usuário possa concedê-las manualmente.
    } else {
        // Algumas permissões foram negadas, exiba uma mensagem ou solicite novamente.
    }
}
```

  
