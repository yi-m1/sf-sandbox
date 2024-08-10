package aws_study_session_2024;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTest {

	public static void main(String[] args) {

		// ロガーを呼び出します
		Logger logger = LogManager.getLogger("Logger");

		// INFOを吐きます
		logger.info("INFOです");

		//WARNを吐きます
		logger.warn("WARNです");

		// ERRORを吐きます
		logger.error("ERRORです。");
	}
}