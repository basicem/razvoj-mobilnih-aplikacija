package ba.etf.rma21.projekat.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class KvizoviAdapter(private var kvizovi: List<Kviz>,
                     private val onItemClicked: (kviz:Kviz) -> Unit) : RecyclerView.Adapter<KvizoviAdapter.KvizViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizViewHolder {
            val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.element_list, parent, false)
            return KvizViewHolder(view)
        }
        override fun getItemCount(): Int = kvizovi.size
        override fun onBindViewHolder(holder: KvizViewHolder, position: Int) {
            //ovo treba popraviti
            var sviPredmetiKviza : String = ""
            //sada nazivi predmeta nisu isto
            sviPredmetiKviza = kvizovi[position].nazivPredmeta!!
            println("Svi predmeti kviza " + sviPredmetiKviza)
            holder.nazivPredmeta.text = sviPredmetiKviza
            //holder.nazivPredmeta.text = kvizovi[position].nazivPredmeta.map { predmet -> predmet.naziv }.toString()
            holder.nazivKviza.text =  kvizovi[position].naziv
            holder.trajanje.text = kvizovi[position].trajanje.toString() + " min"

//            ako je uradjen kviz onda datum rada?
//            ako je aktivan a nije uradjen jos onda datum kraja?
//            ako nije jos akivan onda datum pocetka?
//            ako je prosao a nije uradjen onda datum kraja?

            val pattern = "dd.MM.yyyy"
            val simpleDateFormat = SimpleDateFormat(pattern)
            var bojaMatch: String
            if(kvizovi[position].osvojeniBodovi != null)
                holder.bodovi.text =  kvizovi[position].osvojeniBodovi.toString();
            else
                holder.bodovi.text = " "
            println("MOJ KVIZ JE " + kvizovi[position].osvojeniBodovi)

            //kviz uradjen plava
//            if(kvizovi[position].datumRada != null) {
//                holder.datumKviza.text = simpleDateFormat.format(kvizovi[position].datumRada)
//                bojaMatch = "plava"
//
//            }
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
//            val prije = dateFormat.parse(kvizovi[position].datumPocetka)
//            println("DATUM PRIJE JE " + prije)
//            if(kvizovi[position].datumKraj != null) {
//                val poslije = dateFormat.parse(kvizovi[position].datumKraj)
//                println("DATUM POSLIJE JE " + poslije)
//            }
//            if(kvizovi[position].datumRada != null) {
//                val rad = dateFormat.parse(kvizovi[position].datumRada)
//                println("DATUM POSLIJE JE " + rad)
//            }



            if(kvizovi[position].osvojeniBodovi != null) {
                holder.datumKviza.text = kvizovi[position].datumRada
                bojaMatch = "plava"

            }
//            kviz aktivan ali nije uradjen zelena
            else if(kvizovi[position].datumPocetka < (Calendar.getInstance().time).toString() && (kvizovi[position].datumKraj == null || kvizovi[position].datumKraj!! > (Calendar.getInstance().time).toString())
                && kvizovi[position].datumRada == null) {
                if(kvizovi[position].datumKraj == null) {
                    holder.datumKviza.text = "Inf."
                }
                else
                    holder.datumKviza.text = simpleDateFormat.format(kvizovi[position].datumKraj)
                bojaMatch = "zelena"
            }

            //kviz nije jos aktivan zuta
            else if((kvizovi[position].datumPocetka > (Calendar.getInstance().time).toString())) {
                holder.datumKviza.text = simpleDateFormat.format(kvizovi[position].datumPocetka)
                bojaMatch = "zuta"
            }

            else {
                holder.datumKviza.text = kvizovi[position].datumKraj
                bojaMatch = "crvena"
            }

            val context: Context = holder.imageView.getContext()
            val id: Int = context.resources.getIdentifier(bojaMatch, "drawable", context.getPackageName())
            holder.imageView.setImageResource(id)
            holder.itemView.setOnClickListener{ onItemClicked(kvizovi[position])}
        }

        fun updateTest(kvizovi: List<Kviz>) {
            this.kvizovi = kvizovi
            notifyDataSetChanged()
        }

        inner class KvizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val imageView: ImageView = itemView.findViewById(R.id.imageView)
            val nazivPredmeta: TextView = itemView.findViewById(R.id.nazivPredmeta)
            val datumKviza: TextView = itemView.findViewById(R.id.datumKviza)
            val nazivKviza: TextView = itemView.findViewById(R.id.nazivKviza)
            val bodovi: TextView = itemView.findViewById(R.id.bodoviKviza)
            val trajanje: TextView = itemView.findViewById(R.id.trajanjeKviza)

        }

    }

