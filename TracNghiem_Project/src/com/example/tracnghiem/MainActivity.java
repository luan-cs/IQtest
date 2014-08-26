package com.example.tracnghiem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	int socau = 10;
	int index = 0;
	List<cauhoi> ds_cauhoi;
	cauhoi cauhientai;
	int caudung = 0;
	int soMiliGiay = 11000;
	CountDownTimer demThoiGian;
	TextView tvCauHoi, tvThongBao, tvThoiGian;
	RadioButton rd0, rd1, rd2, rd3;
	RadioGroup rdg;
	Button btNext, btCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvCauHoi = (TextView) findViewById(R.id.textView1);
		tvThongBao = (TextView) findViewById(R.id.textView2);
		tvThoiGian = (TextView) findViewById(R.id.textView3);
		rdg = (RadioGroup) findViewById(R.id.radioGroup1);
		rd0 = (RadioButton) findViewById(R.id.radio0);
		rd1 = (RadioButton) findViewById(R.id.radio1);
		rd2 = (RadioButton) findViewById(R.id.radio2);
		rd3 = (RadioButton) findViewById(R.id.radio3);
		btNext = (Button) findViewById(R.id.button1);
		btCheck = (Button) findViewById(R.id.button2);
		tvThongBao.setTextColor(Color.BLUE);
		tvCauHoi.setTextColor(Color.RED);
		btCheck.setVisibility(View.GONE);
		quanlycauhoi db = new quanlycauhoi(this);
		try {
			db.createDatabase();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this, "Lỗi tạo cơ sỡ dữ liệu", Toast.LENGTH_SHORT)
					.show();
		}
		ds_cauhoi = new ArrayList<cauhoi>();
		ds_cauhoi = db.layNcaungaunghien(socau);
		hienthi(index);
		thoigian();
		btNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				if (rdg.getCheckedRadioButtonId() == -1) {
					Toast.makeText(getBaseContext(),
							"Vui lòng chọn một câu trả lời", Toast.LENGTH_SHORT)
							.show();
				} else {
					demThoiGian.cancel();
					KiemTraCauDung();
					index++;
					if (index < socau) {
						hienthi(index);
						thoigian();
					} else {
						index = 0;
						btNext.setVisibility(View.GONE);
						btCheck.setVisibility(View.VISIBLE);
						tvThoiGian.setVisibility(View.GONE);
					}
				}
			}
		});

		btCheck.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				demThoiGian.cancel();
				tvThoiGian.setVisibility(View.GONE);
				if (index >= socau) {
					tvThongBao.setText("Kết quả: ");
					tvCauHoi.setText("Bạn làm đúng " + caudung + " câu");
					rd0.setVisibility(View.GONE);
					rd2.setVisibility(View.GONE);
					rd3.setVisibility(View.GONE);
					rd1.setVisibility(View.GONE);
					index++;
					btCheck.setText("Exit");
				} else {
					hienthi(index);
					KiemTraLai();
					index++;
				}
				if (index == socau + 2) {
					System.exit(0);
				}
			}
		});
	}

	//Hiển thị thời gian cho mỗi câu hỏi
	public void thoigian() {
		demThoiGian = new CountDownTimer(soMiliGiay, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				tvThoiGian.setText("Thời gian: "+ millisUntilFinished / 1000);
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				KiemTraCauDung();
				index++;
				if (index < socau) {
					hienthi(index);
					thoigian();
				} else {
					index = 0;
					btNext.setVisibility(View.GONE);
					btCheck.setVisibility(View.VISIBLE);
					tvThoiGian.setVisibility(View.GONE);
					demThoiGian.cancel();
				}
			}
		};
		demThoiGian.start();
	}

	public void hienthi(int vitri) {
		tvThongBao.setText("Câu số: " + (vitri + 1) + "/" + socau);
		cauhientai = ds_cauhoi.get(vitri);
		tvCauHoi.setText(cauhientai.cauhoi);
		rd0.setText(cauhientai.cau_a);
		rd1.setText(cauhientai.cau_b);
		rd2.setText(cauhientai.cau_c);
		rd3.setText(cauhientai.cau_d);
		// Sau khi hiển thị, xóa Checked của các Radion Button của Radion Group.
		rdg.clearCheck();
	}

	public void KiemTraCauDung() {
		String cautraloi = "";
		if (rd0.isChecked() == true)
			cautraloi = "a";
		else if (rd1.isChecked() == true)
			cautraloi = "b";
		else if (rd2.isChecked() == true)
			cautraloi = "c";
		else if (rd3.isChecked() == true)
			cautraloi = "d";
		// Lưu trữ câu trả lời của người dùng vào List
		ds_cauhoi.get(index).cautraloi = cautraloi;
		// Kiểm tra câu trả lời và đáp án
		if (cautraloi.equalsIgnoreCase(cauhientai.dapan)) {
			caudung += 1;
		}

	}

	public void KiemTraLai() {
		// Đưa tất cả Radion Button về màu Đen mỗi khi ấn nút Check
		rd0.setTextColor(Color.BLACK);
		rd2.setTextColor(Color.BLACK);
		rd3.setTextColor(Color.BLACK);
		rd1.setTextColor(Color.BLACK);
		// Tô màu đỏ cho câu Đáp án
		if (cauhientai.dapan.equalsIgnoreCase("a"))
			rd0.setTextColor(Color.RED);
		else if (cauhientai.dapan.equalsIgnoreCase("b"))
			rd1.setTextColor(Color.RED);
		else if (cauhientai.dapan.equalsIgnoreCase("c"))
			rd2.setTextColor(Color.RED);
		else if (cauhientai.dapan.equalsIgnoreCase("d"))
			rd3.setTextColor(Color.RED);
		// Checked vào câu trả lời của người dùng để người dùng so sanh với Đáp
		// án
		if (cauhientai.cautraloi.equalsIgnoreCase("a"))
			rd0.setChecked(true);
		else if (cauhientai.cautraloi.equalsIgnoreCase("b"))
			rd1.setChecked(true);
		else if (cauhientai.cautraloi.equalsIgnoreCase("c"))
			rd2.setChecked(true);
		else if (cauhientai.cautraloi.equalsIgnoreCase("d"))
			rd3.setChecked(true);
		// Disable các Radion Button không cho người dùng chọn lại
		rd0.setEnabled(false);
		rd1.setEnabled(false);
		rd2.setEnabled(false);
		rd3.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
