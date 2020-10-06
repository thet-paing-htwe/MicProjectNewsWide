package com.tphtwe.newswide.ui.category

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tphtwe.newswide.R
import com.tphtwe.newswide.model.Category
import kotlinx.android.synthetic.main.fragment_category_list.*


class CategoryListFragment : Fragment(),CategoryListAdapter.ClickListener {
        lateinit var categoryListAdapter: CategoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(toolbar_category)
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title="News Categories"
        var listCategory=ArrayList<Category>()
        listCategory.add(Category(R.drawable.ic_general,"General","general"))
        listCategory.add(Category(R.drawable.ic_business,"Business","business"))
        listCategory.add(Category(R.drawable.ic_health,"Health","health"))
        listCategory.add(Category(R.drawable.ic_volleyball,"Sports","sports"))
        listCategory.add(Category(R.drawable.ic_science,"Science","science"))
        listCategory.add(Category(R.drawable.ic_cloud,"Technology","technology"))
        listCategory.add(Category(R.drawable.ic_entertainment,"Entertainment","entertainment"))


        categoryListAdapter= CategoryListAdapter(listCategory)
        categoryRecycler.apply {
            layoutManager= GridLayoutManager(context,2)
            adapter=categoryListAdapter
        }
        categoryListAdapter.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun click(category:Category) {
        var action= CategoryListFragmentDirections.actionNavCategoryToCategoryNewsFragment(category.CId)
        findNavController().navigate(action)
    }


}