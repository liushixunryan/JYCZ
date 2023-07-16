package cn.ryanliu.jycz.activity.booth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.ryanliu.jycz.R;
import cn.ryanliu.jycz.bean.PrintBT;
import cn.ryanliu.jycz.util.Bluetooth;
import cn.ryanliu.jycz.view.LoadingLayout;

public class BTActivity extends Activity {

    private LoadingLayout loadingLayout;
    private RecyclerView recyHistory;
    private SwipeRefreshLayout swipeRefresh;
    private RelativeLayout activityBt;
    private Context mContext;
    public BluetoothAdapter myBluetoothAdapter;
    private Intent intent;
    private BaseQuickAdapter<PrintBT, BaseViewHolder> baseQuickAdapter;
    private Bluetooth bluetooth;
    private List<PrintBT> list;
    private int tag;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt);
        mContext = getApplicationContext();

        recyHistory = findViewById(R.id.recy_history);
        loadingLayout = findViewById(R.id.loading_layout);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        activityBt = findViewById(R.id.activity_bt);


        initData();
    }

    private void initData() {
        intent = getIntent();
        tag = intent.getIntExtra("TAG", RESULT_CANCELED);
        ListBluetoothDevice();
    }

    @SuppressLint("MissingPermission")
    public void ListBluetoothDevice() {
        if ((myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()) == null) {
            Toast.makeText(this, "没有找到蓝牙适配器", Toast.LENGTH_LONG).show();
            return;
        }

        if (!myBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 2);
        }
        list = new ArrayList<PrintBT>();
        baseQuickAdapter = new BaseQuickAdapter<PrintBT, BaseViewHolder>(android.R.layout.simple_list_item_2, list) {

            @Override
            protected void convert(BaseViewHolder helper, PrintBT item) {
                helper.setText(android.R.id.text1, item.getBTname());
                helper.setText(android.R.id.text2, item.getBTmac());
            }
        };
        recyHistory.setLayoutManager(new LinearLayoutManager(mContext));
        recyHistory.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyHistory.setAdapter(baseQuickAdapter);
        bluetooth = Bluetooth.getBluetooth(this);
        initBT();
        baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("SelectedBDAddress", list.get(position).getBTmac());
                setResult(tag, intent);
                finish();
            }
        });
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initBT();
                if (swipeRefresh.isRefreshing()) {
                    loadingLayout.showLoading();
                    swipeRefresh.setRefreshing(false);
                }

            }
        });
    }

    private void initBT() {
        list.clear();
        baseQuickAdapter.notifyDataSetChanged();
        bluetooth.doDiscovery();
        bluetooth.getData(new Bluetooth.toData() {

            @Override
            public void succeed(String BTname, String BTmac) {
                for (PrintBT printBT : list) {
                    if (BTmac.equals(printBT.getBTmac())) {
                        return;
                    }
                }
                //XiangYinBao_X3,ATOL1
                Log.d("TAG", "BTname:" + BTname);
                PrintBT printBT = new PrintBT();
                printBT.setBTname(BTname);
                printBT.setBTmac(BTmac);
                list.add(printBT);

                loadingLayout.showContent();


                baseQuickAdapter.notifyDataSetChanged();
            }

            @Override
            public void end(String end) {
                if (list.size() > 0) {
                    loadingLayout.showContent();
                } else {
                    loadingLayout.showEmpty();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bluetooth != null){
            bluetooth.disReceiver();
        }

    }
}
