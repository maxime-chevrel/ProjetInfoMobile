package fr.equipe8.projetinfomobile.usecases

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.google.gson.Gson
import fr.equipe8.projetinfomobile.data.routines.Routine
import java.io.File

class ShareUseCase() {
    operator fun invoke(context: Context, routine : Routine){
        val json = Gson().toJson(routine)
        val file = File(context.getExternalFilesDir(null), "routine_export.routine")
        file.writeText(json) // Écrit les données dans le fichier

        val uri = FileProvider.getUriForFile(context, "fr.equipe8.projetinfomobile.fileprovider", file)

        val sharedIntent =  Intent(Intent.ACTION_SEND).apply {
            type = "application/octet-stream" // Type générique
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(sharedIntent, "Partager la routine via"))
    }

}