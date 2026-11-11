package c14220073.navdrawer

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import c14220073.navdrawer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mengaktifkan ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // Mengatur Toolbar sebagai ActionBar utama
        setSupportActionBar(binding!!.toolbar)

        // Mengambil NavController dari NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.frameLayout) as NavHostFragment
        navController = navHostFragment.navController

        // Menghubungkan NavigationView dengan NavController
        binding!!.navView.setupWithNavController(navController)

        // Konfigurasi AppBar agar terhubung dengan Drawer dan Grafik Navigasi
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.profileFragment,
                R.id.settingFragment
            ),
            binding!!.drawer
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Mengatur Insets untuk Toolbar agar tidak tertutup status bar perangkat
        ViewCompat.setOnApplyWindowInsetsListener(binding!!.toolbar) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val params = v.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = systemBars.top
            v.layoutParams = params
            insets
        }
    }

    // Mengatur tombol menu/back di pojok kiri atas
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Membersihkan binding saat Activity dihancurkan untuk mencegah kebocoran memori
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}