package com.example.giderkontrol

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.app.AlertDialog
import android.content.DialogInterface
import android.media.MediaPlayer

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toplam_gider: TextView = findViewById(R.id.toplam_gider)
        val kalan_kota: TextView = findViewById(R.id.kalan_kota)
        val harcamalariniz: TextView = findViewById(R.id.harcamalariniz)
        val gider_turu: EditText = findViewById(R.id.gider_turu)
        val btnKota: Button = findViewById(R.id.btnKota)
        val btnGider: Button = findViewById(R.id.btnGider)
        val miktar: EditText = findViewById(R.id.miktar)
        var toplam = 0
        var kota = 0
        val mp : MediaPlayer= MediaPlayer.create(this,R.raw.videoplayback)


        harcamalariniz.setMovementMethod(ScrollingMovementMethod())

        btnKota.setOnClickListener {
            try{
                val sayi1: Int = Integer.parseInt(miktar.text.toString())
                kalan_kota.text = "Kalan Kota: $sayi1 $"
                kota = sayi1
            }catch(e:NumberFormatException){

                val dialogBuilder = AlertDialog.Builder(this)

                dialogBuilder.setMessage("Lütfen Geçerli Bir Kota Belirleyiniz.")
                    .setCancelable(false)

                    .setNegativeButton("Tamam", DialogInterface.OnClickListener {
                            dialog, id -> dialog.cancel()
                    })

                val alert = dialogBuilder.create()
                alert.setTitle("Kota Miktarı Alanı Boş!")
                alert.show()


            }
        }

        btnGider.setOnClickListener {

            try {
                val sayi2: Int = Integer.parseInt(miktar.text.toString())
                toplam += sayi2
                toplam_gider.text = "Toplam Gider: $toplam $"

                val bakiye = kota - toplam
                kalan_kota.text = "Kalan Kota: $bakiye $"

                val harcamalarString = gider_turu.text.toString()
                harcamalariniz.text =
                      harcamalariniz.text.toString() + "\n ✓ " + harcamalarString +
                            " " + sayi2.toString() + "$ "

                if (toplam > (kota*80)/100 && toplam<kota) {
                    kalan_kota.backgroundTintList=getColorStateList(android.R.color.holo_red_light)
                    mp.start()
                }else if (toplam>=kota){
                    kalan_kota.backgroundTintList=getColorStateList(android.R.color.holo_red_dark)
                    mp.start()
                }

            } catch (e: NumberFormatException) {

                val dialogBuilder = AlertDialog.Builder(this)

                dialogBuilder.setMessage("Lütfen Geçerli Bir Gider Belirleyiniz.")
                    .setCancelable(false)

                    .setNegativeButton("Tamam", DialogInterface.OnClickListener {
                            dialog, id -> dialog.cancel()
                    })

                val alert = dialogBuilder.create()
                alert.setTitle("Gider Miktarı Alanı Boş!")
                alert.show()

            }
        }

    }
}