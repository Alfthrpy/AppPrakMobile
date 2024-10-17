package lat.pam.hellotoast2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.viewModels
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private var mCount = 0;
    private val model: NameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val mShowCount = findViewById<TextView>(R.id.show_count)
        val buttonCountUp = findViewById<Button>(R.id.button_count)
        val buttonToast = findViewById<Button>(R.id.button_toast)
        val buttonReset = findViewById<Button>(R.id.button_reset)
        val textArea = findViewById<EditText>(R.id.editText)
        val buttonPal = findViewById<Button>(R.id.button_palindrome)
        val buttonSwitchPage = findViewById<Button>(R.id.button_switchpage)

        val nameObserver = Observer<Int> { newName ->
            // Update the UI, in this case, a TextView.
            mShowCount.text = newName.toString()
        }

        model.currentName.observe(this,nameObserver)


        buttonCountUp.setOnClickListener(View.OnClickListener {
            mShowCount.textSize = 160F
            mCount++;
            if (mShowCount != null)
                mShowCount.text = mCount.toString()
            model.currentName.setValue(mCount)

        })

        buttonToast.setOnClickListener {
            val tulisan: String = mShowCount?.text.toString()
            val toast: Toast = Toast.makeText(this, "Angka yang dimunculkan $tulisan", Toast.LENGTH_LONG)
            toast.show()
        }


        buttonReset.setOnClickListener(View.OnClickListener{
            mCount = 0

            mShowCount.text = mCount.toString()

        })

        buttonPal.setOnClickListener {
            val inputText = textArea.text.toString()
            mShowCount.textSize = 50.0F
            val charArray = inputText.toCharArray()
            var depan = 0
            var belakang = charArray.size - 1
            var isPalindrome = true

            while (depan < belakang) {
                if (charArray[depan] != charArray[belakang]) {
                    isPalindrome = false
                    break
                }
                depan += 1
                belakang -= 1
            }

            if (isPalindrome) {
                mShowCount.text = "$inputText is a palindrome"

            } else {
                mShowCount.text = "$inputText not a palindrome"
            }
        }

        buttonSwitchPage.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity2::class.java).apply {
                putExtra("key_nama","Hello Alfathir")
            }
            startActivity(intent)
        })







        textArea.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                // Logika yang dijalankan ketika tombol Enter atau Done ditekan
                val inputText = textArea.text.toString()
                mShowCount.text = inputText
                // Contoh tindakan: menampilkan toast dengan teks yang dimasukkan
                Toast.makeText(this, "Teks yang dimasukkan: $inputText", Toast.LENGTH_SHORT).show()

                // Lakukan aksi lain, seperti menyimpan teks atau memproses input
                true // Mengembalikan true untuk menunjukkan bahwa event sudah ditangani
            } else {
                false // Mengembalikan false jika event tidak ditangani
            }
        }

        Log.d("Test debug","Hello World, ini saya");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}