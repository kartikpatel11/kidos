package com.example.kidos.interfaces;

import java.util.Map;

import android.content.Context;

public interface IKidosRestClientWrapper {

	public void restRequest(IKidosRestClientWrapper context, Map<String, Object> args, String method, String url);
	public void restCallBack(String restOutput);
}
