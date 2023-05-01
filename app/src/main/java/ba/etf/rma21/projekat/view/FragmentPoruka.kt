package ba.etf.rma21.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R

class FragmentPoruka : Fragment() {
    private lateinit var  tvPoruka: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.poruka_fragment, container, false)
        val poruka:String = arguments?.getString("upis") as String

        tvPoruka = view.findViewById(R.id.tvPoruka)
        tvPoruka.text = poruka;

        return view;
    }
    companion object {
        fun newInstance(): FragmentPoruka = FragmentPoruka()
    }
}