package com.example.myhome.presentation.features.charge.chart.renderers

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler
import kotlin.math.ceil

class RoundedBarChartRenderer(
    chart: BarDataProvider?,
    animator: ChartAnimator?,
    viewPortHandler: ViewPortHandler?
) : BarChartRenderer(chart, animator, viewPortHandler) {
    private val mBarShadowRectBuffer = RectF()
    private var mRadius = 0

    init {
        initBuffers()
    }

    fun setRadius(mRadius: Int) {
        this.mRadius = mRadius
    }

    override fun drawDataSet(c: Canvas, dataSet: IBarDataSet, index: Int) {
        if (mBarBuffers !== null) {
            val trans: Transformer = mChart.getTransformer(dataSet.axisDependency)
            mBarBorderPaint.color = dataSet.barBorderColor
            mBarBorderPaint.strokeWidth = Utils.convertDpToPixel(dataSet.barBorderWidth)
            mShadowPaint.color = dataSet.barShadowColor
            val drawBorder = dataSet.barBorderWidth > 0f
            val phaseX = mAnimator.phaseX
            val phaseY = mAnimator.phaseY
            if (mChart.isDrawBarShadowEnabled) {
                mShadowPaint.color = dataSet.barShadowColor
                val barData = mChart.barData
                val barWidth = barData.barWidth
                val barWidthHalf = barWidth / 2.0f
                var x: Float
                var i = 0
                val count =
                    ceil((dataSet.entryCount.toFloat() * phaseX).toDouble().toInt().toDouble())
                    .coerceAtMost(dataSet.entryCount.toDouble())
                while (i < count) {
                    val e = dataSet.getEntryForIndex(i)
                    x = e.x
                    mBarShadowRectBuffer.left = x - barWidthHalf
                    mBarShadowRectBuffer.right = x + barWidthHalf
                    trans.rectValueToPixel(mBarShadowRectBuffer)
                    if (!mViewPortHandler.isInBoundsLeft(mBarShadowRectBuffer.right)) {
                        i++
                        continue
                    }
                    if (!mViewPortHandler.isInBoundsRight(mBarShadowRectBuffer.left)) break
                    mBarShadowRectBuffer.top = mViewPortHandler.contentTop()
                    mBarShadowRectBuffer.bottom = mViewPortHandler.contentBottom()
                    c.drawRoundRect(mBarRect, mRadius.toFloat(), mRadius.toFloat(), mShadowPaint)
                    i++
                }
            }
            val buffer = mBarBuffers[index]
            buffer.setPhases(phaseX, phaseY)
            buffer.setDataSet(index)
            buffer.setInverted(mChart.isInverted(dataSet.axisDependency))
            buffer.setBarWidth(mChart.barData.barWidth)
            buffer.feed(dataSet)
            trans.pointValuesToPixel(buffer.buffer)
            val isSingleColor = dataSet.colors.size == 1
            if (isSingleColor) {
                mRenderPaint.color = dataSet.color
            }
            var j = 0
            while (j < buffer.size()) {
                if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2])) {
                    j += 4
                    continue
                }
                if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j])) break
                if (!isSingleColor) {
                    mRenderPaint.color = dataSet.getColor(j / 4)
                }
                if (dataSet.gradientColor != null) {
                    val gradientColor = dataSet.gradientColor
                    mRenderPaint.shader = LinearGradient(
                        buffer.buffer[j],
                        buffer.buffer[j + 3],
                        buffer.buffer[j],
                        buffer.buffer[j + 1],
                        gradientColor.startColor,
                        gradientColor.endColor,
                        Shader.TileMode.MIRROR
                    )
                }
                if (dataSet.gradientColors != null) {
                    mRenderPaint.shader = LinearGradient(
                        buffer.buffer[j],
                        buffer.buffer[j + 3],
                        buffer.buffer[j],
                        buffer.buffer[j + 1],
                        dataSet.getGradientColor(j / 4).startColor,
                        dataSet.getGradientColor(j / 4).endColor,
                        Shader.TileMode.MIRROR
                    )
                }
                val path2: Path = roundRect(
                    RectF(
                        buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                        buffer.buffer[j + 3]
                    ), mRadius.toFloat(), mRadius.toFloat(), true, true, false, false
                )
                c.drawPath(path2, mRenderPaint)
                if (drawBorder) {
                    val path: Path = roundRect(
                        RectF(
                            buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                            buffer.buffer[j + 3]
                        ), mRadius.toFloat(), mRadius.toFloat(), true, true, false, false
                    )
                    c.drawPath(path, mBarBorderPaint)
                }
                j += 4
            }
        }
    }

    private fun roundRect(
        rect: RectF,
        rx: Float,
        ry: Float,
        tl: Boolean,
        tr: Boolean,
        br: Boolean,
        bl: Boolean
    ): Path {
        var rxX = rx
        var ryY = ry
        val top = rect.top
        val left = rect.left
        val right = rect.right
        val bottom = rect.bottom
        val path = Path()
        if (rxX < 0f) rxX = 0f
        if (ryY < 0f) ryY = 0f
        val width = right - left
        val height = bottom - top
        if (rxX > width / 2) rxX = width / 2
        if (ryY > height / 2) ryY = height / 2
        val widthMinusCorners = (width - (2 * rxX))
        val heightMinusCorners = (height - (2 * ryY))
        path.moveTo(right, top + ryY)
        if (tr) path.rQuadTo(0f, -ryY, -rxX, -ryY)
        else {
            path.rLineTo(0f, -ryY)
            path.rLineTo(-rxX, 0f)
        }
        path.rLineTo(-widthMinusCorners, 0f)
        if (tl) path.rQuadTo(-rxX, 0f, -rxX, ryY)
        else {
            path.rLineTo(-rxX, 0f)
            path.rLineTo(0f, ryY)
        }
        path.rLineTo(0F, heightMinusCorners)
        if (bl) path.rQuadTo(0F, ryY, rxX, ryY)
        else {
            path.rLineTo(0F, ryY)
            path.rLineTo(rxX, 0f)
        }
        path.rLineTo(widthMinusCorners, 0f)
        if (br) path.rQuadTo(rxX, 0f, rxX, -ryY)
        else {
            path.rLineTo(rxX, 0f)
            path.rLineTo(0f, -ryY)
        }
        path.rLineTo(0f, -heightMinusCorners)
        path.close()
        return path
    }
}