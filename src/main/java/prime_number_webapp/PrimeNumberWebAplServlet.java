package prime_number_webapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * 素数ウェブアプリのサーブレットクラス
 */
@WebServlet("/PrimeNumberWebAplServlet")
public class PrimeNumberWebAplServlet extends HttpServlet {

	boolean primeNumberSearchFlg = true;

	/**
	 * フォームからデータを受け取る。<br>
	 * 結果をブラウザに返却する。
	 * 
	 * @param request リクエスト
	 * @param response レスポンス
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		ArrayNode array = mapper.createArrayNode();

		// Content Typeを設定
		response.setContentType("text/html; charset=Shift_JIS");

		// 入力されたフォームデータを取得
		String item1 = request.getParameter("item1");
//		try {
		int num = Integer.parseInt(item1);
//		} catch (NumberFormatException e) {
//			System.out.println(e);
//		}

		new Thread() {
			@Override
			public void run() {
				try {
					int from = 2;
					int to = 102;
					while (primeNumberSearchFlg) {
						/*array.add(getPrimeNumber("18.183.82.46", "aws-webapp/PrimeNumber",
								String.valueOf(from), String.valueOf(to)));*/
						// 接続テスト用
						array.add(getPrimeNumber("localhost", "aws-webapp-sento/PrimeNumber",
								String.valueOf(from), String.valueOf(to)));
						from += 202;
						to += 202;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

		new Thread() {
			@Override
			public void run() {
				try {
					int from = 103;
					int to = 203;
					while (primeNumberSearchFlg) {
						array.add(getPrimeNumber("35.78.185.41", "Aws-0.0.1-SNAPSHOT/PrimeNumber",
								String.valueOf(from), String.valueOf(to)));
						from += 202;
						to += 202;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

		String primeNumber = "-";
		while (primeNumberSearchFlg) {
			Iterator<JsonNode> i = array.elements();
			List<JsonNode> list = new ArrayList<>();
			while (i.hasNext()) {
				list.add(i.next());
			}
			list.sort(Comparator.comparing(o -> o.asText()));
			if (list.size() >= num) {
				primeNumber = list.get(num - 1).asText();
				setPrimeNumberSearchFlg(false);
			}
		}

		PrintWriter out = response.getWriter();
		out.println("<html><head></head><body>");
		out.println("<p>その素数は・・・</p>");
		out.println("<p>素数：" + primeNumber + "</p>");
		out.println("</body></html>");
	}

	/**
	 * 指定された範囲の素数を返却する。
	 * 
	 * @param url1 計算サーバのホスト名 or IPアドレス
	 * @param url2 計算サーバのサブディレクトリ
	 * @param from 計算開始範囲
	 * @param to 計算終了範囲
	 * @return Json形式の配列にした素数
	 * @throws IOException 
	 */
	private ArrayNode getPrimeNumber(String url1, String url2,
			String from, String to) throws IOException {
		String result = "";
		HttpURLConnection connection = null;
		BufferedReader in = null;
		ArrayNode primeNumberJsonResult = null;

		try {
			String getUrl = "http://" + url1 + ":8080/" + url2 + "?from=" + from + "&to=" + to;
			// URL に対して openConnection メソッドを呼び出す、接続オブジェクトを生成
			URL url = new URL(getUrl);
			connection = (HttpURLConnection) url.openConnection();
			//リクエストのメソッドを指定
			connection.setRequestMethod("GET");
			//通信開始
			connection.connect();
			// レスポンスコードを判断する、OKであれば、進める
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				// 通信に成功した
				// テキストを取得する
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String tmp = "";

				while ((tmp = in.readLine()) != null) {
					result += tmp;
				}

				// ObjectMapperを利用し JSON文字列 をJavaオブジェクトに変換する
				ObjectMapper mapper = new ObjectMapper();
				primeNumberJsonResult = (ArrayNode) mapper.readTree(result);
			}

		} finally {
			// クローズ
			if (in != null) {
				in.close();
			}
			connection.disconnect();
		}

		return primeNumberJsonResult;
	}

	private void setPrimeNumberSearchFlg(boolean flg) {
		this.primeNumberSearchFlg = flg;
	}
}