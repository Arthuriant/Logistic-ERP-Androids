package andika.polman.logisticerp2

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import andika.polman.logisticerp2.databinding.ActivitySeconBinding

class SeconActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeconBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeconBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navViewSecon

        val navController = findNavController(R.id.nav_host_fragment_activity_secon)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_ReportItem, R.id.navigation_IncomingItem, R.id.navigation_ExitItem
            )
        )

        navView.setupWithNavController(navController)
    }
}