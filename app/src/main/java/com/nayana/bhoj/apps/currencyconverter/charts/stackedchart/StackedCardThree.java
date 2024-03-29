package com.nayana.bhoj.apps.currencyconverter.charts.stackedchart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.nayana.bhoj.apps.currencyconverter.R;
import com.nayana.bhoj.apps.currencyconverter.charts.CardController;

import chart.Tools;
import chart.animation.Animation;
import chart.model.BarSet;
import chart.renderer.AxisRenderer;
import chart.view.HorizontalStackBarChartView;


public class StackedCardThree extends CardController {


	private final HorizontalStackBarChartView mChart;

	private final String[] mLabels = {"", "", "", ""};

	private final float[][] mValues =
			  {{30f, 60f, 50f, 80f}, {-70f, -40f, -50f, -20f}, {50f, 70f, 10f, 30f},
						 {-40f, -70f, -60f, -50f}};


	public StackedCardThree(CardView card, Context context) {

		super(card);

		mChart = (HorizontalStackBarChartView) card.findViewById(R.id.chart4);
		((TextView) card.findViewById(R.id.electric_text)).setTypeface(
				  Typeface.createFromAsset(context.getAssets(), "Ponsi-Regular.otf"));
		((TextView) card.findViewById(R.id.fuel_text)).setTypeface(
				  Typeface.createFromAsset(context.getAssets(), "Ponsi-Regular.otf"));
	}


	@Override
	public void show(Runnable action) {

		super.show(action);

		BarSet dataset = new BarSet(mLabels, mValues[0]);
		dataset.setColor(Color.parseColor("#687E8E"));
		mChart.addData(dataset);

		dataset = new BarSet(mLabels, mValues[1]);
		dataset.setColor(Color.parseColor("#FF5C8E67"));
		mChart.addData(dataset);

		mChart.setRoundCorners(Tools.fromDpToPx(5));
		mChart.setBarSpacing(Tools.fromDpToPx(5));

		mChart.setBorderSpacing(Tools.fromDpToPx(5))
				  .setYLabels(AxisRenderer.LabelPosition.NONE)
				  .setXLabels(AxisRenderer.LabelPosition.NONE)
				  .setXAxis(false)
				  .setYAxis(false)
				  .setAxisBorderValues(-80, 80, 10);

		Animation anim = new Animation().setEasing(new DecelerateInterpolator()).setEndAction(action);

		mChart.show(anim);
	}


	@Override
	public void update() {

		super.update();

		if (firstStage) {
			mChart.updateValues(0, mValues[2]);
			mChart.updateValues(1, mValues[3]);
		} else {
			mChart.updateValues(0, mValues[0]);
			mChart.updateValues(1, mValues[1]);
		}
		mChart.notifyDataUpdate();
	}


	@Override
	public void dismiss(Runnable action) {

		super.dismiss(action);

		mChart.dismiss(mChart.getChartAnimation().setEasing(new AccelerateInterpolator()).setEndAction(action));
	}

}
