package eu.seijindemon.myinformation

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import eu.seijindemon.myinformation.data.MyInfoViewModel
import eu.seijindemon.myinformation.data.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.add_user_popup.*
import kotlinx.android.synthetic.main.add_user_popup.view.*
import kotlinx.android.synthetic.main.delete_user_popup.*
import kotlinx.android.synthetic.main.delete_user_popup.view.*
import kotlinx.android.synthetic.main.model_link.view.*
import kotlinx.android.synthetic.main.users_popup.view.*
import java.util.*

class MainActivity : AppCompatActivity(), UsersCustomAdapter.OnItemClickListener{

    private lateinit var mMyInfoViewModel: MyInfoViewModel

    private lateinit var myAdapter: UsersCustomAdapter

    private lateinit var usersList: List<User>

    private lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        setContentView(R.layout.activity_main)

        // Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        // End Toolbar

        // Room
        mMyInfoViewModel = ViewModelProvider(this).get(MyInfoViewModel::class.java)
        mMyInfoViewModel.getAllUsers().observe(this, androidx.lifecycle.Observer { users ->
            usersList = users
        })
        // End Room


        update_button.setOnClickListener{

        }

        delete_button.setOnClickListener{
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.delete_user_popup, null)
            val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
            val mAlertDialog = mBuilder.show()

            val user: User = currentUser
            mDialogView.firstName_delete.text = user.firstName
            mDialogView.lastName_delete.text = user.lastName
            mDialogView.delete_user_button.setOnClickListener {
                if(user != null){
                    mMyInfoViewModel.deleteUser(user)
                    Toast.makeText(this, "Successfully deleted!", Toast.LENGTH_LONG).show()
                    mAlertDialog.dismiss()

                    //  clear main textviews
                    val firstName = findViewById<TextView>(R.id.firstNameView)
                    val lastName = findViewById<TextView>(R.id.lastNameView)

                    firstName.text = "FirstName:"
                    lastName.text = "LastName"
                }
                else
                {
                    Toast.makeText(this, "UnSuccessfully delete!", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    // Change Language
    private fun showChangeLanguageDialog() {
        val listItems = arrayOf("English", "Greek")
        val mBuilder = AlertDialog.Builder(this@MainActivity)
        mBuilder.setTitle("Choose Language...")
        mBuilder.setSingleChoiceItems(listItems, -1)
        { dialog, i ->
            if (i == 0) {
                setLocale("en")
                recreate()
            } else if (i == 1) {
                setLocale("el")
                recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun setLocale(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocale() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null) {
            setLocale(language)
        }
    }
    // End Change Language

    // Popup Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.popup_menu, menu)
        return true
    }
    // End Popup Menu

    // Check Data
    private fun inputCheck(firstName: String, lastName: String): Boolean
    {
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName))
    }
    // End Check Data

    // Choose User
    override fun onItemClick(potition: Int)
    {
        val user: User = usersList[potition]
        currentUser = user // get current user for use it in others fun
        // Toast.makeText(this, user.firstName, Toast.LENGTH_SHORT).show()

        val firstName = findViewById<TextView>(R.id.firstNameView)
        val lastName = findViewById<TextView>(R.id.lastNameView)

        firstName.text = user.firstName
        lastName.text = user.lastName
    }
    // End Choose User

    // Menu Item Click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_user -> {
                //
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.add_user_popup, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                     //.setTitle("Add User")
                val mAlertDialog = mBuilder.show()
                mDialogView.button_signin.setOnClickListener {
                    val firstName = mDialogView.firstName_signin.text.toString()
                    val lastName = mDialogView.lastName_signin.text.toString()
                    if(inputCheck(firstName, lastName)){
                        val user = User(0, firstName, lastName, null, null, null)
                        mMyInfoViewModel.addUser(user)
                        Toast.makeText(this, "Successfully added!", Toast.LENGTH_LONG).show()
                        mAlertDialog.dismiss()
                    }
                    else
                    {
                        Toast.makeText(this, "Please fill out all fields!", Toast.LENGTH_LONG).show()
                    }
                }
                true
            }
            R.id.users -> {
                //
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.users_popup, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    //.setTitle("Users")
                val mAlertDialog = mBuilder.show()

                // Set Up RecyclerList and LinearLayoutManager
                val recyclerView = mDialogView.recycler_view
                myAdapter = UsersCustomAdapter(this)
                recyclerView.adapter = myAdapter
                recyclerView.layoutManager = LinearLayoutManager(this)
                // End Set Up RecyclerList and LinearLayoutManager
                mMyInfoViewModel.getAllUsers().observe(this, androidx.lifecycle.Observer { users ->
                    myAdapter.setData(users)
                })

                true
            }
            R.id.language -> {
                // Open Change Language
                showChangeLanguageDialog()
                true
            }
            R.id.share -> {
                // Open Share It
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, "My Informations")
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Download this Application now: http://play.google.com/store/apps/details?id=$packageName"
                )
                startActivity(Intent.createChooser(intent, "ShareVia"))
                true
            }
            R.id.rate -> {
                // Open Rate URL
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$packageName")
                        )
                    )
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                        )
                    )
                }
                true
            }
            R.id.privacy_police -> {
                // Open Privacy Police URL
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://my-informations.flycricket.io/privacy.html")
                        )
                    )
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "Privacy Police Not Found!", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> false
        }
    }
    // End Menu Item Click

    // Show Icons with correct color
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            if (menu is MenuBuilder) {
                try {
                    val field = menu.javaClass.getDeclaredField("mOptionalIconsVisible")
                    field.isAccessible = true
                    field.setBoolean(menu, true)
                } catch (ignored: Exception) {
                    // ignored exception
                    //logger.debug("ignored exception: ${ignored.javaClass.simpleName}")
                }
            }
            for (item in 0 until menu.size()) {
                val menuItem = menu.getItem(item)
                menuItem.icon.setIconColor(
                    if (menuItem.getShowAsAction() == 0) Color.BLACK
                    else Color.WHITE
                )
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    fun MenuItem.getShowAsAction(): Int {
        var f = this.javaClass.getDeclaredField("mShowAsAction")
        f.isAccessible = true
        return f.getInt(this)
    }

    fun Drawable.setIconColor(color: Int) {
        mutate()
        setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
    // End Show Icons with correct color

    // Back Pressed
    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    // End Back Pressed

}