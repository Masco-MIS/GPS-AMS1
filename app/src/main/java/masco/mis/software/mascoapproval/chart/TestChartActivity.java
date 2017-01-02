package masco.mis.software.mascoapproval.chart;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;
import masco.mis.software.mascoapproval.auxiliary.Database;
import masco.mis.software.mascoapproval.pojo.TRequest;

import static masco.mis.software.mascoapproval.auxiliary.Values.ApiGetData;

public class TestChartActivity extends Activity {
    List<BarEntry> entries = new ArrayList<BarEntry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_chart);
        final BarChart chart = (BarChart) findViewById(R.id.chart);
        TRequest tRequest = new TRequest();
        tRequest.setSp("sp_M_demo_barchart");
        tRequest.setDb(Database.SCM);
        Gson gson = new Gson();
        try {
            JSONObject json = new JSONObject(gson.toJson(tRequest, TRequest.class));
            Tapplication.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST, ApiGetData, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray data = response.getJSONArray("data");
                        List<BarDataSet> barDataSetList = new ArrayList<BarDataSet>();
                        String[] h = new String[data.length()];
                        if (data.length() > 0) {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject j = data.getJSONObject(i);
                                entries.add(new BarEntry(i + 2f, j.getInt("COUNT"), j.getString("STATUS")));
                                h[i] = j.getString("STATUS");
                            //    barDataSetList.add(new BarDataSet(entries, j.getString("STATUS")));

                            }
                            BarDataSet dataSet = new BarDataSet(entries, "Attendence ");
                            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            dataSet.setStackLabels(h);
                            dataSet.setValueFormatter(new IValueFormatter() {
                                @Override
                                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                                    //rather than diaplaying value show label
                                    return entry.getData().toString()+ " ("+entry.getY() + ")";
                                }
                            });
                            BarData lineData = new BarData(dataSet);
//                            for (int i =0;i <barDataSetList.size();i++)
//                            {
//                                lineData.addDataSet();
//                            }

                            chart.setData(lineData);
                            Legend l = chart.getLegend();
                            l.setEnabled(true);
//                            l.setFormSize(10f); // set the size of the legend forms/shapes
//                            l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
//
//                            l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//
//                            l.setTextSize(12f);
//                            l.setTextColor(Color.BLACK);
//                            l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
//                            l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis
                            chart.invalidate(); // refresh
                        }
                    } catch (Exception e) {
                        Log.e("mango", e.getMessage());
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }));
        } catch (Exception e) {

        }


////        BarEntry entry = new BarE
////        for(int i =0;i<50;i++)
////        {
////            Entry ey = new Entry();
////            ey.setX(i+10);
////            ey.setY(i+0);
////            entries.add(ey);
////        }
//        entries.add(new BarEntry(8f,10));
//
//      //  entries.add(new BarEntry(4f,16));
//     //   entries.add(new BarEntry(16f,2));
//        BarEntry nn = new BarEntry(9f,23);
//
//        BarDataSet dataSet = new BarDataSet(entries,"L1");
//
//    //    BarDataSet dataSet = new BarDataSet(entries, "Some Chart"); // add entries to dataset
//        dataSet.setColor(Color.YELLOW);
//        dataSet.setValueTextColor(Color.BLUE); // styling, ...
//      //  dataSet.setColors(ColorTemplate.COLORFUL_COLORS);


    }
}
