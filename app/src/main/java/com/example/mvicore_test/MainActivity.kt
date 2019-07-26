package com.example.mvicore_test

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvicore_feature_test.Feature
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ObservableSourceActivity<UiEvent>(), Consumer<ViewModel> {

    private lateinit var binding: Binding

    private val adapter = GroupAdapter<ViewHolder>()

    override fun accept(viewModel: ViewModel) {
        when {
            viewModel.throwable != null -> {
                progress.visibility = View.GONE
                list.visibility = View.GONE
                error.visibility = View.VISIBLE
                error.text = viewModel.throwable.message
            }
            viewModel.isLoading -> {
                progress.visibility = View.VISIBLE
                list.visibility = View.GONE
                error.visibility = View.GONE
            }
            !viewModel.isLoading -> {
                progress.visibility = View.GONE
                list.visibility = View.VISIBLE
                adapter.addAll(viewModel.info)
                error.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = Binding(this, Feature())
        binding.setup(this)

        list.adapter = this.adapter
        list.layoutManager = LinearLayoutManager(this)
        button.setOnClickListener { onNext(UiEvent.GetInfo) }
    }

}