package com.letranbaosuong.applicationloadingstatusapp

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private val valueAnimator = ValueAnimator.ofInt(0, 360).setDuration(2000)

    private var buttonBackgroundColor = 0
    private var buttonTextColor = 0
    private var buttonLoadingColor = 0
    private var buttonCircleColor = 0

    private var buttonTextString = ""
    private var progress = 0

    var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Loading -> {
                buttonTextString = resources.getString(R.string.button_loading)

                valueAnimator.start()

            }

            ButtonState.Completed -> {
                buttonTextString = resources.getString(R.string.button_name)
                valueAnimator.cancel()

                progress = 0
            }

            else -> {}
        }
        invalidate()
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 50.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
            buttonTextColor = getColor(R.styleable.LoadingButton_textColor, 0)
            buttonBackgroundColor = getColor(R.styleable.LoadingButton_backgroundColor, 0)
            buttonCircleColor = getColor(R.styleable.LoadingButton_buttonCircleColor, 0)
            buttonLoadingColor = getColor(R.styleable.LoadingButton_buttonLoadingColor, 0)
        }

        buttonState = ButtonState.Completed

        valueAnimator.apply {
            addUpdateListener {
                progress = it.animatedValue as Int
                invalidate()
            }
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.color = buttonBackgroundColor
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), paint)

        paint.color = buttonLoadingColor
        canvas?.drawRect(0f, 0f, widthSize * progress / 360f, heightSize.toFloat(), paint)

        paint.color = buttonTextColor
        canvas?.drawText(buttonTextString, widthSize / 2.0f, heightSize / 2.0f + 20.0f, paint)

        paint.color = buttonCircleColor
        canvas?.drawArc(
            widthSize - 200f,
            20f,
            widthSize - 100f,
            130f,
            0f,
            progress.toFloat(),
            true,
            paint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val width: Int = resolveSizeAndState(minWidth, widthMeasureSpec, 1)
        val height: Int = resolveSizeAndState(
            MeasureSpec.getSize(width),
            heightMeasureSpec,
            0
        )
        widthSize = width
        heightSize = height
        setMeasuredDimension(width, height)

//        val desiredWidth = 100
//        val desiredHeight = 100
//
//        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
//        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
//        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
//        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
//
//        //Measure Width
//        val width: Int = when (widthMode) {
//            MeasureSpec.EXACTLY -> {
//                //Must be this size
//                widthSize
//            }
//
//            MeasureSpec.AT_MOST -> {
//                //Can't be bigger than...
//                min(desiredWidth, widthSize)
//            }
//
//            else -> {
//                //Be whatever you want
//                desiredWidth
//            }
//        }
//
//        //Measure Height
//        val height: Int = when (heightMode) {
//            MeasureSpec.EXACTLY -> {
//                //Must be this size
//                heightSize
//            }
//
//            MeasureSpec.AT_MOST -> {
//                //Can't be bigger than...
//                min(desiredHeight, heightSize)
//            }
//
//            else -> {
//                //Be whatever you want
//                desiredHeight
//            }
//        }
//
//        //MUST CALL THIS
//        setMeasuredDimension(width, height)
    }
}