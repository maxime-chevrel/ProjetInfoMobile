package fr.equipe8.projetinfomobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import fr.equipe8.projetinfomobile.data.routines.Routine
import fr.equipe8.projetinfomobile.usecases.RoutinesUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class ImportRoutineActivity : ComponentActivity() {

    @Inject lateinit var routinesUseCases: RoutinesUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri = intent?.data

        if (uri != null) {
            importRoutineFromUri(uri)
        }
        else {
            finish()
        }

    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Traite le fichier à nouveau si l'Activity est déjà en premier plan
        val data: Uri? = intent.data

        data?.let {
            importRoutineFromUri(it)
        }
    }

    private fun importRoutineFromUri(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val json = inputStream?.bufferedReader()?.use { it.readText() }
            val routine = Gson().fromJson(json, Routine::class.java)
            lifecycleScope.launch {
                val id : Int  = routinesUseCases.upsertRoutine(routine.copy(id=0))
                val intent = Intent(this@ImportRoutineActivity, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    putExtra("routineId", id)
                }
                startActivity(intent)
                finish()
            }

        } catch (e: Exception) {
            Log.e("ImportRoutineActivity", "Erreur lors de l'importation de la routine : ${e.message}")
            e.printStackTrace()
            finish()
        }
    }

}