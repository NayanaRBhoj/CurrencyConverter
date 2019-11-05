package chart.animation;

import java.util.ArrayList;

import chart.model.ChartSet;


public interface ChartAnimationListener {

	boolean onAnimationUpdate(ArrayList<ChartSet> data);
}
