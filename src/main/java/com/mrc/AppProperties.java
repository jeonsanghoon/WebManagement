package com.mrc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {
	@Value("${UPloadPath}")
	private String UPloadPath;
	@Value("${DownloadPath}")
	private String DownloadPath;
}
