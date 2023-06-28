package hr.dice.damir_stipancic.weatherapp.ui.daily_details_weather

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import hr.dice.damir_stipancic.weatherapp.R

class CustomDividerItemDecoration(context: Context, orientation: Int) : DividerItemDecoration(context, orientation) {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount - 1) {
            val childView = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(childView)
                .let { if (it == RecyclerView.NO_POSITION) return else it }
            val viewType = parent.adapter?.getItemViewType(position)
            val nextViewType = parent.adapter?.getItemViewType(position + 1)

            if (viewType == R.layout.daily_details_content_item &&
                nextViewType == R.layout.daily_details_content_item
            ) {
                val params = childView.layoutParams as RecyclerView.LayoutParams
                val top = childView.bottom + params.bottomMargin
                val bottom = top + drawable!!.intrinsicHeight
                val start = childView.paddingStart
                drawable!!.setBounds(start, top, parent.right, bottom)
                drawable!!.draw(c)
            }
        }
    }
}
