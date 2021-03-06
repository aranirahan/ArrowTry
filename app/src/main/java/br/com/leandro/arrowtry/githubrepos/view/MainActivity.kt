package br.com.leandro.arrowtry.githubrepos.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import br.com.leandro.arrowtry.R
import br.com.leandro.arrowtry.githubrepos.di.RepositoriesDeps
import br.com.leandro.arrowtry.githubrepos.domain.Repository
import br.com.leandro.arrowtry.githubrepos.presentation.getRepositories
import br.com.leandro.arrowtry.githubrepos.view.adapter.RepositoriesAdapter
import br.com.leandro.arrowtry.retrofit.apiClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity(), RepositoriesView {

    private val repoList : MutableList<Repository> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupList(repoList)
    }

    private fun setupList(listItems : List<Repository>) {
        repositoryListRV.layoutManager = LinearLayoutManager(this)
        repositoryListRV.adapter = RepositoriesAdapter(listItems, { Log.d("Click", "Got a click!") })
    }

    override fun onResume() {
        super.onResume()
        getRepositories().run(RepositoriesDeps(this, apiClient()))
    }

    override fun showNotFoundError() {
        runOnUiThread {
            alert("Not found!!") {
                yesButton { }
            }.show()
        }
    }

    override fun showGenericError() {
        runOnUiThread {
            alert("Generic error!!") {
                yesButton { }
            }.show()
        }
    }

    override fun showAuthenticationError() {
        runOnUiThread {
            alert("Auth error!!") {
                yesButton { }
            }.show()
        }
    }

    override fun drawRepositories(heroes: List<Repository>) {
        runOnUiThread {
            repoList.addAll(heroes)
            repositoryListRV.adapter.notifyDataSetChanged()
        }
    }
}
