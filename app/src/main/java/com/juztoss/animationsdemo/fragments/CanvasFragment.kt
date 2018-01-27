package com.juztoss.animationsdemo.fragments

import android.app.Fragment
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.juztoss.animationsdemo.R


class CanvasFragment : Fragment {

    constructor() : super()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.canvas, container, false);
    }
}

class SnowAnimation : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private var snowflakes: Array<Snowflake>? = null

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        snowflakes = Array(10, {
            Snowflake(right - left, bottom - top,
                    context.getDrawable(R.drawable.snowflake),
                    resources.getDimension(R.dimen.max_snowflake_size),
                    resources.getDimension(R.dimen.max_snowflake_speed))
        })
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        snowflakes?.forEach {
            it.update()
            it.draw(canvas)
        }
        postInvalidateOnAnimation()
    }
}

internal class Snowflake(private val containerWidth: Int,
                         private val containerHeight: Int,
                         private val drawable: Drawable,
                         private val maxSize: Float,
                         private val maxSpeed: Float) {

    private var size: Double = 0.0
    private var speed: Double = 0.0
    private var x: Double = 0.0
    private var y: Double = 0.0

    init {
        reset()
    }

    private fun reset() {
        size = Math.random() * maxSize / 2 + maxSize / 2
        speed = Math.random() * maxSpeed / 2 + maxSpeed / 2
        y = -size;
        x = Math.random() * containerWidth
    }

    fun update() {
        y += speed
        if (y > containerHeight) {
            reset()
        }
    }

    fun draw(canvas: Canvas) {
        drawable.setBounds(x.toInt(), y.toInt(), (x + size).toInt(), (y + size).toInt())
        drawable.draw(canvas)
    }
}